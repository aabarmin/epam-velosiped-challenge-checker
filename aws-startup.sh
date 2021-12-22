#!/bin/sh
## Verbose output
set -o xtrace

## Installing Corretto JDK 11
sudo yum install -y java-11-amazon-corretto

## Creating dirs
cd /opt
mkdir app
cd app

## Downloading sources from S3
aws s3 cp s3://epam-velosiped-challenge/checker-1.0.jar app.jar

## Running the app
java -jar app.jar --server.port=8080 --github.access-token=<put-token-here>