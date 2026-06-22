package helloworkflow.common.models;

public record SayHelloRequest(
    String name,
    boolean shouldFail
) {
    
}
