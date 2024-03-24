import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingCartFrame extends JFrame {
    private ShoppingCart shoppingCart;
    private User user;
    public ShoppingCartFrame (ShoppingCart shoppingCart, User user, JFrame f){
        this.shoppingCart = shoppingCart;
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] shoppingTableColumns = {"Product", "Quantity", "Price"};
        String[][] shoppingRows = new String[shoppingCart.productsInCart.size()][3];
        ArrayList<Product> cartList  = shoppingCart.productsInCart;

        //Setting the values for the shopping cart table
        for (int i = 0; i < cartList.size(); i++) {
            String info = "";
            if (cartList.get(i) instanceof Electronics){
                info = ((Electronics) cartList.get(i)).getBrand() + ", " + ((Electronics) cartList.get(i)).getWarranty()+"M Warranty";
            } else if (cartList.get(i) instanceof Clothing) {
                info = ((Clothing) cartList.get(i)).getSize() + ", " + ((Clothing) cartList.get(i)).getColour();
            }
            shoppingRows[i][0] = "<html>" + cartList.get(i).getId() + "<br>" + cartList.get(i).getName() + "<br>" + info + "</html>";
            // quantity should be taken from hashmap in the shopping cart
            shoppingRows[i][1] = shoppingCart.getQuantity(cartList.get(i)) + "";
            shoppingRows[i][2] = cartList.get(i).getPrice() * shoppingCart.getQuantity(cartList.get(i)) + "";
        }

        JTable shoppingCartTable = new JTable(shoppingRows, shoppingTableColumns);
        JLabel totalCostLabel = new JLabel("Total Cost         £" + shoppingCart.totalCost());
        totalCostLabel.setBounds(300, 300, 220, 30);


        //displaying  user discount
        JLabel firstPurchaseDiscount;
        if (!user.getHasMadeFirstPurchase()){
            firstPurchaseDiscount = new JLabel("First Purchase Discount          £" + shoppingCart.userDiscount(user));

        } else {
            firstPurchaseDiscount= new JLabel("First Purchase Discount          £0");

        }
        firstPurchaseDiscount.setBounds(220, 320, 220, 30);

        //displaying same category discount
        JLabel sameCategoryDiscount = new JLabel("3 in the same category discount          £" + shoppingCart.sameCategoryDiscount());

        sameCategoryDiscount.setBounds(180,340,240,30);
        JLabel finalTotal = new JLabel("Final Total         £" + shoppingCart.finalTotal(user));
        finalTotal.setBounds(300,370, 220, 30);

        JButton purchaseBtn = new JButton("Purchase");
        purchaseBtn.setBounds(250, 440, 100, 30);
        purchaseBtn.addActionListener((e)->{
            JOptionPane.showMessageDialog(null, "Purchase Successful");
            user.setHasMadeFirstPurchase(true);
            shoppingCart.productsInCart.clear();
            shoppingCart.clearCart();
            dispose();
            f.dispose();

        });
        shoppingCartTable.getColumnModel().getColumn(0).setCellRenderer(new MultilineCellRenderer());
        JScrollPane sp = new JScrollPane(shoppingCartTable);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setBounds(20,0,550,200);
        add(sp);
        add(totalCostLabel);
        add(firstPurchaseDiscount);
        add(sameCategoryDiscount);
        add(finalTotal);
        add(purchaseBtn);
        setLayout(null);
        setSize(600, 600);
        setVisible(true);
    }

    static class MultilineCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            table.setRowHeight(row, getPreferredSize().height);
            return this;
        }
    }

}
