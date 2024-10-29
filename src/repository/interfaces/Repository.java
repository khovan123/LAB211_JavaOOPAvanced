package repository.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Repository<T, C> {

    C readData();

    void insertToDB(T entry) throws SQLException;

    void updateToDB(String id, Map<String, Object> entry) throws SQLException;

    void deleteToDB(String ID) throws SQLException;

    List<String> getMany() throws SQLException;

    void insertOne(Map<String, String> entry) throws SQLException;

    void updateOne(String ID, Map<String, String> entry) throws SQLException;

    void deleteOne(String ID) throws SQLException;

}
