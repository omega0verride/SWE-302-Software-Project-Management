#!/bin/bash
# source env.sh
set -e

echo "[1/3][0] Exportig fronted..."
cd ../../frontend && npm install --legacy-peer-deps && npm run export
if [ $? -eq 0 ]; then
    echo "[1/3][1] Export successful!"
    echo "[1/3][2] Deleting old prodcution target directory..."
    rm -rf ../production/target
    echo "[1/3][3] Creating new prodcution target directory..."
    mkdir -p ../production/target/html
    echo "[1/3][4] Copying static html files..."
    pwd
    cp -r ./out/* ../production/target/html/
    
    echo "[2/3][0] Building backend jar..."
    cd ../backend && mvn package
    if [ $? -eq 0 ]; then
        echo "[2/3][1] mvn package executed succesfully!"
        jarFile=$( find ./target/ -name "*.jar" -print0 | xargs -r -0 ls -1 -t | head -) # get latest jar file
        if [ $? -eq 0 ]; then
            if ! [[ -z "$jarFile" ]]; then
              echo "Copying jar file: $jarFile"
              cp $jarFile ../production/target/
              echo "Copying sprint application properties..."
              cp ./src/main/resources/application.yml ../production/target/
            else
                read -n 1 -p "Error: jar not found! Press any key to continue..." _continue
                exit 1
            fi
        else
            read -n 1 -p "Error: Could not locate jar file under [backend/target/]. Press any key to continue..." _continue
            exit 1
        fi
    else
        read -n 1 -p "Error: [mvn package] failed. Press any key to continue..." _continue
        exit 1
    fi
else
    read -n 1 -p "Error: [npm export] failed. Press any key to continue..." _continue
    exit 1
fi

echo "All commands completed successfully..."
exit 0