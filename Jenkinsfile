pipeline {
    agent any
    environment {
        PATH="$PATH:/usr/local/bin"
        LANG="en_US.UTF-8"
        ANDROID_SDK_ROOT="/Users/yogeshkumar/Library/Android/sdk"
        ANDROID_AVD_HOME="/Users/yogeshkumar/.android/avd"
        JAVA_HOME="/opt/homebrew/opt/openjdk@17"
    }
    options {
        timeout(time:2, unit:'HOURS')
    }
    stages {
        stage('Run UI test') {
            steps {
                script {
                  sh 'ant chromeTest'
                }
            }
        }
    }
}
Footer
