#!/usr/bin/env bash

 scp docker-compose.yaml ec2-user@3.64.26.65:/home/ec2-user
 docker-compose -f docker-compose.yaml up --detach
 echo "success"