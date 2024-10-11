package repository.interfaces;

public interface Repository<T, C> {

    final String path = "";

    C readFile();

    void writeFile(C c);
}
