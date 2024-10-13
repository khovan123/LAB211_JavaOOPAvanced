
package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import exception.EmptyDataException;
import exception.EventException;
import exception.NotFoundException;
import model.PracticalDay;
import service.interfaces.IPracticalDayService;
import utils.GlobalUtils;

public class PracticalDayService implements IPracticalDayService{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_fg_GREEN = "\u001B[32m";

    private List<PracticalDay> practicalDayList;

    public PracticalDayService() {
        this.practicalDayList = new ArrayList<>();
    }

    @Override
    public void display() throws EmptyDataException {
        if (practicalDayList.isEmpty()) {
            throw new EmptyDataException(ANSI_RED + "No practice day found!!!" + ANSI_RESET);
        } else {
            for (PracticalDay practicalDay : practicalDayList) {
                System.out.println(practicalDay);
            }
        }
    }

    @Override
    public void add(PracticalDay practiceDay) throws EventException {
        try {
            practicalDayList.add(practiceDay);
            System.out.println(ANSI_fg_GREEN + "Practical Day added successfully!" + ANSI_RESET);
        } catch (Exception e) {
            throw new EventException(ANSI_RED + "Failed to add Practical Day: " + e.getMessage() + ANSI_RESET);
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            practicalDayList.remove(this.search(p -> p.getPracticeDayId().equalsIgnoreCase(id)));
            System.out.println(ANSI_fg_GREEN + "Deleted Practical Day with ID: " + id + " successfully!" + ANSI_RESET);
        } catch (NotFoundException e) {
            throw new NotFoundException(ANSI_RED + "Practical Day with ID: " + id + " not found!" + ANSI_RESET);
        } catch (Exception e) {
            throw new EventException(ANSI_RED + "An error occurred while deleting Practical Day with ID: " + id + ". " + e.getMessage() + ANSI_RESET);
        }
    }

    @Override
    public void update(PracticalDay practicalDay) {
        while (true) {
            Field[] fields = practicalDay.getClass().getDeclaredFields();
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
                Object currentValue = field.get(practicalDay);
                System.out.println("Current value of " + field.getName() + "is: " + currentValue);

                String newValue = GlobalUtils.getValidatedInput("Enter new value of " + field.getName() + ": ",
                        "Your input cannot be null, empty, or whitespace.",
                        input -> input != null && !input.trim().isEmpty());

                if(field.getType() == String.class) {
                    field.set(practicalDay, newValue);
                } else if(field.getType() == int.class){
                    field.set(practicalDay, Integer.parseInt(newValue));
                } else if(field.getType() == boolean.class){
                    field.set(practicalDay, Boolean.parseBoolean(newValue));
                } else if (field.getType() == Date.class){
                    Date dateValue = GlobalUtils.getDate(newValue);
                    field.set(practicalDay, dateValue);
                }

                System.out.println(ANSI_fg_GREEN + "Updated " + field.getName() + " to " + newValue + "successful." + ANSI_RESET);
            } catch (Exception e){
                System.out.println(ANSI_RED + "Error updating field: " + e.getMessage() + ANSI_RESET);
                e.printStackTrace();
            }
        }
    }

    @Override
    public PracticalDay search(Predicate<PracticalDay> p) throws NotFoundException {
        for (PracticalDay practicalDay : practicalDayList) {
            if (p.test(practicalDay)) {
                return practicalDay;
            }
        }
        throw new NotFoundException(ANSI_RED + "Not found any practice day!!!" + ANSI_RESET);
    }

    @Override
    public PracticalDay filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
