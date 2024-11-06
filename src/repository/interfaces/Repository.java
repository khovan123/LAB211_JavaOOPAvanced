package repository.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Repository<T, C> {

    C readData() throws SQLException;

    void insertToDB(T entry) throws SQLException;

    void updateToDB(int id, Map<String, Object> entry) throws SQLException;

    void deleteToDB(int ID) throws SQLException;

    List<String> getMany() throws SQLException;

    void insertOne(Map<String, Object> entry) throws SQLException;

    void updateOne(int ID, Map<String, Object> entry) throws SQLException;

    void deleteOne(int ID) throws SQLException;

}
