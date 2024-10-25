
package repository;

import exception.IOException;
import java.util.List;
import model.UserProgress;
import repository.interfaces.IUserProgressRepository;


public class UserProgressRepository implements IUserProgressRepository{

   
    //generate with id: CP-YYYY in scheduleRepository
    static{
        
    }

    @Override
    public List<UserProgress> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<UserProgress> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
