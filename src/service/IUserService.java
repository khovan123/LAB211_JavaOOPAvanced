package service;

import java.util.function.Predicate;
import model.User;

public interface IUserService extends Service<User> {

    @Override
    void display();

    @Override
    void add(User user);

    @Override
    void delete(String id);

    @Override
    User search(Predicate<User> p);

    @Override
    User filter(String entry, String regex);
}
