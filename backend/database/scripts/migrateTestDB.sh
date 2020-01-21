#!/bin/bash

DATABASE_URL="jdbc:mysql://localhost/scheduler_test?serverTimezone=UTC&useSSL=false"
DATABASE_USER=root
DATABASE_PASSWORD=

echo
cat <<EOT >> flyway.conf
flyway.url=$DATABASE_URL
flyway.user=$DATABASE_USER
flyway.password=$DATABASE_PASSWORD
flyway.locations=filesystem:database/migrations
flyway.jarDirs=database/drivers
EOT

flyway clean  -configFiles=flyway.conf
flyway migrate -configFiles=flyway.conf

rm flyway.conf
