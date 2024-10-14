
package repository;

import repository.interfaces.ICoachRepository;

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
    }


// String coachId;
//    String coachName;
//    CourseService courseService;
//    UserService userService;


    @Override
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
