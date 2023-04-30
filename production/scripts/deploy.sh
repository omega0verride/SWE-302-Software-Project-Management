#!/bin/bash
source env.sh
# replace env.sh with locally stored env.sh (we do not want to include it in git, yes gitignore works but cannot trust anyone that edits it)
rm -f ../../production.tar
tar cf ../../production.tar ../
ssh -i $SSH_KEY_PATH $USER@$HOST rm -f ~/production.tar; rm -rf ~/production
scp -i $SSH_KEY_PATH ../../production.tar $USER@$HOST:~/production.tar
ssh -i $SSH_KEY_PATH $USER@$HOST "mkdir ~/production; tar xvf ~/production.tar -C ~/production; cd ~/production/scripts/; chmod +x startup.sh; ./startup.sh"
# add startup.sh to cron
# delete the zip