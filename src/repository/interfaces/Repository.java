package repository.interfaces;

import exception.IOException;

public interface Repository<T, C> {

    final String path = "";
  
    C readFile() throws IOException;

    void writeFile(C entry) throws IOException, java.io.IOException;

}
