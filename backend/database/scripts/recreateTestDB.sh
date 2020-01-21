#!/bin/bash

DATABASE_USER=root

mysql -u${DATABASE_USER} -e "drop database scheduler_test;"
mysql -u${DATABASE_USER} -e "create database scheduler_test;"
