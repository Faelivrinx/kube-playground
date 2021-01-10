#!/bin/sh

kubectl delete deploy/payment-service
kubectl delete deploy/message-service
kubectl delete svc/payment-service
kubectl delete svc/message-service

sleep 5

if [ "$#" -eq  "0" ]
then
  ./mvnw clean package
fi

eval $(minikube docker-env)

echo "Starting deploy payment service..."
cd payment || exit
docker rmi payment-service
sleep 1
docker build -t payment-service .
kubectl apply -f deployment.yml
cd ..

cd messaging || exit
pwd
docker rmi message-service
sleep 1
docker build -t message-service .
kubectl apply -f deployment.yml

echo "Deploy finished"