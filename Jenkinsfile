pipeline {
    agent any
    environment {
        PATH="$PATH:/usr/local/bin"
        LANG="en_US.UTF-8"
        ANDROID_SDK_ROOT="/Users/yogeshkumar/Library/Android/sdk"
        ANDROID_AVD_HOME="/Users/yogeshkumar/.android/avd"
        JAVA_HOME="/opt/homebrew/opt/openjdk@17"
 //       ANT_HOME="/opt/homebrew/Cellar/ant/1.10.12/libexec/bin/lib"
    }
    options {
        timeout(time:2, unit:'HOURS')
    }
    stages {
        stage('Check tool versions'){
            steps {
                script {
                sh 'ant -version'
                sh 'java -version'
                sh 'mvn -version'
                }
            }
        }
        stage('Launch Android emulator') {
            steps {
                script {
                sh 'chmod 755 ./src/test/resources/setup/emulator27_arm64.sh'
                sh "./src/test/resources/setup/emulator27_arm64"
                }
            }
        }
        stage('Run UI test') {
            steps {
                script {
                  sh 'ant chromeTest'
                }
            }
        }
    }
}
