import java.io.Serializable;
abstract public class Product implements Comparable<Product>, Serializable {
    //instance variables
    private String id;
    private String name;
    private int quantity;
    private int price;

    //Constructor

    public Product(String id, String name, int quantity, int price){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    //Overridden method from Comparable interface
    @Override
    public int compareTo(Product product){
        return this.getId().compareTo(product.getId());
    }

    //Getters and Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


@Override
    public String toString(){
        return "Product ID: " + getId() + "\n" +
                "Product Name: " + getName() + "\n" +
                "Product Quantity: " + getQuantity() + "\n" +
                "Product Price: " + getPrice() + "\n";
    }
}
