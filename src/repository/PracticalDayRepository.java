package repository;

import exception.IOException;
import java.util.TreeSet;
import model.PracticalDay;
import repository.interfaces.IPracticalDayRepository;

public class PracticalDayRepository implements IPracticalDayRepository {

    //data sample: PD-YYYY, 14/10/2024, 18, CP-YYYY
    static {

    }

    @Override
    public TreeSet<PracticalDay> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(TreeSet<PracticalDay> praciDays) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
