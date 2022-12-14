#!/bin/zsh

DEVICE_NAME="EMULATOR31"
SYSTEM_IMAGE_PATH="system-images;android-31;google_apis;arm64-v8a"

$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --install ${SYSTEM_IMAGE_PATH}

echo no | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd -n ${DEVICE_NAME} -k ${SYSTEM_IMAGE_PATH} --force -d pixel_3a_xl
$ANDROID_HOME/tools/emulator -avd ${DEVICE_NAME}