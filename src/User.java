import java.util.ArrayList;

public class User {
    private String email;
    private String password;

    private boolean hasMadeFirstPurchase;

    //constructors
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.hasMadeFirstPurchase = false;
    }

    //Getters and Setters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getHasMadeFirstPurchase() {
        return hasMadeFirstPurchase;
    }

    public void setHasMadeFirstPurchase(boolean hasMadeFirstPurchase) {
        this.hasMadeFirstPurchase = hasMadeFirstPurchase;
    }
}
