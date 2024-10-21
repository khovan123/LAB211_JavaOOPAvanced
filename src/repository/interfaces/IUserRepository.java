package repository.interfaces;

import java.util.List;
import model.User;

public interface IUserRepository extends Repository<User, List<User>> {

    final String userPath = "";
}
