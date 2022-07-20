
secrets: secret-controller fe-secrets docker-secrets

all-backend: backend backend-service

start: secrets all-backend fe

secret-controller:
	kubectl apply -f secret-controller.yml

encrypt-fe-secret:
	kubeseal  --controller-namespace kube-system   --controller-name sealed-secrets-controller   --format yaml   < fe-secret.raw.yml   > fe-secret.yml

encrypt-docker-secret:
	kubeseal  --controller-namespace kube-system   --controller-name sealed-secrets-controller   --format yaml   < docker-secret.raw.yml   > docker-secret.yml

fe-secrets: encrypt-fe-secret
	kubectl apply -f fe-secret.yml

docker-secrets: encrypt-docker-secret
	kubectl apply -f docker-secret.yml

backend-service:
	kubectl apply -f service-backend.yml

backend:
	kubectl apply -f config-backend.yml
	kubectl apply -f backend.yml

fe:
	kubectl apply -f fe.yml

