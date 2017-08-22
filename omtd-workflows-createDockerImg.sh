#!/bin/bash

Dockerfile="./omtd-workflows.dockerfile"
DockerImg="omtd-workflows-docker"

docker build -f $Dockerfile -t $DockerImg .

