package repository;

import exception.IOException;
import model.CourseCombo;
import repository.interfaces.ICourseComboRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CourseComboRepository implements ICourseComboRepository {
    public List<CourseCombo> readFile() {
        List<CourseCombo> courseCombos = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("-> File not found at path: " + path);
            return courseCombos;
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bf.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    CourseCombo courseCombo = new CourseCombo(
                            data[1].trim(),
                            data[2].trim(),
                            (data[3].trim())
                    );
                    courseCombo.validate();
                    courseCombos.add(courseCombo);
                } catch (Exception e) {
                    System.err.println("-> Error While Adding Course Combo - " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("-> Read file failed at path: " + path);
            System.err.println(e.getMessage());
        }
        return courseCombos;
    }

    @Override
    public void writeFile(List<CourseCombo> entry) throws IOException {

    }
}
