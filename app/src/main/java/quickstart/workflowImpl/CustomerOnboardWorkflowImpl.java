package quickstart.workflowImpl;

import quickstart.common.models.Customer;
import quickstart.workflowInterfaces.CustomerOnboardWorkflow;
import io.temporal.workflow.Workflow;

public class CustomerOnboardWorkflowImpl implements CustomerOnboardWorkflow {
    
    private Customer customer;

    @Override
    public Customer onboard(String name) {
        this.customer = new Customer(null, name);
        Workflow.await(() -> this.customer.id() != null);
        return this.customer;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public void updateCustomerId(String id) {
        this.customer = new Customer(id, this.customer.name());
    }

}
