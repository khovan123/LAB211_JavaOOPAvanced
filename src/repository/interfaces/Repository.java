package repository.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Repository<T, C> {
    
    C readData();
    
    void insert(T entry)throws SQLException;
    
    void update(T entry) throws SQLException;
    
    void delete(T entry) throws SQLException;

    List<String> getMany() throws SQLException;

    void insertOne(Map<String, String> entries) throws SQLException;

    void updateOne(String ID, Map<String, String> entries) throws SQLException;

    void deleteOne(String ID) throws SQLException;

}
