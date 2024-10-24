package repository;

import exception.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import model.CourseSegment;
import model.Workout;
import repository.interfaces.ICourseSegmentRepository;

public class CourseSegmentRepository implements ICourseSegmentRepository {

    //data sample: CS-YYYY, 30 days full body master, CA-YYYY 
    static {

    }

    @Override
    public List<CourseSegment> readFile() throws IOException {
        List<CourseSegment> courseSegmentList = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File Not Found - " + path);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                List<Workout> workoutList = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    workoutList.add(new Workout(data[i]));
                }
                try {
                    CourseSegment courseSegment = new CourseSegment(
                            data[0],
                            data[1],
                            data[2],
                            workoutList
                    );
                    courseSegmentList.add(courseSegment);
                } catch (Exception e) {
                    throw new IOException("-> Error While Adding Course Segment - " + e.getMessage());
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("-> Error While Reading File - " + e.getMessage());
        }
        return courseSegmentList;
    }

    @Override
    public void writeFile(List<CourseSegment> courseSegments) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
