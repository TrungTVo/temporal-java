package helloworkflow.activityInterfaces;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GreetActivities {

    @ActivityMethod
    String greet(String name, boolean shouldFail);

}
