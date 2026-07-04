package quickstart;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import quickstart.common.models.Customer;
import quickstart.workflowInterfaces.CustomerOnboardWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

public class StartCustomerOnboard {
    public static void main(String[] args) {
        String target = resolveTemporalTarget();
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(target)
                        .build());
        WorkflowClient client = WorkflowClient.newInstance(service);

        CustomerOnboardWorkflow customerOnboardWorkflow = client.newWorkflowStub(
                CustomerOnboardWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("my-task-queue")
                        .setWorkflowId("customer-onboard-workflow")
                        .build());

        // asynchronous workflow execution
        WorkflowExecution customerOnboardWorkflowExecution = WorkflowClient.start(customerOnboardWorkflow::onboard, "Aiko Chu");
        System.out.println("Started: " + customerOnboardWorkflowExecution.getWorkflowId() + ", with run ID: " + customerOnboardWorkflowExecution.getRunId());

        CountDownLatch shutdownLatch = new CountDownLatch(1);
        customerOnboardWorkflowResultListener(customerOnboardWorkflow, service, shutdownLatch);

        try {
            shutdownLatch.await();
            cleanUp(service);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void customerOnboardWorkflowResultListener(CustomerOnboardWorkflow customerOnboardWorkflow, WorkflowServiceStubs service, CountDownLatch shutdownLatch) {
        CompletableFuture<Customer> resultFuture = WorkflowStub.fromTyped(customerOnboardWorkflow)
                .getResultAsync(Customer.class);

        resultFuture.whenComplete((result, error) -> {
            System.out.println("Customer query: " + customerOnboardWorkflow.getCustomer().toString());
            if (error != null) {
                System.err.println("CustomerOnboardWorkflow failed: ");
                error.printStackTrace();
            } else {
                System.out.println("CustomerOnboardWorkflow completed: " + result.toString());
            }
            shutdownLatch.countDown();
        });
    }

    static String resolveTemporalTarget() {
        return System.getProperty("temporalTarget");
    }

    static void cleanUp(WorkflowServiceStubs service) {
        System.out.println("Shutting down Temporal service...");
        service.shutdown();
        service.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);
    }
}
