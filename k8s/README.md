# Secret management

In this case I am using https://github.com/bitnami-labs/sealed-secrets .

Steps: 

* copy the latest kubeseal **controller manifest** https://github.com/bitnami-labs/sealed-secrets/releases/download/v0.18.1/controller.yaml
  * apply it
* create a new secret: 
  * ```kubectl create secret generic <NAME-OF-SECRET> --dry-run=client --output yaml --namespace default --from-literal='<SECRET-KEY>=<SECRET-VALUE>' > secret.raw.yml ```
    * this will create a new file `secret.raw.yml`
    * the secret-value will be in BASE64
    * once this is created it will be easier to just add more secrets
* Once you create the secret, you can encrypt it with
  * ```kubeseal   --controller-namespace kube-system   --controller-name sealed-secrets-controller   --format yaml   < secret.raw.yml   > secret.yml```
    * **controller-namespace** is defined in the **controller manifest** created before
    * **controller-name** is also defined in the **controller-manifest**
* To get the secret simply do
  * ```kubectl get secret <NAME-OF-SECRET>  -o yaml  > secret.raw.yml```
    * this command is a simple `kubectl` command that will show you the secrets (always in base64)

## Note: on k8s new setup
Of course when you create a new k8s instance (for instance when you do `minikube delete`) you need to seal the secrets again: your _controller_ will have a new pair of async keys, hence, your previous sealed secrets can not be decrypher anymore!


## For having the cluster using a private docker registry 

Simple: create a secret like this:
```yaml
apiVersion: v1
data:
  .dockerconfigjson: ewoJImF1dGhzIjogewoJCSJodHRwczovL2luZGV4LmRvY2tlci5pby92MS8iOiB7CgkJCSJhdXRoIjogIloybHZiR2wwYnpwblVpWm5iemt4SVE9PSIKCQl9Cgl9Cn0=
kind: Secret
metadata:
  creationTimestamp: "2022-07-19T16:00:56Z"
  managedFields:
    - apiVersion: v1
      fieldsType: FieldsV1
      fieldsV1:
        f:data:
          .: {}
          f:.dockerconfigjson: {}
        f:type: {}
      manager: kubectl-create
      operation: Update
      time: "2022-07-19T16:00:56Z"
  name: giolito-docker-secrets
  namespace: default
  resourceVersion: "42730"
  uid: 96dc90d5-f25e-41d4-92e9-75832b8ed229
type: kubernetes.io/dockerconfigjson
```
You can also encrypt it like we saw before ;) (tbp).
Then, as easy as it is, simply, add in your `deployment`, under `specs` the following line:
```yaml
      imagePullSecrets:
        - name: regcred
```