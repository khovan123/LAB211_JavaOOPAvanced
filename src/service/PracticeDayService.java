
package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import exception.EventException;
import exception.NotFoundException;
import model.sub.PracticeDay;
import service.interfaces.IPracticeDayService;
import utils.GlobalUtils;

public class PracticeDayService implements IPracticeDayService {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_fg_GREEN = "\u001B[32m";

    private List<PracticeDay> practiceDayList;

    public PracticeDayService() {
        this.practiceDayList = new ArrayList<>();
    }

    @Override
    public void display() throws NotFoundException {
        if (practiceDayList.isEmpty()) {
            throw new NotFoundException(ANSI_RED + "No practice day found!!!" + ANSI_RESET);
        } else {
            for (PracticeDay practiceDay : practiceDayList) {
                System.out.println(practiceDay);
            }
        }
    }

    @Override
    public void add(PracticeDay practiceDay) {
        try {
            practiceDayList.add(practiceDay);
        } catch (Exception e) {
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
        }
    }

    @Override
    public void delete(String id) {
        try {
            practiceDayList.remove(this.search(p -> p.getPracticeDayId().equalsIgnoreCase(id)));
        } catch (NotFoundException e) {
            System.out.println(ANSI_RED + e.getMessage() + " with id: " + id + ANSI_RESET);
        }
    }

    @Override
    public void update(PracticeDay practiceDay) {
        while (true) {
            Field[] fields = practiceDay.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                System.out.println((i + 1) + ". " + fields[i].getName());
            }
            System.out.println((fields.length + 1) + ". Back");

            String choiceString = GlobalUtils.getValidatedInput("Enter your choice: ",
                    "Your choice cannot be null, empty, or whitespace.",
                    input -> input != null && !input.trim().isEmpty());
            int choice = Integer.parseInt(choiceString);

            if (choice == fields.length + 1){
                break;
            }
            if(choice < 1 || choice > fields.length){
                System.out.println(ANSI_RED + "Invalid choice. Please try again!!!" + ANSI_RESET);
            }
            Field field = fields[choice - 1];
            field.setAccessible(true);
            try{
                Object currentValue = field.get(practiceDay);
                System.out.println("Current value of " + field.getName() + "is: " + currentValue);

                String newValue = GlobalUtils.getValidatedInput("Enter new value of " + field.getName() + ": ",
                                "Your input cannot be null, empty, or whitespace.",
                        input -> input != null && !input.trim().isEmpty());

                if(field.getType() == String.class) {
                    field.set(practiceDay, newValue);
                } else if(field.getType() == int.class){
                    field.set(practiceDay, Integer.parseInt(newValue));
                } else if(field.getType() == boolean.class){
                    field.set(practiceDay, Boolean.parseBoolean(newValue));
                } else if (field.getType() == Date.class){
                    Date dateValue = GlobalUtils.getDate(newValue);
                    field.set(practiceDay, dateValue);
                }

                System.out.println(ANSI_fg_GREEN + "Updated " + field.getName() + " to " + newValue + "successful." + ANSI_RESET);
            } catch (Exception e){
                System.out.println(ANSI_RED + "Error updating field: " + e.getMessage() + ANSI_RESET);
                e.printStackTrace();
            }
        }
    }

    @Override
    public PracticeDay search(Predicate<PracticeDay> p) throws NotFoundException {
        for (PracticeDay practiceDay : practiceDayList) {
            if (p.test(practiceDay)) {
                return practiceDay;
            }
        }
        throw new NotFoundException(ANSI_RED + "Not found any practice day!!!" + ANSI_RESET);
    }

    @Override
    public PracticeDay filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
