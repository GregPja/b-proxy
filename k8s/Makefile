
secrets: secret-controller encrypt-fe-secret encrypt-docker-secret

all-backend: backend backend-service

start: secrets all-backend fe

secret-controller:
	kubectl apply -f secret-controller.yml

encrypt-fe-secret:
	kubeseal  --controller-namespace kube-system   --controller-name sealed-secrets-controller   --format yaml   < fe-secret.raw.yml   > secret.yml

encrypt-docker-secret:
	kubeseal  --controller-namespace kube-system   --controller-name sealed-secrets-controller   --format yaml   < fe-docker.raw.yml   > secret.yml


backend-service:
	kubectl apply -f service-backend

backend:
	kubectl apply -f config-backend.yml
	kubectl apply -f backend.yml

fe:
	kubectl apply -f fe.yml

