package quickstart.workflowInterfaces;

import quickstart.common.models.Customer;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CustomerOnboardWorkflow {

    @WorkflowMethod
    Customer onboard(String name);

    @QueryMethod
    Customer getCustomer();

    @SignalMethod
    void updateCustomerId(String id);

}
