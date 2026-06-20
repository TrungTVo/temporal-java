package helloworkflow;

import helloworkflow.activityImpl.GreetActivitiesImpl;
import helloworkflow.workflowImpl.SayHelloWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class SayHelloWorker {

    public static void main(String[] args) {

        String target = resolveTemporalTarget();
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(target)
                        .build());
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker("my-task-queue");
        worker.registerWorkflowImplementationTypes(SayHelloWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GreetActivitiesImpl());

        System.out.println("Starting SayHelloWorker for task queue 'my-task-queue' at " + target + "...");

        factory.start();

    }

    static String resolveTemporalTarget() {
        return System.getProperty(
                "temporal.target",
                System.getenv().getOrDefault("TEMPORAL_ADDRESS", "localhost:7234"));
    }

}
