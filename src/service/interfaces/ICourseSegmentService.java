package service.interfaces;

import java.util.function.Predicate;
import model.CourseSegment;
import model.Workout;

public interface ICourseSegmentService extends Service<CourseSegment> {

    void addWorkout(Workout workout);

    void updateWorkout(Workout workout);

    void deleteWorkout(Workout workout);

    Workout searchWorkout(Predicate<Workout> p);

}
