
package Library;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LogInFrame extends JFrame implements ActionListener{
    JLabel titlelbl = new JLabel("Library Management System");
    JLabel idlbl = new JLabel("Librarian ID:");
    JLabel passlbl = new JLabel("Password:");
    JTextField idField = new JTextField();
    JPasswordField passField = new JPasswordField();
    JButton loginButton = new JButton("LOG IN");
    JPanel form = new JPanel();
    JPanel picture = new JPanel();

    
    public LogInFrame(){
        this.setLayout(null);
        this.setTitle("LBS LOG IN FORM");
        this.setSize(1366, 768);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
 
        form.setLayout(null);
        form.setBounds(0, 0, 700, 700);
        add(form);

        titlelbl.setFont(new Font("Arial", Font.BOLD, 24));
        titlelbl.setBounds(200, 50, 400, 40);
        form.add(titlelbl);

        idlbl.setFont(new Font("Arial", Font.PLAIN, 18));
        idlbl.setBounds(200, 150, 120, 30);
        form.add(idlbl);
        
        idField.setBounds(200, 180, 300, 40);
        form.add(idField);

        passlbl.setFont(new Font("Arial", Font.PLAIN, 18));
        passlbl.setBounds(200, 250, 120, 30);
        form.add(passlbl);

        passField.setBounds(200, 280, 300, 40);
        form.add(passField);

        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loginButton.setBounds(300, 350, 150, 40);
        form.add(loginButton);
        
        loginButton.addActionListener(this);
        
        picture.setBounds(700, 0, 700, 800);
        picture.setBackground(new Color(0, 102, 51));
        add(picture);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String librarianID = idField.getText();
        String password = new String(passField.getPassword());

        if (librarianID.equals("admin") && password.equals("123456")) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid ID or Password!");
        }
    }

    public static void main(String[] args) {
        LogInFrame login = new LogInFrame ();
        login.setVisible(true);
    }
}

