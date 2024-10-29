package repository;

import exception.IOException;
import model.Course;
import repository.interfaces.ICourseRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CourseRepository implements ICourseRepository {

    @Override
    public List<Course> readData() throws SQLException {
        return List.of();
    }

    @Override
    public void insertToDB(Course entry) throws SQLException {

    }

    @Override
    public void updateToDB(String id, Map<String, Object> entry) throws SQLException {

    }

    @Override
    public void deleteToDB(String ID) throws SQLException {

    }

    @Override
    public List<String> getMany() throws SQLException {
        return List.of();
    }

    @Override
    public void insertOne(Map<String, String> entry) throws SQLException {

    }

    @Override
    public void updateOne(String ID, Map<String, String> entry) throws SQLException {

    }

    @Override
    public void deleteOne(String ID) throws SQLException {

    }
}
