import java.io.Serializable;


public class Clothing extends Product implements Serializable {
    //Instance variables
    private String size;
    private String colour;

    //Constructor
    public Clothing(String id, String name, int quantity, int price, String size, String colour) {
        super(id, name, quantity, price);
        this.size = size;
        this.colour = colour;
    }

    //Getters and Setters
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getColour() {
        return colour;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString(){
        return
                super.toString()
                + "Product Size: " + getSize() + "\n"
                + "Product Colour: " + getColour() + "\n";
    }
}
