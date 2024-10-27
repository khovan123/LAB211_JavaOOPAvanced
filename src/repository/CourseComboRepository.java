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
    @Override
    public List<CourseCombo> readFile() throws IOException {
        List<CourseCombo> courseCombos = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File Not Found At Path - " + path);
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    CourseCombo courseCombo = new CourseCombo(
                            data[1],
                            data[2],
                            Double.parseDouble(data[3])
                    );
                    courseCombo.runValidate();
                    courseCombos.add(courseCombo);
                } catch (Exception e) {
                    throw new IOException("-> Error While Adding Course Combo - " + e.getMessage());
                }
            }

        } catch (java.io.IOException e) {
            throw new IOException("-> Error While Reading File - " + e.getMessage());
        }
        return courseCombos;
    }

    @Override
    public void writeFile(List<CourseCombo> entry) throws IOException {

    }
}
