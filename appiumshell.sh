#!/bin/bash
sleep 40 && mvn test &
echo "Virtual device start..."
os=${OSTYPE//[0-9.-]*/}

case "$os" in
  darwin)
    ~/Library/Android/sdk/tools/emulator -avd meraki ;;

  msys)
    emulator -avd meraki ;;

  linux)
    emulator -avd meraki ;;
  *)

    echo "Unknown Operating System $OSTYPE"

 esac

 wait  