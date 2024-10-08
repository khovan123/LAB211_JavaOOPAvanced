package repository;

public interface Repository<T, C> {

    final String path = "";

    C readfile();

    void writeFile(C c);
}
