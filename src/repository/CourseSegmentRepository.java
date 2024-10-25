package repository;

import exception.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import model.Course;
import repository.interfaces.ICourseSegmentRepository;

public class CourseSegmentRepository implements ICourseSegmentRepository {

    private static List<Course> courses = new ArrayList<>();

    //data sample: CS-YYYY, 30 days full body master, CA-YYYY

    @Override
    public List<Course> readFile() throws IOException {
        List<Course> courseList = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File Not Found - " + path);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    Course course = new Course(
                            data[0],
                            data[1],
                            data[2]
                    );
                    courseList.add(course);
                } catch (Exception e) {
                    throw new IOException("-> Error While Adding Course Segment - " + e.getMessage());
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("-> Error While Reading File - " + e.getMessage());
        }
        return courseList;
    }

    @Override
    public void writeFile(List<Course> courses) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
