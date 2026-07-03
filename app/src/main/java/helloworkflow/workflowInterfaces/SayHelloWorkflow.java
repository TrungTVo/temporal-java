package helloworkflow.workflowInterfaces;

import helloworkflow.common.models.SayHelloRequest;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SayHelloWorkflow {

    @WorkflowMethod
    String sayHello(SayHelloRequest request);

    /**
     * 1. A Query handler must NOT modify Workflow state.
     * 2. CANNOT perform blocking operations such as executing an Activity
     */
    @QueryMethod
    String getGreetingStatus();
}
