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

    public List<RegisteredCourse> readFile() throws IOException {
        List<RegisteredCourse> registeredCours = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File not found");
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    RegisteredCourse registeredCourse = new RegisteredCourse(
                            data[1],
                            LocalDate.parse(data[2]),
                            LocalDate.parse(data[3]),
                            data[4],
                            data[5]
                    );
                    registeredCours.add(registeredCourse);
                } catch (Exception e) {
                    throw new IOException("-> Error While Adding - " + e.getMessage());
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("-> Error While Reading File - " + e.getMessage());
        }
        return registeredCours;
    }

    @Override
    public void writeFile(List<RegisteredCourse> entry) throws IOException {

    }
}
