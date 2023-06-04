# SWE-302-Software-Project-Management

To run/deploy the project you can use the scripts located in production/scripts

Note that the project will not run withoud defining some environmant variables.
startup.sh and deploy.sh refer to ./env.sh to get the path to the production env.sh file
i.e:

env.sh
```
export PRODUCTION_ENV_FILEPAHT=~/SWE-302/env.sh
```

The production env file can contain the following variables:
~/SWE-302/env.sh
```
# ssh credentials
export SSH_KEY_PATH=$CURRENT_SHELL_FILE_PATH/id_rsa
export HOST=75.119.149.135
export USER=root
# non sensitive values
export DATABASE_VOLUME=~/database-data
export DATABASE_NAME=redscooter
export DATABASE_PORT=5432
# - FileStore - #
export FILE_STORE_ROOT_PERSIST_PATH=~/filestore
export FILE_STORE_PUBLIC_ROOT_PERSIST_PATH=~/filestore/public
export FILE_STORE_PUBLIC_ROOT_URI=/resources/public
# - FileStore - #
# -
export DATABASE_USER=admin
export DATABASE_PASSWORD=admin
export SMTP_USERNAME=redscooter.al@gmail.com
export SMTP_PASSWORD=MY_SMTP_PASSWORD
export JWT_SECRET='RANDROM_SECRET_W&gN*^eBp*Yb?bAkzz_@Vh?=3jTL^UAR?'

```


## Deployment Diagram
![deployment_diagram](https://github.com/omega0verride/SWE-302-Software-Project-Management/assets/64291401/ab875351-6252-4155-821a-b3136f912339)


*on the latest updates, backend uses "RestProcessors" (@DynamicRestMapping), created by ibreti20@epoka.edu.al for his graduation project. This dependency is not included and is not available from public maven repositories.
