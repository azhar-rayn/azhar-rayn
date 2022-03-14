#!/bin/bash

aws cloudformation create-stack --stack-name zindagi-api-network --template-body file://network.yml --capabilities CAPABILITY_IAM

aws cloudformation create-stack --stack-name zindagi-api-db --template-body file://database.yml --capabilities --parameters ParameterKey=DBName,ParameterValue=zindagi ParameterKey=NetworkStackName,ParameterValue=zindagi-api-network ParameterKey=DBUsername,ParameterValue=zind

aws cloudformation create-stack --stack-name zindagi-api-service --template-body file://service.yml --parameters ParameterKey=NetworkStackName,ParameterValue=zindagi-api-network ParameterKey=ServiceName,ParameterValue=zindagi-api ParameterKey=ImageUrl,ParameterValue=374947411078.dkr.ecr.us-east-1.amazonaws.com/zindagi:latest ParameterKey=ContainerPort,ParameterValue=8080 ParameterKey=HealthCheckIntervalSeconds,ParameterValue=100 ParameterKey=DatabaseStackName,ParameterValue=zindagi-api-db;
