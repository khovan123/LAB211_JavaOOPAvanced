package service;

import exception.EventException;
import exception.NotFoundException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import model.Coach;
import repository.SampleCoachRepository;
import utils.FieldUtils;
import utils.GlobalUtils;
import utils.ObjectUtils;

public class SampleCoachService {

    SampleCoachRepository sampleCoachRepository = new SampleCoachRepository();

    public void update(String id, Map<String, Object> entry) throws NotFoundException, EventException {
        for (String fieldName : entry.keySet()) {
            Coach coach = findById(id);
            Field field = FieldUtils.getFieldByName(coach.getClass(), fieldName);
            try {
                field.set(coach, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                sampleCoachRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    public Coach findById(String id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete(String id) throws EventException, NotFoundException {
        try {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            //if delete successfully calll delete in repository
            sampleCoachRepository.deleteToDB(id);
        } catch (UnsupportedOperationException | SQLException e) {
            //throw new ...
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        if (fieldName.equalsIgnoreCase("fullName")) {
            return SampleCoachRepository.FullName_Column;
        }
        //if  ... in another case

        throw new NotFoundException("Not found any field");
    }

    
    //this method below implement in controller
    public void updateOrDeleteCoachFromConsoleCustomize(Object obj) {
        if (isEmpty()) {
            System.out.println("Please create new contract ^^");
            return;
        }
        while (true) {
            try {
                String id = GlobalUtils.getValue("Enter id for update: ", "Cannot a left blank");
                Coach coach;
                if (!ObjectUtils.validID(id)) {
                    System.out.println("Id must be correct form: ... ");
                } else if ((coach = findById(id)) != null) {
                    System.out.println(coach.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(coach.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GlobalUtils.getInteger("Enter selection: ", "Please enter a valid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                delete(coach.getCoachId());
                                System.out.println("Delete successfully");
                            } catch (EventException | NotFoundException e) {
                                System.err.println(e.getMessage());
                            }
                            return;
                        } else if (selection == editMenuOptions.length) {
                            return;
                        }
                        while (true) {
                            try {
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Can not a blank");
                                update(id, FieldUtils.getFieldValueByName(coach, editMenuOptions[selection - 1], newValue));
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }

                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
