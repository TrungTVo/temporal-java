package helloworkflow.workflowImpl;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

import helloworkflow.activityInterfaces.GreetActivities;
import helloworkflow.common.models.SayHelloRequest;
import helloworkflow.workflowInterfaces.SayHelloWorkflow;

public class SayHelloWorkflowImpl implements SayHelloWorkflow {

    private String greetingStatus = "Greeting has not started yet.";

    private final GreetActivities activities = Workflow.newActivityStub(
            GreetActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(15))
                    .setRetryOptions(
                            RetryOptions.newBuilder()
                                    .setMaximumAttempts(5)
                                    .build())
                    .build());

    @Override
    public String sayHello(SayHelloRequest request) {
        Workflow.sleep(Duration.ofSeconds(10));
        greetingStatus = "Greeting is in progress for " + request.name() + ".";
        try {
            String greeting = activities.greet(request.name(), request.shouldFail());
            greetingStatus = "Greeting completed for " + request.name() + ".";
            return greeting;
        } catch (RuntimeException e) {
            greetingStatus = "Greeting failed for " + request.name() + ".";
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 1. A Query handler must NOT modify Workflow state.
     * 2. CANNOT perform blocking operations such as executing an Activity
     * 3. The Query annotation accepts arguments (name, description)
     */
    @Override
    public String getGreetingStatus() {
        return greetingStatus;
    }

    /**
     * 1. The Signal handler can modify Workflow State but should NOT return a value. The response is sent immediately from the server, without waiting for the Workflow to process the Signal.
     * 2. The Signal annotation accepts arguments (name, description and unfinished_policy)
     * 3. Can be blocking. This allows you to use Activities, Child Workflows, durable Workflow.sleep Timers, Workflow.await, and more.
     */
    @Override
    public void setGreetingStatus(String greetingStatus) {
        this.greetingStatus = greetingStatus;
    }

}
