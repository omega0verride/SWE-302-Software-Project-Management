#!/bin/bash
ENV_FILE_PATH=../env.sh 
if [[ "$1" == "-l" ]]; then
    source ./env.sh
    ENV_FILE_PATH=$PRODUCTION_ENV_FILEPAHT
fi
echo $ENV_FILE_PATH
docker-compose --env-file $ENV_FILE_PATH  up --build --force-recreate