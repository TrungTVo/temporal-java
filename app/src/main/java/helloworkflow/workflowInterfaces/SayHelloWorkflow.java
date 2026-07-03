package helloworkflow.workflowInterfaces;

import helloworkflow.common.models.SayHelloRequest;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SayHelloWorkflow {

    @WorkflowMethod
    String sayHello(SayHelloRequest request);

    @QueryMethod
    String getGreetingStatus();
}
