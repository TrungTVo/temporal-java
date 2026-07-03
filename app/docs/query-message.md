# Workflow query method

`SayHelloWorkflow` also declares a read-only query:

```java
@QueryMethod
String getGreetingStatus();
```

`SayHelloWorkflowImpl` updates an in-memory `greetingStatus` field as the workflow runs. While the activity is sleeping, `Starter` calls `workflow.getGreetingStatus()` and prints the current status.

Queries do not change workflow state or append workflow history events. They are useful when a client wants to inspect the current state of a running workflow without sending a signal or waiting for completion.

Queries can be sent from a Temporal Client or the Temporal CLI to a Workflow Execution, even if this Workflow has Completed. This call is synchronous and will call into the corresponding Query handler. You can also send a built-in "Stack Trace Query" for debugging.

## Querying from Temporal CLI

Sample:
```bash
temporal workflow query \
  --workflow-id say-hello-workflow \
  --type getGreetingStatus \
  --address localhost:7234


Query result:
  QueryResult  "Greeting has not started yet."
  QueryResult  "Greeting is in progress for Trung Vo."
  QueryResult  "Greeting completed/failed for Trung Vo."
```

## Stack Trace Query

> **Stack Trace Queries are available only for running Workflow Executions.**

```bash
temporal workflow stack \
  --workflow-id say-hello-workflow \
  --address localhost:7234
```

or

```bash
temporal workflow query \
  --workflow-id say-hello-workflow \
  --type __stack_trace \
  --address localhost:7234
```