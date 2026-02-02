#!/bin/bash
echo "########### Criando S3 Bucket ###########"
awslocal s3 mb s3://bucket-videos
echo "########### Bucket criado com sucesso ###########"