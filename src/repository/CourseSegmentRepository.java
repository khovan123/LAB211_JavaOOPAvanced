package repository;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import model.CourseSegment;
import repository.interfaces.ICourseSegmentRepository;

public class CourseSegmentRepository implements ICourseSegmentRepository {

    private static List<CourseSegment> courseSegments = new ArrayList<>();
    private static WorkoutRepository workoutRepository = new WorkoutRepository();
    private static CoachRepository coachRepository = new CoachRepository();
    //data sample: CS-YYYY, 30 days full body master, CA-YYYY 

    static {

    }

    public List<CourseSegment> getCourseSegments() {
        return courseSegments;
    }

    @Override
    public void addFromDatabase() throws EventException {
        try {
            throw new UnsupportedOperationException("Not supported yet.");
        } catch (Exception e) {
            System.out.println("-> Error While Adding From Database - " + e.getMessage());
        }
    }

    @Override
    public List<CourseSegment> readFile() throws IOException {
        try {

            throw new UnsupportedOperationException("Not supported yet.");
        } catch (Exception e) {
            System.out.println("-> Error While Reading File - " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<CourseSegment> courseSegments) throws IOException {
        try {

            throw new UnsupportedOperationException("Not supported yet.");
        } catch (Exception e) {
            System.out.println("-> Error While Writing File - " + e.getMessage());
        }
    }

    @Override
    public void add(CourseSegment courseSegment) throws EventException {
        try {
            // Check coachId in courseSegments contains in coachRepository
            if (!coachRepository.getCoachs().stream().anyMatch(coach -> coach.getCoachId().equals(courseSegment.getCoachId()))) {
                throw new InvalidDataException("-> Coach ID Does Not Exist - " + courseSegment.getCoachId());
            }

            // Check courseSegmentId in workoutRepository contains in courseSegments
            if (!workoutRepository.getWorkouts().stream().anyMatch(workout -> workout.getWorkoutId().equals(courseSegment.getCourseId()))) {
                System.out.println("-> Workout ID Does Not Exist - " + courseSegment.getCourseId());
            }

            if (!courseSegment.getCourseId().matches("CS-\\d{4}")) {
                System.out.println("-> Invalid Course Segment ID Format: " + courseSegment.getCourseId());
            }

            courseSegments.add(courseSegment);
            System.out.println("-> Course Added Successfully.");
        } catch (Exception e) {
            System.out.println("-> Error While Adding Course Segment - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException {
        try {
            boolean removed = courseSegments.removeIf(courseSegment -> courseSegment.getCourseId().equalsIgnoreCase(id));
            if (removed) {
                System.out.println("-> Course Segment With ID " + id + " Has Been Removed");
            } else {
                System.out.println("-> Course Segment With ID " + id + " Not Found");
            }
        } catch (Exception e) {
            System.out.println("-> Error While Deleting ID - " + id + " " + e.getMessage());
        }
    }

    @Override
    public CourseSegment search(Predicate<CourseSegment> predicate) throws NotFoundException {
        try {
            for (CourseSegment courseSegment : courseSegments) {
                if (predicate.test(courseSegment)) {
                    return courseSegment;
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Searching Course Segment - " + e.getMessage());
        }
        return null;
    }


    @Override
    public CourseSegment filter(String entry, String regex) throws InvalidDataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
