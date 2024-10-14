package repository.interfaces;
<<<<<<< HEAD
import java.util.ArrayList;
import model.main.Coach;
public interface ICoachRepository extends Repository<Coach, ArrayList<Coach>>{
String coachPath ="/data/coach.csv";
=======

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.Coach;
>>>>>>> e532ad77b3b75a540bc9966cc858d1dec80820d8

public interface ICoachRepository extends Repository<Coach, List<Coach>> {

    final String path = "";

    @Override
    void addFromDatabase() throws EventException;

    @Override
    List<Coach> readFile() throws IOException;

    @Override
    void writeFile(List<Coach> coachs) throws IOException;

    @Override
    void add(Coach coach) throws EventException;

    @Override
    void delete(String id) throws EventException;

    @Override
    Coach search(Predicate<Coach> p) throws NotFoundException;

    @Override
    Coach filter(String entry, String regex) throws InvalidDataException;
}
