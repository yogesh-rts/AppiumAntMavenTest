#!/bin/zsh

DEVICE_NAME="SIMULATOR13"
DEVICE_TYPE="iPhone 13"

xcrun simctl delete ${DEVICE_NAME}
NEW_DEVICE=$(xcrun simctl create ${DEVICE_NAME} ${DEVICE_TYPE})
echo "Created device ID: ${NEW_DEVICE}"
xcrun simctl bootstatus ${DEVICE_NAME} -bin
open -a simulator