import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WestminsterShoppingManager implements ShoppingManager, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    //stores products
    protected ArrayList<Product> productsList = new ArrayList<>();

    //stores users login details
    protected ArrayList<User> userList = new ArrayList<>();


    // adds product by creating either an electronics or clothing object depending on user input
    @Override
    public void addProduct() {
        Scanner sc = new Scanner(System.in);
        if(productsList.size() >= 50){
            System.out.println("There can only be 50 products in the system");
            return;
        }

        boolean endLoop = true;



        String productId = validID("Enter Product ID");

        System.out.println("Enter Product Name");
        String productName = sc.nextLine();

        int productQuantity = validIntAboveZero("Enter Product Quantity : ");

        int productPrice = validIntAboveZero("Enter Product Price : ");

        while (endLoop) {
            System.out.println("What type of Product are you adding? Electronics or Clothing? (E/C)");
            String answer = sc.nextLine().trim().toUpperCase();
            if (answer.equals("E")) {
                System.out.println("Enter the brand name");
                String brandName = sc.nextLine();

                int warrantyMonths = validWarranty(validIntAboveZero("Enter the warranty period in months : "));

                Electronics e = new Electronics(productId, productName, productQuantity, productPrice, brandName, warrantyMonths);
                productsList.add(e);
                endLoop = false;
            } else if (answer.equals("C")) {
                System.out.println("Enter the size");
                String size = sc.nextLine();

                System.out.println("Enter the colour");
                String colour = sc.nextLine();

                Clothing c = new Clothing(productId, productName, productQuantity, productPrice, size, colour);
                productsList.add(c);
                endLoop = false;
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println("The product has been added successfully");
    }

    //Deletes the products and displays deleted product details
    @Override
    public void deleteProduct() {
        System.out.println("Enter the Product ID");
        Scanner sc = new Scanner(System.in);
        String productId = sc.nextLine();
        boolean productFound = false;

        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getId().equals(productId)) {
                System.out.println(productsList.get(i));
                productsList.remove(i);
                productFound = true;
                break;
            }
        }

        if (productFound) {
            System.out.println("-------------------------------------");
            System.out.println("Product has been removed successfully");
            System.out.println("There are " + productsList.size() + " products in the system");
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Product not found");
        }
    }

    //Displays Product information in alphabetical order according to Product ID
    @Override
    public void printProduct() {
        ArrayList<Product> clone = new ArrayList<>(productsList);
        Collections.sort(clone);
        for (Product product: clone){
            System.out.println(product.toString());
            System.out.println("----------------------------------------------------");
        }
    }

    //serializes the arraylist with products
    @Override
    public void saveInFile() {
        try {
            FileOutputStream fileStream = new FileOutputStream("ProductList.ser");
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(this.productsList);
            System.out.println("The object has been saved in a file successfully");

        }catch (IOException e){
            System.out.println("IO exception has occurred");
        }

    }

    //deserializes the object
    public void readFromFile(){
        try{
            FileInputStream fileIn = new FileInputStream("ProductList.ser");
            ObjectInputStream is = new ObjectInputStream(fileIn);

            //noinspection unchecked
            productsList = (ArrayList<Product>) is.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Could not load File to the System");
        }


    }

    // returns an arraylist with only electronic products
    public ArrayList<Product> filterByElectronics() {
        ArrayList<Product> clone = new ArrayList<>();

        for (Product product: productsList){
            if (product instanceof Electronics){
                clone.add(product);
            }
        }

        return clone;
    }

    // returns an arraylist with only clothing products
    public ArrayList<Product> filterByClothing() {
        ArrayList<Product> clone = new ArrayList<>();

        for (Product product: productsList){
            if (product instanceof Clothing){
                clone.add(product);
            }
        }

        return clone;
    }

    //returns the product after doing a linear search in the Arraylist
    public Product searchById(String id){
        for (Product product: productsList){
            if (product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }


    //Adds user to a user list
    public void addUser(String email, String password){
        User user = new User(email, password);
        userList.add(user);
    }

    //validates email using a regular expression
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //checks if a user exists with that specific email
    public boolean isEmailTaken(String email){
        for (User user: userList){
            if (user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    //Used to find the user
    public User searchUser(String email){
        for (User user: userList){
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    //Prints the customers that have registered in the system
    public void showUserList(){
        for (User user: userList){
            System.out.println("Email                  "+user.getEmail());
            System.out.println("Made First Purchase? : "+user.getHasMadeFirstPurchase());
            System.out.println("-------------------------------------------------------");
        }
    }

    //Checks if id is a duplicate or not
    public String validID(String text){
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        String id = "";

        do {

            System.out.println(text);
            id = sc.nextLine();

            for (Product product: productsList){
                if (product.getId().equals(id)){
                    System.out.println("ID is already taken");
                    isValid = false;
                    break;
                } else {
                    isValid = true;
                }
            }
        } while (!isValid);
        return id;
    }



    //validates the if number and the number should be above zero
    public int validIntAboveZero(String text){
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        int number = 0;
        do {
            System.out.println(text);
            try {
                number = sc.nextInt();
                if (number > 0){
                    isValid = true;
                } else {
                    System.out.println("Number should be above zero");
                }
            } catch (Exception e){
                System.out.println("Invalid number");
                sc.nextLine();
            }
        } while (!isValid);
        return number;
    }

    // the maximum warranty that can be given is for 10 years that is 120 months
    public int validWarranty(int warranty){
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        do {
            try {
                if (warranty > 0 && warranty <= 120){
                    isValid = true;
                } else {
                    System.out.println("Warranty should be above zero and less than 120");
                    warranty = sc.nextInt();
                }
            } catch (Exception e){
                System.out.println("Invalid number");
                sc.nextLine();
            }
        } while (!isValid);
        return warranty;
    }

}
