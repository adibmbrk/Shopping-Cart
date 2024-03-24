import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    //the map stores Product ID and Quantity
    private Map<String, Integer> cart = new HashMap<>();

    //The products in cart only displays the type of products in the cart, no quantity is displayed
    //Hashmap was used owing to its benefits in faster lookup time
    protected ArrayList<Product> productsInCart = new ArrayList<>();
    public void addToCart(Product product, int quantity) {
        if (cart.containsKey(product.getId())) {
            // add to the existing quantity in the cart hashmap
            cart.put(product.getId(), cart.get(product.getId()) + quantity);
        } else {
            cart.put(product.getId(), quantity);
            productsInCart.add(product);
        }

    }

    //return quantity from the hashmap
    public int getQuantity(Product product) {
        return cart.get(product.getId());
    }

    //total cost of all the products
    public int totalCost(){
        int totalCost = 0;
        for (Product product : productsInCart) {
            totalCost += product.getPrice() * cart.get(product.getId());
        }
        return totalCost;
    }

    // first purchase discount
    public double userDiscount(User user){
        double discount = 0;
        if (!user.getHasMadeFirstPurchase()){
            discount = totalCost() * 0.1;
        }
        return Math.round(discount * 100.0) / 100.0;
    }

    //calculating discount for same category
    public double sameCategoryDiscount(){
        double discount = 0;
        int electronicsCount = 0;
        int clothingCount = 0;
        for (Product product : productsInCart) {
            if (product instanceof Electronics){
                electronicsCount += cart.get(product.getId());
            } else if (product instanceof Clothing){
                clothingCount += cart.get(product.getId());
            }
        }
        if (electronicsCount >= 3 || clothingCount >= 3){
            discount = totalCost() * 0.2;
        }
        return Math.round(discount * 100.0) / 100.0;
    }

    //display final total minus discounts
    public double finalTotal(User user){
        double finalTotal = totalCost() - userDiscount(user) - sameCategoryDiscount();
        return Math.round(finalTotal * 100.0) / 100.0;
    }

    //removing anc clearing all items from the cart
    public void clearCart(){
        cart.clear();
        productsInCart.clear();
    }


}
