The key mental model is:
- `Starter` asks Temporal to run work.
- `SayHelloWorker` performs that work.
- The Temporal Server coordinates them.

```
Starter
   │ starts workflow
   ▼
Temporal Server ──task queue──► SayHelloWorker
                                   │
                                   ├─ runs workflow code
                                   └─ runs activity code
```

# `Starter.java`: the client

Represents an application that wants something done.

- Connects to Temporal Server.
- Creates a typed client-side workflow stub.
- Starts a workflow.
- Queries the workflow status while it is running.
- Waits for the result.
- Prints the result and exits.

# Workflow query method

`SayHelloWorkflow` also declares a read-only query:

```java
@QueryMethod
String getGreetingStatus();
```

`SayHelloWorkflowImpl` updates an in-memory `greetingStatus` field as the workflow runs. While the activity is sleeping, `Starter` calls `workflow.getGreetingStatus()` and prints the current status.

Queries do not change workflow state or append workflow history events. They are useful when a client wants to inspect the current state of a running workflow without sending a signal or waiting for completion.

# `SayHelloWorker.java`: the worker process

Hosts your workflow and activity implementations.

- Connects to Temporal Server.
- Creates a `WorkerFactory`.
- Creates a worker that polls `my-task-queue`.
- Registers the code that the worker can execute.
- Starts polling for tasks.
- Remains running.

# Task queue

A task queue is a routing mechanism between Temporal Server and workers.

The names must match. If the starter uses `queue-A` while the worker polls `queue-B`, the workflow remains pending because no appropriate worker receives its tasks.
**A task queue does not normally store your business data or workflow state**. Temporal stores workflow state as event history. The task queue distributes work to available workers.

## Workflow tasks and activity tasks

There are two important task types here:
- A `Workflow Task` asks a worker to advance workflow logic.
- An `Activity Task` asks a worker to perform an activity.

Because the activity stub does not specify a separate task queue, its activity tasks use the workflow’s task queue, `my-task-queue`.
Thus this one worker handles both kinds because it registered both implementations.
Task queues also provide **load balancing**. If you run three identical workers polling the same queue:
```
                  ┌─ Worker 1
my-task-queue ────┼─ Worker 2
                  └─ Worker 3
```

**Temporal distributes tasks among them. A particular task goes to one worker, not all three.**