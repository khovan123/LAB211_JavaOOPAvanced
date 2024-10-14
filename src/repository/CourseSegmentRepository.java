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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CourseSegment> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<CourseSegment> courseSegments) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(CourseSegment courseSegment) throws EventException {
        //check coachId in courseSegments contains in coachRepository
        //check courseSegmentId in workoutRepository contains in courseSegments
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) throws EventException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CourseSegment search(Predicate<CourseSegment> p) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CourseSegment filter(String entry, String regex) throws InvalidDataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
