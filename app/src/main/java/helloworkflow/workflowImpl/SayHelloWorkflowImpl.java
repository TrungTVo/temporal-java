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

    private final GreetActivities greetActivities = Workflow.newActivityStub(
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
            String greeting = greetActivities.greet(request.name(), request.shouldFail());
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
     */
    @Override
    public String getGreetingStatus() {
        return greetingStatus;
    }

}
