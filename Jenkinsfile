pipeline {
    agent any
    environment {
        PATH="$PATH:/usr/local/bin"
        LANG="en_US.UTF-8"
        ANDROID_HOME="/Users/yogeshkumar/Library/Android/sdk"
        ANDROID_AVD_HOME="/Users/yogeshkumar/.android/avd"
        ANDROID_PLATFORM_TOOLS="${ANDROID_HOME}/platform-tools"
        ANDROID_TOOLS="${ANDROID_HOME}/tools"
        JAVA_HOME="/Users/yogeshkumar/Library/Java/JavaVirtualMachines/temurin-17.0.5/Contents/Home"
        EMULATOR_HOME="${ANDROID_HOME}/emulator"
        MAVEN_HOME="/opt/homebrew/Cellar/maven/3.8.7/libexec/bin"
        PYTHON_HOME="/opt/homebrew/opt/python@3.11/libexec/bin"

        APPIUM_PORT=4723

 //       ANT_HOME="/opt/homebrew/Cellar/ant/1.10.12/libexec/bin/lib"
    }
    /* tools {
        maven 'Maven'
    } */
    options {
        timeout(time:2, unit:'HOURS')
    }
    stages {
        stage('Check tool versions') {
            steps {
                script {
              //  sh 'ant -version'
                sh 'java -version'
                sh 'source ~/.profile'
                sh 'appium &'
                sh 'mvn -version'
                sh 'python3 --version'
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
        stage('Setup virtual environment') {
            steps {
                sh """
                    # Go to test project folder
                    cd ${WORKSPACE}

                     # Setup virtual environment
                     python3.11 -m venv ./venv
                """
            }
        }

        stage('Activate virtual env') {
            steps {
                sh """
                    #Activate the virtual environment
                    cd ${WORKSPACE}
                    . ./venv/bin/activate
                """
            }
        }
        stage('Android UI tests') {
            steps {
                script {
                    // Check for any real devices being connected
                    def devices = sh(returnStdout: true, script: "${ANDROID_PLATFORM_TOOLS}/adb devices").trim()
                    if (devices == "List of devices attached") {

                    // No real devices are connected, check emulator image is already exits
                    sh 'if [[ ! -d ~/.android/avd/EMULATOR27.avd ]]; then echo "EMULATOR27 image does not exists"; fi'

                    // If emulator image doesn't exists override permission and create it
                   // sh 'if [[ ! -d ~/.android/avd/EMULATOR27.avd ]]; then chmod +x "${WORKSPACE}/src/test/resources/setup/emulator27_arm64.sh" sh "${WORKSPACE}/src/test/resources/setup/emulator27_arm64.sh" else echo "EMULATOR27.avd already exists"; fi'
                    // sh 'chmod +x ${WORKSPACE}/src/test/resources/setup/emulator27_arm64.sh'

                    sh """
                        # Check if the AVD directory exists
                        if [[ ! -d ~/.android/avd/EMULATOR27.avd ]]; then
                            # Change permissions and run the setup script
                            chmod +x "${WORKSPACE}/src/test/resources/setup/emulator27_arm64.sh"
                            sh "${WORKSPACE}/src/test/resources/setup/emulator27_arm64.sh"

                        else
                            echo "Already AVD is created and exists"
                        fi
                    """

                    sh "${EMULATOR_HOME}/emulator @EMULATOR27 &"
                    sh "${ANDROID_PLATFORM_TOOLS}/adb wait-for-device"
                    sh 'sleep 60'
                        /* # Check if the AVD was created successfully
                        if [[ -d ~/.android/avd/EMULATOR27.avd ]]; then
                            echo "AVD created successfully"
                            # Start the emulator
                            sh "${EMULATOR_HOME}/emulator @EMULATOR27 &"
                            echo "Emulator started"
                            sh "${ANDROID_PLATFORM_TOOLS}/adb wait-for-device"
                        else
                            echo "Failed to launch AVD"
                        fi */


                    // Launch EMULATOR27 in background and wait for it to be ready
                    /* sh "${EMULATOR_HOME}/emulator @EMULATOR27 &"
                    sh "${ANDROID_PLATFORM_TOOLS}/adb wait-for-device"
                    sh 'sleep 60' */
                    }

                    def adbOutput = sh(script: "${ANDROID_PLATFORM_TOOLS}/adb devices", returnStdout: true)
                    def deviceId = adbOutput.split('\n')[1].split('\t')[0]

                    // Find the file that matches the wildcard
                    /* def filePathName= sh(returnStdout: true, script:'ls /Users/yogeshkumar/.jenkins/workspace/Android-QA/Android-UI-Project/Android-UI-*-debug.apk').trim()
                    def fileName = sh(returnStdout: true, script: "basename ${filePathName}").trim()
                    echo "${fileName}"
                    // Copy APK artifact from another job 'HALO-ANDROID'
                    apkPath= "/Users/yogeshkumar/.jenkins/workspace/Android-QA/Android-UI-Project/${fileName}"
                    targetDir = "${WORKSPACE}/Android-UI-3-debug.apk"
                    sh "cp ${apkPath} ${targetDir}" */


                   //Check if the file was copied successfully
                    /* sh """if [ $? -eq 0 ]; then
                        echo "Successfully copied .apk file from shared file server to workspace"
                    else
                        echo "Failed to copy .apk file from shared file server to workspace"
                        exit 1
                    fi""" */

                    copyArtifacts(
                    projectName: 'Android-QA/Android-UI-Project',
                    flatten: true,
                    fingerprintArtifacts: true,
                    selector: lastWithArtifacts()
                    );

                    def isInstalled = sh(script: "${ANDROID_PLATFORM_TOOLS}/adb shell pm list packages", returnStdout: true).trim()
                    echo "${isInstalled}"
                    // If the APK is installed, uninstall it
                    if (isInstalled.contains("com.example.demoandroidapp")) {
                        sh "${ANDROID_PLATFORM_TOOLS}/adb uninstall com.example.demoandroidapp"
                    } else {
                        echo "the package is not installed"
                    }

                    // Install APK to the connected device
                    sh "${ANDROID_PLATFORM_TOOLS}/adb -s ${deviceId} install Android-UI-*-debug.apk"

                    // Run UI automation tests
                    sh 'mvn test -Pandroid'

                    sh 'mvn surefire-report:report-only'
                }
            }

            post {
                always {

                    script {
                        sh 'mv target/testng-custom-results.xml target/android-test-results.xml'
                        archiveArtifacts 'target/android-test-results.xml'

                        withCredentials([usernamePassword(credentialsId: 'TestRailDemo', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                            sh 'echo $USERNAME'
                            sh 'echo $PASSWORD'

                            sh """
                                # Go to test project folder and activate virtual environment
                                cd ${WORKSPACE}
                                . ./venv/bin/activate

                                # Install TestRail CLI and upload test results
                                pip install trcli
                                trcli -n \
                                -h "https://jenkinsautotest.testrail.io/" \
                                -u $USERNAME \
                                -p $PASSWORD \
                                --project "Android-Automation" \
                                parse_junit \
                                --case-matcher "property" \
                                --title "Automated Regression Test Run" \
                                -f "target/android-test-results.xml"
                            """
                        }
                    }
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
        /* stage('Archive Junit Results') {
            steps {
                junit 'target/surefire-reports *//** /* *//*.xml'
            }
        } */
    }

    post {
        always {
           // junit 'target/testng-custom-results.xml'

            /* archiveArtifacts 'target/testng-custom-results.xml' */

            //testNG reportFilenamePattern: '**/target/surefire-reports/testng-results.xml'

            echo "Stop running appium server"
            sh "kill \$(lsof -t -i :${APPIUM_PORT})"

           /*  script {
                withCredentials([usernamePassword(credentialsId: 'TestRailDemo', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'echo $USERNAME'
                    sh 'echo $PASSWORD'

                    sh """
                        # Go to test project folder and activate virtual environment
                        cd ${WORKSPACE}
                        . ./venv/bin/activate

                        # Install TestRail CLI and upload test results
                        pip install trcli
                        trcli -n \
                            -h "https://jenauto.testrail.io/" \
                            -u $USERNAME \
                            -p $PASSWORD \
                            --project "Android-Automation" \
                            parse_junit \
                            --case-matcher "property" \
                            --title "Automated Regression Test Run" \
                            -f "target/testng-custom-results.xml"
                    """

                }

            } */

        }
    }
}
