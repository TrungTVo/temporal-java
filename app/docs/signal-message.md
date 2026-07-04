# Workflow signal method

```java
/**
 * 1. The Signal handler can modify Workflow State but should NOT return a value. The response is sent immediately from the server, without waiting for the Workflow to process the Signal.
 * 2. The Signal annotation accepts arguments (name, description and unfinished_policy)
 * 3. Can be blocking. This allows you to use Activities, Child Workflows, durable Workflow.sleep Timers, Workflow.await, and more.
*/
@SignalMethod
void setGreetingStatus(String greetingStatus);
```

After `SayHelloWorkflow` started, `greetingStatus` is initially set to `"Greeting has not started yet."`. The `Starter` calls `workflow.setGreetingStatus("Greeting is forced to be in progress now!")` to send a signal to the workflow. The workflow will update its state and the next query will return the new value.

Output:
```
1. Greeting status query: Greeting has not started yet.
2. Greeting status query: Greeting is forced to be in progress now!   ->  send signal to the workflow
3. Greeting status query: Greeting is in progress for Trung Vo.
4. Greeting status query: Greeting completed/failed for Trung Vo.
```