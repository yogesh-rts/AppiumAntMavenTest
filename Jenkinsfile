pipeline {
    agent any
    environment {
        PATH="$PATH:/usr/local/bin"
        LANG="en_US.UTF-8"
        ANDROID_HOME="/Users/yogeshkumar/Library/Android/sdk"
        ANDROID_AVD_HOME="/Users/yogeshkumar/.android/avd"
        ANDROID_PLATFORM_TOOLS="${ANDROID_HOME}/platform-tools"
        ANDROID_TOOLS="${ANDROID_HOME}/tools"
        JAVA_HOME="/opt/homebrew/opt/openjdk@17"
        EMULATOR_HOME="${ANDROID_HOME}/emulator"
     //   MAVEN_HOME="/opt/homebrew/Cellar/maven/3.8.6"
     //   MAVEN_PATH="${MAVEN_HOME}/bin:${MAVEN_PATH}"

        APPIUM_PORT=4723

 //       ANT_HOME="/opt/homebrew/Cellar/ant/1.10.12/libexec/bin/lib"
    }
    options {
        timeout(time:2, unit:'HOURS')
    }
    stages {
        stage('Check tool versions') {
            steps {
                script {
              //  sh 'ant -version'
                sh 'java -version'
                sh 'mvn -version'
                sh 'appium &'
                }
            }
        }
        /* stage('Clean Workspace') {
            steps {
                clean()
            }
        } */
        /* stage('Clone project') {
            steps {
                git branch: 'main', credentialsId: 'MyGitHub', url: 'https://github.com/yogesh-rts/AppiumAntMavenTest'
            }
        } */
        stage('Android UI tests') {
            steps {
                script {
                    // Check for any real devices being connected
                    def devices = sh(returnStdout: true, script: "${ANDROID_PLATFORM_TOOLS}/adb devices").trim()
                    if (devices == "List of devices attached") {

                    // No real devices are connected, check emulator image is already exits
                    sh 'if [[ ! -d ~/.android/avd/EMULATOR27.avd ]]; then echo "EMULATOR27 image does not exists"; fi'

                    // If emulator image doesn't exists override permission and create it
                    sh 'if [[ ! -d ~/.android/avd/EMULATOR27.avd ]]; then chmod +x "${WORKSPACE}/src/test/resources/setup/emulator27_arm64.sh"; fi'
                    // sh 'chmod +x ${WORKSPACE}/src/test/resources/setup/emulator27_arm64.sh'
                    sh "${WORKSPACE}/src/test/resources/setup/emulator27_arm64.sh"

                    /*sh 'chmod 755 /Users/yogeshkumar/MyGit - Repo/AppiumAntMavenTest/src/test/resources/setupsrc/test/resources/setup/emulator27_arm64.sh'
                    sh '/Users/yogeshkumar/MyGit - Repo/AppiumAntMavenTest/src/test/resources/setup/emulator27_arm64.sh'*/

                    // Launch EMULATOR27 in background and wait for it to be ready
                    sh "${EMULATOR_HOME}/emulator @EMULATOR27 &"
                    sh "${ANDROID_PLATFORM_TOOLS}/adb wait-for-device"
                    sh 'sleep 60'
                    }

                    def adbOutput = sh(script: "${ANDROID_PLATFORM_TOOLS}/adb devices", returnStdout: true)
                    def deviceId = adbOutput.split('\n')[1].split('\t')[0]

                    // Copy APK artifact from another job 'HALO-ANDROID'
                    copyArtifacts(
                    projectName: 'AndroidUIProject',
                    flatten: true,
                    fingerprintArtifacts: true
                    );

                    def isInstalled = sh(script: "${ANDROID_PLATFORM_TOOLS}/adb shell pm list packages | grep com.example.demoandroidapp", returnStdout: true).trim()

                    // If the APK is installed, uninstall it
                    if (isInstalled) {
                        sh "${ANDROID_PLATFORM_TOOLS}/adb uninstall com.example.demoandroidapp"
                    }

                    // Install APK to the connected device
                    sh "${ANDROID_PLATFORM_TOOLS}/adb -s ${deviceId} install Android-UI-*-debug.apk"

                    // Run UI automation tests
                    sh 'mvn test -Pandroid'

                    sh 'mvn surefire-report:report-only'
                }
            }
        }
        stage('Publish Test Results') {
            steps {
                publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: 'target/surefire-reports',
                reportFiles: '*.html',
                reportName: 'Test Report'
                ])
            }
        }
    }

    post {
        always {
            echo "Stop running appium server"
            sh "kill \$(lsof -t -i :${APPIUM_PORT})"

            cleanWs()
        }
    }
}