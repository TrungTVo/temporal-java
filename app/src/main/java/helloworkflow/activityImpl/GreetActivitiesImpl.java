package helloworkflow.activityImpl;

import helloworkflow.activityInterfaces.GreetActivities;

public class GreetActivitiesImpl implements GreetActivities {

    @Override
    public String greet(String name) {
      return "Hello " + name;
    }
    
}
