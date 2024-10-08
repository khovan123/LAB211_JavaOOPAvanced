package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public abstract class Menu<T> {

    protected ArrayList<T> menuOptions;
    protected String title;
    protected static boolean continueExecution = true;

    public Menu() {
    }

    public Menu(String title, T[] menuOptions) {
        this.title = title;
        this.menuOptions = new ArrayList<>();
        this.menuOptions.addAll(Arrays.asList(menuOptions));
    }

    public void display() {
        System.out.println("-------------------------");
        System.out.println(title);
        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.println(i + 1 + "." + menuOptions.get(i));
        }
        System.out.println("-------------------------");
    }

    public int getSelected() {
        int selected = 0;
        while (true) {
            display();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter selection: ");
            try {
                selected = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please try again.");
            }
        }
        return selected;
    }

    public abstract void execute(int selection);

    public void exitMenu() {
        continueExecution = false;
    }

    public void run() {
        int sizeOfMenu = menuOptions.size();
        int selection;
        do {
            selection = getSelected();
            if (selection > 0 && selection <= sizeOfMenu) {
                execute(selection);
            } else {
                System.out.println("Invalid option.\n Please try again.");
            }

        } while (continueExecution);

    }

}
