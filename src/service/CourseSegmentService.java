package service;

import exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.CourseSegment;
import model.Workout;
import repository.CourseSegmentRepository;
import service.interfaces.ICourseSegmentService;

public class CourseSegmentService implements ICourseSegmentService {

    private final CourseSegmentRepository courseSegmentRepository = new CourseSegmentRepository();
    private final List<CourseSegment> courseSegmentList;

    public CourseSegmentService() {
        courseSegmentList = new ArrayList<>();
        readFromDatabase();
    }

    public CourseSegmentService(List<CourseSegment> courseSegmentList) {
        this.courseSegmentList = courseSegmentList;
        readFromDatabase();
    }

    @Override
    public void display() throws EmptyDataException {
        if (courseSegmentList.isEmpty()) {
            throw new EmptyDataException("-> Course Segment List Is Empty. ");
        } else {
            for (CourseSegment courseSegment : courseSegmentList) {
                System.out.println(courseSegment);
            }
        }
    }

    public void readFromDatabase() {
        try {
            courseSegmentList.addAll(courseSegmentRepository.readFile());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void add(CourseSegment courseSegment) throws EventException {
        if (courseSegment == null) {
            throw new EventException("-> Course Segment Cannot Be Null.");
        }
        if (!courseSegment.getCourseId().matches("CS-\\d{4}")) {
            throw new EventException("-> Invalid Course Segment ID Format: " + courseSegment.getCourseId() + " - Must Be CS-YYYY");
        }
        if (existsID(courseSegment)) {
            throw new EventException("-> Course Segment With ID - " + courseSegment.getCourseId() + " - Already Exist");
        }
        try {
            courseSegmentList.add(courseSegment);
            System.out.println("-> Course Segment Add Successfully!");
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Add Course Segment - " + e.getMessage());
        }
    }


    @Override
    public void delete(String id) throws EventException, NotFoundException {
        if (id == null || id.trim().isEmpty()) {
            throw new EventException("-> Course Segment ID Cannot Be Null Or Empty.");
        }
        CourseSegment segmentToRemove = findById(id);
        if (segmentToRemove != null) {
            courseSegmentList.remove(segmentToRemove);
            System.out.println("-> Course Segment With ID - " + id + " - Remove Successfully");
        } else {
            throw new EventException("-> Course Segment with ID " + id + " not found.");
        }
    }


    @Override
    public void update(CourseSegment courseSegment) throws EventException, NotFoundException {
        if (courseSegment == null) {
            throw new EventException("-> Course Segment Cannot Be Null.");
        }
        CourseSegment existCourse = findById(courseSegment.getCourseId());
        if (existCourse == null) {
            throw new NotFoundException("-> Course Segment With ID - " + courseSegment.getCourseId() + " - Not Found.");
        }
        try {
            courseSegmentList.remove(existCourse);
            courseSegmentList.add(courseSegment);
            System.out.println("-> Update Course Segment - " + courseSegment.getCourseId() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Updating - " + courseSegment.getCourseId());
        }
    }

    @Override
    public CourseSegment search(Predicate<CourseSegment> p) throws NotFoundException {
        for (CourseSegment courseSegment : courseSegmentList) {
            if (p.test(courseSegment)) {
                return courseSegment;
            }
        }
        throw new NotFoundException("-> No Course Segment found matching the criteria.");
    }

    @Override
    public CourseSegment findById(String id) throws NotFoundException {
        for (CourseSegment courseSegment : courseSegmentList) {
            if (courseSegment.getCourseId().equalsIgnoreCase(id)) {
                return courseSegment;
            }
        }
        throw new NotFoundException("-> Course Segment With ID - " + id + " - Not Found.");
    }

    public boolean existsID(CourseSegment courseSegment) {
        try {
            return findById(courseSegment.getCourseId()) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

    @Override
    public void addWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Workout searchWorkout(Predicate<Workout> p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
