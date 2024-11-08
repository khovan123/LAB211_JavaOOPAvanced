package repository;

import exception.InvalidDataException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import model.Nutrition;
import repository.interfaces.INutritionRepository;
import utils.GlobalUtils;

public class NutritionRepository implements INutritionRepository {

    public static final String NutritionID_Column = "NutritionID";
    public static final String Calories_Column = "Caliries";
    public static final String PracticalDayID_Column = "PracticalDayID";
    private static final List<String> REGISTEDWORKOUTMODELCOLUMN = new ArrayList<>(Arrays.asList(NutritionID_Column, Calories_Column, PracticalDayID_Column));
    private final Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<Nutrition> readData() throws SQLException {
        List<Nutrition> list = new ArrayList<>();
        for (String row : getMany()) {
            String data[] = row.split(",");
            try {
                list.add(new Nutrition(data[0].trim(), data[1].trim(), data[2].trim()));
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);

            }
        }
        return list;
    }

    @Override
    public void insertToDB(Nutrition entry) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
//        entries.put(NutritionID_Column, entry.getNutritionId());
        entries.put(Calories_Column, GlobalUtils.convertToString(entry.getCalories()));
        entries.put(PracticalDayID_Column, entry.getPracticalDayId());
        insertOne(entries);
    }

    @Override
    public void updateToDB(int id, Map<String, Object> entry) throws SQLException {
        Map<String, Object> coachMap = new HashMap<>();
        for (String column : entry.keySet()) {
            coachMap.putIfAbsent(column, GlobalUtils.convertToString(entry.get(column)));
        }
        updateOne(id, coachMap);
    }

    @Override
    public void deleteToDB(int ID) throws SQLException {
        deleteOne(ID);
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NutritionID, Calories, PracticalDayID FROM NutritionModel");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    row.append(rs.getString(i)).append(i < columnCount ? ", " : "");
                }
                list.add(row.toString());
                row = new StringBuilder();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void insertOne(Map<String, Object> entries) throws SQLException {
        String courseComboQuery = "INSERT INTO NutritionModel(X) VALUES(Y)";
        StringBuilder modelColumn = new StringBuilder();
        StringBuilder modelValue = new StringBuilder();
        for (String column : entries.keySet()) {
            if (REGISTEDWORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(NutritionID_Column))) {
                if (modelColumn.length() > 0) {
                    modelColumn.append(", ");
                    modelValue.append(", ");
                }
                modelColumn.append(column);
                modelValue.append("?");
            }
        }

        courseComboQuery = courseComboQuery.replace("Y", modelValue.toString()).replace("X", modelColumn.toString());

        try (PreparedStatement ps = conn.prepareStatement(courseComboQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEDWORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(NutritionID_Column))) {
                    SQLServerConnection.setParamater(ps, i++, entries.get(column));
                }
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateOne(int ID, Map<String, Object> entries) throws SQLException {
        String query = "UPDATE NutritionModel SET X WHERE NutritionID = ?";
        StringBuilder modelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEDWORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(NutritionID_Column))) {
                modelColumn.append((modelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        query = query.replace("X", modelColumn.toString());

        PreparedStatement ps = conn.prepareStatement(query);
        int i = 1;
        for (String column : entries.keySet()) {
            if (REGISTEDWORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(NutritionID_Column))) {
//                    ps.setString(i++, entries.get(column));
                SQLServerConnection.setParamater(ps, i++, entries.get(column));
            }
        }
        ps.setInt(i, ID);
        ps.executeUpdate();

    }

    @Override
    public void deleteOne(int ID) throws SQLException {
        String query = "DELETE FROM NutritionModel WHERE NutritionID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}
