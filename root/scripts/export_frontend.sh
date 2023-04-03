#!/bin/bash
source env.sh

echo "[1/3] Exportig fronted..."
cd ../frontend && npm install --legacy-peer-deps && npm run export
if [ $? -eq 0 ]; then
  echo "[1/3] Building backend jar..."
  cd ../backend && mvn package
  if [ $? -eq 0 ]; then
    scp -i $SSH_KEY_PATH -r ../frontend/out/* $USER@$HOST:/var/www/html/
    if [ $? -ne 0 ]; then
      read -n 1 -p "Error: SCP failed. Press any key to continue..." _continue
      exit 1
    fi
  else
    read -n 1 -p "Error: SSH delete failed. Press any key to continue..." _continue
    exit 1
  fi
else
  read -n 1 -p "Error: npm export failed. Press any key to continue..." _continue
  exit 1
fi

echo "All commands completed successfully..."
exit 0