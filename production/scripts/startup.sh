#!/bin/bash
current_shell_file_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd $current_shell_file_path
ENV_FILE_PATH=../env.sh 
if [[ "$1" == "-l" ]]; then
    source ./env.sh
    ENV_FILE_PATH=$PRODUCTION_ENV_FILEPAHT
fi
docker-compose --env-file $ENV_FILE_PATH  up --build --force-recreate