package quickstart.common.models;

public record SayHelloRequest(
    String name,
    boolean shouldFail,
    boolean invokeCustomerOnboardWorkflow
) {
    
}
