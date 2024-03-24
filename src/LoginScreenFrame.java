import javax.swing.*;
import java.awt.*;

public class LoginScreenFrame extends JFrame {
    private WestminsterShoppingManager manager;

    public LoginScreenFrame(WestminsterShoppingManager manager) {
        this.manager = manager;

    }

    public void showLoginScreen(){
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField email = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField password = new JPasswordField(20);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");


        loginBtn.addActionListener((e) ->{
            if (email.getText().isEmpty() || new String(password.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
                return;
            }

            if (!manager.isValidEmail(email.getText())){
                JOptionPane.showMessageDialog(null, "Please enter a valid email");
                return;
            }

            if (!manager.isEmailTaken(email.getText())){
                JOptionPane.showMessageDialog(null, "Email not registered");
                return;
            }

            User user = manager.searchUser(email.getText());
            if (!user.getPassword().equals(new String(password.getPassword()))){
                JOptionPane.showMessageDialog(null, "Incorrect password");
                return;
            }

            ShoppingManagerFrame newGUI = new ShoppingManagerFrame(manager, user);
            newGUI.showGUI();
            dispose();

        });

        registerBtn.addActionListener((e)->{
            if (email.getText().isEmpty() || new String(password.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
                return;
            }
            if (!manager.isValidEmail(email.getText())){
                JOptionPane.showMessageDialog(null, "Please enter a valid email");
                return;
            }

            if (manager.isEmailTaken(email.getText())){
                JOptionPane.showMessageDialog(null, "Email already taken");
                return;
            }

            manager.addUser(email.getText(), new String(password.getPassword()));
            JOptionPane.showMessageDialog(null, "User registered successfully");

        });


        mainPanel.add(emailLabel);
        mainPanel.add(email);
        mainPanel.add(passwordLabel);
        mainPanel.add(password);
        mainPanel.add(loginBtn);
        mainPanel.add(registerBtn);


        add(mainPanel);

        setLayout(new FlowLayout());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
