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

# Send Signal from one Workflow to external Workflow

## 1. Start `CustomerOnboardWorkflow` first
```bash
./gradlew runCustomerOnboard -PtemporalTarget=localhost:7234
```

This workflow will wait until customer ID is NOT null
```java
Workflow.await(() -> this.customer.id() != null);
```

To make this happen, we will send a signal from `SayHelloWorkflow` to `CustomerOnboardWorkflow` to update the customer ID.

## 2. Start `SayHelloWorkflow` with `invokeCustomerOnboardWorkflow` flag set to `true`
```bash
./gradlew runSayHello -PtemporalTarget=localhost:7234 --args="--invokeCustomerOnboardWorkflow=true"
```

Once `SayHelloWorkflow` is started, it will send a signal to `CustomerOnboardWorkflow` to update the customer ID. The `CustomerOnboardWorkflow` will then continue its execution and complete.