package helloworkflow.workflowInterfaces;

import helloworkflow.common.models.SayHelloRequest;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SayHelloWorkflow {

    @WorkflowMethod
    String sayHello(SayHelloRequest request);

    /**
     * 1. A Query handler must NOT modify Workflow state.
     * 2. CANNOT perform blocking operations such as executing an Activity
     * 3. The Query annotation accepts arguments (name, description)
     */
    @QueryMethod
    String getGreetingStatus();

    /**
     * 1. The Signal handler can modify Workflow State but should NOT return a value. The response is sent immediately from the server, without waiting for the Workflow to process the Signal.
     * 2. The Signal annotation accepts arguments (name, description and unfinished_policy)
     * 3. Can be blocking. This allows you to use Activities, Child Workflows, durable Workflow.sleep Timers, Workflow.await, and more.
     */
    @SignalMethod
    void setGreetingStatus(String greetingStatus);
}
