package repository.interfaces;

import java.util.List;

import model.Coach;

public interface ICoachRepository extends Repository<Coach, List<Coach>> {

    final String coachPath = "/data/coach.csv"; // Define coach file path

    List<Coach> getCoaches();
}
