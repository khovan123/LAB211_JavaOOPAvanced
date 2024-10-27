package repository;

import exception.IOException;
import model.RegisteredCourse;
import repository.interfaces.IRegistedCourseRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistedCourseRepository implements IRegistedCourseRepository {

    @Override
    public List<RegisteredCourse> readFile() {
        List<RegisteredCourse> registeredCourses = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("-> File not found at path: " + path);
            return registeredCourses;
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    RegisteredCourse registeredCourse = new RegisteredCourse(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim()
                    );
                    registeredCourse.validate();
                    registeredCourses.add(registeredCourse);
                } catch (Exception e) {
                    System.err.println("-> Error While Adding Registered Course - " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("-> Read file failed at path: " + path);
            System.err.println(e.getMessage());
        }
        return registeredCourses;
    }

    @Override
    public void writeFile(List<RegisteredCourse> entry) throws IOException {

    }
}
