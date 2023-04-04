#!/bin/bash
# this scripts makes the container waits until postgres is ready 
set -e
cmd="$@"

postgres

until psql -U "postgres" -c '\l'; do
  >&2 echo "Postgres is unavailable - The database system is starting up..."
  sleep 1
done

>&2 echo "Postgres is up - executing command: [$cmd]"
exec $cmd