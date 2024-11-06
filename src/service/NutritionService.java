package service;

import exception.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import model.Nutrition;
import repository.NutritionRepository;
import service.interfaces.INutritionService;
import utils.FieldUtils;

public class NutritionService implements INutritionService {

    private NutritionRepository nutritionRepository = new NutritionRepository();
    private List<Nutrition> nutritions = new ArrayList<>();

    public NutritionService() {
        this.readFromDatabase();
    }

    private void readFromDatabase() {
        try {
            for (Nutrition nutrition : nutritionRepository.readData()) {
                try {
                    if (!existed(nutrition.getNutritionId())) {
                        this.nutritions.add(nutrition);
                    }
                } catch (Exception e) {

                }
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (nutritions.isEmpty()) {
            throw new EmptyDataException("List of nutrition is empty");
        }
    }

    @Override
    public void add(Nutrition entry) throws EventException, InvalidDataException {
        if (!this.existed(entry.getNutritionId())) {
            nutritions.add(entry);
        } else {
            throw new InvalidDataException("Nutrition with ID: " + entry.getNutritionId() + " was existed");
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            nutritions.remove(this.findById(id));
            nutritionRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws EventException, NotFoundException {
        Nutrition nutrition = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(nutrition.getClass(), fieldName);
            try {
                field.setAccessible(true); 
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                nutritionRepository.updateToDB(id, updatedMap);
                field.set(nutrition, entry.get(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        if (fieldName.equalsIgnoreCase("nutritionId")) {
            return NutritionRepository.NutritionID_Column;
        }
        if (fieldName.equalsIgnoreCase("calories")) {
            return NutritionRepository.Calories_Column;
        }
        if (fieldName.equalsIgnoreCase("practicalDayId")) {
            return NutritionRepository.PracticalDayID_Column;
        }
        throw new NotFoundException("Not found any field");
    }

    @Override
    public Nutrition search(Predicate<Nutrition> p) throws NotFoundException {
        for (Nutrition nutrition : nutritions) {
            if (p.test(nutrition)) {
                return nutrition;
            }
        }
        throw new NotFoundException("Not found any nutrition");
    }

    @Override
    public Nutrition findById(int id) throws NotFoundException {
        return this.search(p -> p.getNutritionId() == (id));
    }

    public boolean existed(int id) {
        try {
            return this.findById(id) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
