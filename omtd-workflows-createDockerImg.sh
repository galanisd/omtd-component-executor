#!/bin/bash

Dockerfile="./omtd-workflows-executor.dockerfile"
DockerImg="omtd-workflows-executor"

docker build -f $Dockerfile -t $DockerImg .

