package repository;

import exception.IOException;
import model.Course;
import model.Workout;
import repository.interfaces.ICourseRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements ICourseRepository {

    @Override
    public List<Course> readFile() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File Not Found - " + path);
        }

        List<Course> courseList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    Course course = new Course(
                            data[0],
                            data[1],
                            data[2]);
                    courseList.add(course);
                } catch (Exception e) {
                    throw new IOException("-> Error While Adding Course " + e.getMessage());
                }
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("-> Error While Reading Course Data " + e.getMessage());
        }
        return courseList;
    }

    @Override
    public void writeFile(List<Course> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
