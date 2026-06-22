package helloworkflow.workflowImpl;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

import helloworkflow.activityInterfaces.GreetActivities;
import helloworkflow.common.models.SayHelloRequest;
import helloworkflow.workflowInterfaces.SayHelloWorkflow;

public class SayHelloWorkflowImpl implements SayHelloWorkflow {

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
        return activities.greet(request.name(), request.shouldFail());
    }

}
