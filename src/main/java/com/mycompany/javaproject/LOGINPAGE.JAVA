package com.mycompany.test;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;

public class LoginPage implements ActionListener {

    Button register;
    Button login;
    Button exit;
    JFrame window;

    public LoginPage() {
        window = new JFrame();
        window.setSize(300, 200);
        window.setLocation(1000, 200);
        window.setLayout(new FlowLayout());

        register = new Button("Register");
        login = new Button("Login");
        exit = new Button("Exit");

        register.addActionListener(this);
        login.addActionListener(this);
        exit.addActionListener(this);

        window.add(register);
        window.add(login);
        window.add(exit);

        window.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            DataIO.writeData();
            System.exit(0);
        } else if (e.getSource() == login) {
            String x = JOptionPane.showInputDialog("Enter username:");
            String y = JOptionPane.showInputDialog("Enter password:");

            if (x.equals("admin") && y.equals("123")) {
                AdminPage info= new AdminPage();
                info.show(); 
            } else {
                DataIO.Customer found = null;

                for (DataIO.Customer c : DataIO.all) {
                    if (c.username.equals(x)) {
                        found = c;
                        break;
                    }
                }

                if (found == null) {
                    JOptionPane.showMessageDialog(window, "Wrong username!");
                } else {
                    int password = Integer.parseInt(y);
                    if (found.password == password) {
                        window.setVisible(false);
                        DataIO.Test.z.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(window, "Wrong password!");
                    }
                }
            }
        } else if (e.getSource() == register) {
            try {
                String name = JOptionPane.showInputDialog("Enter your username");
                int password = Integer.parseInt(JOptionPane.showInputDialog("Enter your password"));

                if (DataIO.all == null) {
                    DataIO.all = new ArrayList<>();
                }

                DataIO.Customer c = new DataIO.Customer(name, password);
                DataIO.all.add(c);
                DataIO.writeData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(window, "Use only integers for password!");
            }
        }
    }

    private void openAdminPage() {
        JFrame adminWindow = new JFrame();
        adminWindow.setSize(800, 600);
        adminWindow.setLocationRelativeTo(null); 
        adminWindow.setTitle("Admin Page");

        adminWindow.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage();
    }

    public static class DataIO {
        public static void writeData() {
        }

        public static class Customer {
            public String username;
            public int password;

            public Customer(String username, int password) {
                this.username = username;
                this.password = password;
            }
        }

        public static class Test {
            public static JFrame z = new JFrame();
        }

        public static List<Customer> all = new ArrayList<>();
    }
}
