# Explore Temporal Java

## Start Temporal Server
```bash
temporal server start-dev --ui-port 8233 --metrics-port 57271 --port 7234
```

## Run the Worker
```bash
./gradlew runWorker
```

## Run the Sample Workflow
```bash
./gradlew runStarter
```

## Check workers list
```bash
temporal worker list --address localhost:7234 --namespace default
```
