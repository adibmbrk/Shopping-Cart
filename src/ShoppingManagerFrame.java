import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ShoppingManagerFrame {
    final private WestminsterShoppingManager manager;
    ShoppingCart shoppingCart = new ShoppingCart();
    User user;

    JFrame f;

    public ShoppingManagerFrame(WestminsterShoppingManager manager, User user) {
        this.manager = manager;
        this.user = user;
    }


    public void showGUI( ) {

        f = new JFrame("Westminster Shopping Centre");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label1 = new JLabel("Select a category");
        label1.setBounds(220, 50, 120,30);

        String[] options = {"All", "Clothing","Electronics"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setBounds(330, 50, 120,30);

        JButton shoppingCartBtn = new JButton("Shopping Cart");
        shoppingCartBtn.setBounds(600, 50, 120,30);


        String[] columns = {"Product ID", "Name", "Category", "Price", "Info"};
        String[][] rows = new String[manager.productsList.size()][5];
        ArrayList<Product> list  = manager.productsList;

        setValues(rows, list);
        JTable table = new JTable(rows, columns);
        table.setModel(new DefaultTableModel(rows, columns));
        table.setDefaultRenderer(Object.class, new ProductTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(50,120,650,250);

        JLabel label2 = new JLabel("No Product Selected");
        JLabel label3 = new JLabel();
        JLabel label4 = new JLabel();
        JLabel label5 = new JLabel();
        JLabel label6 = new JLabel();
        JLabel label7 = new JLabel();
        JLabel label8 = new JLabel();
        label2.setBounds(50, 372, 220, 40);
        label3.setBounds(50, 400, 220, 30);
        label4.setBounds(50, 420, 220, 30);
        label5.setBounds(50, 440, 220, 30);
        label6.setBounds(50, 460, 220, 30);
        label7.setBounds(50, 480, 220, 30);
        label8.setBounds(50, 500, 220, 30);

        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.setBounds(310, 600, 120,30);

        comboBox.addActionListener((e -> {



            if(comboBox.getSelectedItem().equals("All")){
                setValues(rows, list);
                table.setModel(new DefaultTableModel(rows, columns));
            } else if (comboBox.getSelectedItem().equals("Clothing")){
                String [][] updatedRows = new String[manager.filterByClothing().size()][5];
                setValues(updatedRows, manager.filterByClothing() );
                table.setModel(new DefaultTableModel(updatedRows, columns));
            } else if (comboBox.getSelectedItem().equals("Electronics")){
                String [][] updatedRows = new String[manager.filterByElectronics().size()][5];
                setValues(updatedRows, manager.filterByElectronics() );
                table.setModel(new DefaultTableModel(updatedRows, columns));
            }
        }));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                label2.setText("Selected Product Details");
                Product selectedProduct = manager.searchById((String) model.getValueAt(row, 0));

                label3.setText("Product ID - " + selectedProduct.getId());

                if (selectedProduct instanceof Electronics){
                    label4.setText("Category - " + "Electronics");
                    label5.setText("Name - " + selectedProduct.getName());
                    label6.setText("Brand - " + ((Electronics) selectedProduct).getBrand());
                    label7.setText("Warranty - " + ((Electronics) selectedProduct).getWarranty());
                } else if (selectedProduct instanceof Clothing){
                    label4.setText("Category - " + "Clothing");
                    label5.setText("Name - " + selectedProduct.getName());
                    label6.setText("Size - " + ((Clothing) selectedProduct).getSize());
                    label7.setText("Colour - " + ((Clothing) selectedProduct).getColour());

                }
                label8.setText("Quantity - " + selectedProduct.getQuantity());


                f.repaint();



            }
        });


        addToCartBtn.addActionListener((e) ->{
            int row = table.getSelectedRow();
            //validation to only allow adding product if product is selected
            if (row == -1){
                JOptionPane.showMessageDialog(f, "Please select a product");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Product selectedProduct = manager.searchById((String) model.getValueAt(row, 0));

            //validation to see if the product is in stock
            if (selectedProduct.getQuantity() == 0){
                JOptionPane.showMessageDialog(f, "Product is out of stock");
                return;
            }

            selectedProduct.setQuantity(selectedProduct.getQuantity() - 1);

            label8.setText("Quantity - " + selectedProduct.getQuantity());


            table.repaint();


            shoppingCart.addToCart(selectedProduct, 1);

            JOptionPane.showMessageDialog(f, "Product added to the cart");
        });

        shoppingCartBtn.addActionListener((e) ->{
            //validating  if cart is empty
            if (shoppingCart.productsInCart.isEmpty()){
                JOptionPane.showMessageDialog(f, "Shopping Cart is empty");
                return;
            }
            new ShoppingCartFrame(shoppingCart, user, f);

        });


        f.add(label1);
        f.add(comboBox);
        f.add(shoppingCartBtn);
        f.add(scrollPane);


        f.add(label2);
        f.add(label3);
        f.add(label4);
        f.add(label5);
        f.add(label6);
        f.add(label7);
        f.add(label8);

        f.add(addToCartBtn);

        f.setSize(800, 800);
        f.setLayout(null);

        f.setVisible(true);

    }
    class ProductTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            String productId = (String) model.getValueAt(row, 0);
            Product product = manager.searchById(productId);
            if (product.getQuantity() < 3) {
                c.setBackground(new Color(255, 75, 75));

            } else {
                c.setBackground(Color.WHITE);
            }
            return c;
        }
    }


    public void setValues(String[][] rows, ArrayList<Product> products){
        for (int i = 0; i < products.size(); i++) {
            rows[i][0] = products.get(i).getId();
            rows[i][1] = products.get(i).getName();
            rows[i][2] = products.get(i).getClass().getSimpleName();
            rows[i][3] = String.valueOf(products.get(i).getPrice());
            if (products.get(i) instanceof Electronics){
                rows[i][4] = ((Electronics) products.get(i)).getBrand() + ", " + ((Electronics) products.get(i)).getWarranty();
            } else if (products.get(i) instanceof Clothing) {
                rows[i][4] = ((Clothing) products.get(i)).getSize() + ", " + ((Clothing) products.get(i)).getColour();
            }


        }
    }


}
