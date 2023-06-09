#!/bin/bash
source ./env.sh
export CURRENT_SHELL_FILE_PATH="$(dirname "$PRODUCTION_ENV_FILEPAHT")"
source $PRODUCTION_ENV_FILEPAHT
# replace env.sh with locally stored env.sh (we do not want to include it in git, yes gitignore works but cannot trust anyone that edits it)
rm -f ../../production.tar
tar cf ../../production.tar ../
ssh -i $SSH_KEY_PATH $USER@$HOST rm -f ~/production.tar; rm -rf ~/production
scp -i $SSH_KEY_PATH ../../production.tar $USER@$HOST:~/production.tar
rm -f ../../production.tar
ssh -i $SSH_KEY_PATH $USER@$HOST "mkdir ~/production; tar xvf ~/production.tar -C ~/production;"
scp -i $SSH_KEY_PATH  $PRODUCTION_ENV_FILEPAHT $USER@$HOST:~/production/env.sh
ssh -i $SSH_KEY_PATH $USER@$HOST "cd ~/production/scripts/; sed -i -e 's/\r$//' startup.sh; chmod +x startup.sh; ./startup.sh"
# add startup.sh to cron
# delete the zip
