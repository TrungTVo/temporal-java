package quickstart;

import quickstart.activityImpl.GreetActivitiesImpl;
import quickstart.workflowImpl.CustomerOnboardWorkflowImpl;
import quickstart.workflowImpl.SayHelloWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class MyWorker {

    public static void main(String[] args) {

        String target = resolveTemporalTarget();
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(target)
                        .build());
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker("my-task-queue");
        worker.registerWorkflowImplementationTypes(SayHelloWorkflowImpl.class, CustomerOnboardWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GreetActivitiesImpl());

        System.out.println("Starting MyWorker for task queue 'my-task-queue' at " + target + "...");

        factory.start();

    }

    static String resolveTemporalTarget() {
        return System.getProperty("temporalTarget");
    }

}
