import java.io.Serializable;

public class Electronics  extends Product implements Serializable {
    //Instance variables
    private String brand;
    private int warranty;

    //Constructor
    public Electronics( String id, String name, int quantity, int price, String brand, int warranty) {
        super(id, name, quantity, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    //Getters and Setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    @Override
    public String toString(){
        return super.toString()
                + "Product Brand: " + getBrand() + "\n"
                + "Product Warranty: " + getWarranty() + "\n";
    }
}
