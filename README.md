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
To demonstrate `retries` and `failed` scenario, set the `shouldFail` flag to `true`:
```bash
./gradlew runStarter -PtemporalTarget=localhost:7234 --args="--shouldFail=true"
```

## Check workers list
```bash
temporal worker list --address localhost:7234 --namespace default
```

# Other use cases

- [`asynchronous-workflow`](https://github.com/TrungTVo/temporal-java/tree/asynchronous-workflow): Asynchronous workflow execution example.
- [`query-message`](https://github.com/TrungTVo/temporal-java/tree/query-message): Use query message to read the current state of the workflow as it is running or after it has completed.