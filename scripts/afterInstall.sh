#!/bin/bash
REPOSITORY=/home/ubuntu/workspace
CONTAINER_NAME=genti-deploy
ECR_REGISTRY=058264505532.dkr.ecr.ap-northeast-2.amazonaws.com

mkdir -p $REPOSITORY && cd $REPOSITORY

echo "> PULL DOCKER IMAGE FROM ECR"
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin $ECR_REGISTRY

echo "> RUN APPLICATION CONTAINER"
docker-compose pull genti
docker-compose up -d