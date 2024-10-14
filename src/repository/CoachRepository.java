package repository;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import model.Coach;
import repository.interfaces.ICoachRepository;

<<<<<<< HEAD
public class CoachRepository implements ICoachRepository{
    protected ArrayList<Coach> coachList = new ArrayList<>();


    @Override
    public ArrayList<Coach> readfile() {
        String line;
        try {
            BufferedReader input = new BufferedReader(new FileReader(path + coachPath));
            while ((line = input.readLine()) != null) {
                String[] tokString = line.split(",");
//                String coachId = tokString[0];
//                String coachName = tokString[1];
//                // Assuming CourseService and UserService can be created or retrieved
//                CourseService courseService = new CourseService(); // replace with actual logic
//                UserService userService = new UserService(); // replace with actual logic
//                Coach coach = new Coach(coachId, coachName, courseService, userService);
//                coachList.add(coach);
            }
            return coachList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
=======
public class CoachRepository implements ICoachRepository {

    private static List<Coach> coachs = new ArrayList<>();
    //data sample: CA-YYYY, Cris Rona
    static {

    }

    public List<Coach> getCoachs() {
        return coachs;
    }

    @Override
    public void addFromDatabase() throws EventException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
>>>>>>> e532ad77b3b75a540bc9966cc858d1dec80820d8
    }


// String coachId;
//    String coachName;
//    CourseService courseService;
//    UserService userService;


    @Override
<<<<<<< HEAD
    public void writeFile(ArrayList <Coach> coachs) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(path + employeePath, true)))
        { for (Coach coach : coachs){
           //..//
            output.write(line);
            output.newLine();
        }catch (IOException e ){
            e.printStackTrace();
        }

        }
=======
    public List<Coach> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<Coach> coachs) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(Coach coach) throws EventException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) throws EventException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Coach search(Predicate<Coach> p) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Coach filter(String entry, String regex) throws InvalidDataException {
>>>>>>> e532ad77b3b75a540bc9966cc858d1dec80820d8
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
