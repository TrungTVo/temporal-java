# Explore Temporal Java

## Start Temporal Server
```bash
temporal server start-dev --ui-port 8233 --metrics-port 57271 --port 7234
```
Default temporal service port is `7233`

## Run the Worker
```bash
./gradlew runWorker -PtemporalTarget=localhost:7234
```

## Run the Sample Workflow
```bash
./gradlew runStarter -PtemporalTarget=localhost:7234
```
The starter starts the workflow asynchronously, then calls the workflow query method:
```text
Greeting status query: Greeting is in progress for Trung Vo.
```
This query is declared with `@QueryMethod` on the workflow interface. It reads workflow state without changing the workflow history.

To demonstrate `retries` and `failed` scenario, set the `shouldFail` flag to `true`:
```bash
./gradlew runStarter -PtemporalTarget=localhost:7234 --args="--shouldFail=true"
```

## Check workers list
```bash
temporal worker list --address localhost:7234 --namespace default
```
