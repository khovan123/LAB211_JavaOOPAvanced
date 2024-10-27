package repository;

import exception.IOException;
import model.RegistedCourse;
import repository.interfaces.IRegistedCourseRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistedCourseRepository implements IRegistedCourseRepository {

    public List<RegistedCourse> readFile() throws IOException {
        List<RegistedCourse> registedCourses = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File not found");
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    RegistedCourse registedCourse = new RegistedCourse(
                            data[1],
                            LocalDate.parse(data[2]),
                            LocalDate.parse(data[3]),
                            data[4],
                            data[5]
                    );
                    registedCourses.add(registedCourse);
                } catch (Exception e) {
                    throw new IOException("-> Error While Adding - " + e.getMessage());
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("-> Error While Reading File - " + e.getMessage());
        }
        return registedCourses;
    }

    @Override
    public void writeFile(List<RegistedCourse> entry) throws IOException {

    }
}