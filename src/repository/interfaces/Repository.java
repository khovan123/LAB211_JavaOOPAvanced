package repository.interfaces;

public interface Repository<T, C> {

    final String path = "";

    C readfile();

    void writeFile(C c);

    T filter(String entry, String regex);

}
