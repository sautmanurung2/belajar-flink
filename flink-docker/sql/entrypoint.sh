#!/bin/bash
set -e

# Run the default PostgreSQL entrypoint
docker-entrypoint.sh postgres &

# Wait for PostgreSQL to start
until pg_isready -h localhost -p 5432 -U "$POSTGRES_USER"; do
    echo "Waiting for PostgreSQL to start..."
    sleep 2
done

# Run the init.sql file
psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/init.sql

# Wait to keep the container running
wait