package repository;

import exception.IOException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import model.CourseSegment;
import repository.interfaces.ICourseSegmentRepository;

public class CourseSegmentRepository implements ICourseSegmentRepository {

    //data sample: CS-YYYY, 30 days full body master, CA-YYYY 
    static {

    }


    @Override
    public List<CourseSegment> readData() throws SQLException {
        return List.of();
    }

    @Override
    public void insertToDB(CourseSegment entry) throws SQLException {

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
