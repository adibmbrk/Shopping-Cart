import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.readFromFile();
        boolean endLoop = false;

        String userChoice;

        // Menu runs until user inputs number 7 for exit
        while (!endLoop) {
            System.out.println("""
                   ---------------------------------------------
                   -Welcome to the Westminster Shopping Manager-
                   ---------------------------------------------
                    Please enter one of the following options:
                    1. Add new product
                    2. Delete a product
                    3. Print the list of products
                    4. Save in file
                    5. Open GUI
                    6. Show user list
                    7. Exit
                    --------------------------------------------
                    """);

            userChoice = sc.nextLine();

            if ("1".equals(userChoice)) {
                manager.addProduct();
            } else if ("2".equals(userChoice)) {
                manager.deleteProduct();
            } else if ("3".equals(userChoice)) {
                manager.printProduct();
            } else if ("4".equals(userChoice)) {
                manager.saveInFile();
            } else if ("5".equals(userChoice)) {
                LoginScreenFrame loginScreenFrame = new LoginScreenFrame(manager);
                loginScreenFrame.showLoginScreen();
            } else if ("6".equals(userChoice)) {
                manager.showUserList();
            } else if ("7".equals(userChoice)) {
                endLoop = true;
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}
