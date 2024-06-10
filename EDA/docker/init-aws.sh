#!/bin/bash

awslocal sqs create-queue --queue-name my-queue

awslocal sns create-topic --name my-topic

awslocal sns subscribe \
    --topic-arn "arn:aws:sns:us-east-1:000000000000:my-topic" \
    --protocol sqs \
    --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:my-queue"




## awslocal sns publish --topic-arn "arn:aws:sns:us-east-1:000000000000:my-topic" --message "hello"
## awslocal sqs receive-message --queue-url "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue"
