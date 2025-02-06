@echo off
kubectl delete service --all
kubectl delete deployments --all
kubectl delete pods --all