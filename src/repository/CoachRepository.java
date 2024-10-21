package repository;

import exception.IOException;
import java.util.List;
import model.Coach;
import repository.interfaces.ICoachRepository;

public class CoachRepository implements ICoachRepository {

    @Override
    public List<Coach> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<Coach> coachs) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //data sample: CA-YYYY, Cris Rona
    

}
