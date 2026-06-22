package helloworkflow.activityImpl;

import helloworkflow.activityInterfaces.GreetActivities;

public class GreetActivitiesImpl implements GreetActivities {

    @Override
    public String greet(String name, boolean shouldFail) {
        try {
            System.out.println("Greet Activity sleeping for 8 seconds...");
            Thread.sleep(8000);
            if (shouldFail) {
                throw new RuntimeException("Something went wrong!");
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
            throw new RuntimeException("Activity interrupted", e);
        }
        return "Hello World! Welcome, " + name;
    }

}
