@echo off
kubectl apply -f configmap.yaml
kubectl apply -f db-pvc.yaml
kubectl apply -f db-deployment.yaml
kubectl apply -f db-service.yaml
