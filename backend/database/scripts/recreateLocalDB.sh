#!/bin/bash

DATABASE_USER=root

mysql -u${DATABASE_USER} -e "drop database scheduler;"
mysql -u${DATABASE_USER} -P3306 -e "create database scheduler;"
