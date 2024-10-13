package repository.interfaces;

public interface Repository<T, C> {

    final String path = "";

    C readFile();

    void writeFile(C c);

    T filter(String entry, String regex);

}
