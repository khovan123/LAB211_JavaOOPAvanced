package service;

public interface Service<T> {

    void display();
    
    T filter(String input, String regex);
}
