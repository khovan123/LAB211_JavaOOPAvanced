package repository;

import exception.IOException;
import model.Course;
import repository.interfaces.ICourseRepository;
import utils.GlobalUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements ICourseRepository {

    private static List<Course> courses = new ArrayList<>();
    private static final String path = "path/to/your/course/file.csv";
    //data sample: CS-YYYY, 30 days full body master, CA-YYYY

    @Override
    public List<Course> readFile() throws IOException {
        List<Course> courseList = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("-> File Not Found At Path - " + path);
            return courseList;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    boolean addventor = data[2].trim().equalsIgnoreCase("Y");
                    Course course = new Course(
                            data[0].trim(),
                            data[1].trim(),
                            addventor,
                            data[3].trim(),
                            Double.parseDouble(data[4].trim()),
                            data[5].trim(),
                            data[6].trim()
                    );
                    course.runValidate();
                    courseList.add(course);

                } catch (Exception e) {
                    System.err.println("-> Error While Adding - " + e.getMessage());
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("-> Error While Reading File - " + e.getMessage());
        }
        return courseList;
    }


    @Override
    public void writeFile(List<Course> courses) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
