@echo off
kubectl apply -f app-configmap.yaml
@REM kubectl apply -f db-pvc.yaml
kubectl apply -f db-service.yaml
kubectl apply -f db-deployment.yaml

@REM kubectl apply -f app-deployment.yaml
@REM kubectl apply -f app-service.yaml
