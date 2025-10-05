pipeline {
  agent any

  tools {
    maven 'Maven-3.9'
    jdk   'JDK-21'
  }

  environment {
    REGISTRY              = "docker.io"
    IMAGE_REPO            = "shsullivan/discphase2"       // e.g., myuser/myapp or myorg/myapp
    DOCKER_CREDENTIALS_ID = "docker-registry-creds"
    MAVEN_ARGS            = "-B -ntp"
  }

  options {
    timestamps()
    ansiColor('xterm')
  }

  parameters {
    booleanParam(name: 'PUSH_IMAGE', defaultValue: true, description: 'Push Docker image after build')
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Compute Tags') {
      steps {
        script {
          env.GIT_SHORT_SHA = isUnix() ?
            sh(returnStdout: true, script: "git rev-parse --short HEAD").trim() :
            bat(returnStdout: true, script: "git rev-parse --short HEAD").trim()
          env.IMAGE_TAG = env.GIT_SHORT_SHA
          echo "IMAGE TAG: ${env.IMAGE_TAG}"
        }
      }
    }

    stage('Build & Test (Maven)') {
      steps {
        script {
          if (isUnix()) {
            sh "mvn ${env.MAVEN_ARGS} clean test"
            sh "mvn ${env.MAVEN_ARGS} package -DskipTests"
          } else {
            bat "mvn ${env.MAVEN_ARGS} clean test"
            bat "mvn ${env.MAVEN_ARGS} package -DskipTests"
          }
        }
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
      }
    }

    stage('Docker Build') {
      steps {
        script {
          def buildCmd = """
            docker build \
              --build-arg JAR_FILE=target/*.jar \
              -t ${env.REGISTRY}/${env.IMAGE_REPO}:${env.IMAGE_TAG} \
              .
          """.trim()
          isUnix() ? sh(buildCmd) : bat(buildCmd)
        }
      }
    }

    stage('Docker Push') {
      when {
        expression { return params.PUSH_IMAGE }
      }
      steps {
        withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          script {
            def loginCmd = "echo ${isUnix() ? '$DOCKER_PASS' : '%DOCKER_PASS%'} | docker login ${env.REGISTRY} -u ${isUnix() ? '$DOCKER_USER' : '%DOCKER_USER%'} --password-stdin"
            isUnix() ? sh(loginCmd) : bat(loginCmd)

            def pushTag = "${env.REGISTRY}/${env.IMAGE_REPO}:${env.IMAGE_TAG}"
            isUnix() ? sh("docker push ${pushTag}") : bat("docker push ${pushTag}")

            //
            if (env.BRANCH_NAME == 'main' || env.GIT_BRANCH == 'origin/main') {
              def latest = "${env.REGISTRY}/${env.IMAGE_REPO}:latest"
              isUnix() ? sh("docker tag ${pushTag} ${latest}") : bat("docker tag ${pushTag} ${latest}")
              isUnix() ? sh("docker push ${latest}") : bat("docker push ${latest}")
            }
          }
        }
      }
    }
  }
  post {
    cleanup {
      script {
        if (isUnix()) {
          sh 'docker image prune -f || true'
        } else {
          bat 'docker image prune -f || exit /b 0'
        }
      }
    }
  }
}
