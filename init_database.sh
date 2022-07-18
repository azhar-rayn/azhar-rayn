#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER root;
    CREATE DATABASE dost;
    GRANT ALL PRIVILEGES ON DATABASE dost TO root;
EOSQL