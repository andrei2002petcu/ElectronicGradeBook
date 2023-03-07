import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPage  extends JFrame implements ActionListener {

    Catalog catalog;
    Container container;
    JLabel role;
    JLabel username;
    JTextField roleTextField;
    JTextField usernameTextField;
    JButton loginButton;
    JButton resetButton;
    public SearchPage(Catalog catalog) {

        this.catalog = catalog;
        initializeAndSetComponents();
        addComponents();
        setGenericSettings();

    }

    public void setGenericSettings() {
        setVisible(true);
        setTitle("Search Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 800, 550);
        setResizable(false);
    }

    public void initializeAndSetComponents() {
        container = getContentPane();
        container.setLayout(null);
        //container.setBackground(Color.GRAY);

        role = new JLabel("ROLE");
        role.setBounds(300, 150, 50, 30);
        username = new JLabel("FIRSTNAME LASTNAME");
        username.setBounds(200, 220, 150, 30);

        roleTextField = new JTextField();
        roleTextField.setBounds(350, 150, 150, 30);
        usernameTextField = new JTextField();
        usernameTextField.setBounds(350, 220, 150, 30);

        loginButton = new JButton("SEARCH");
        resetButton = new JButton("RESET");
        loginButton.setBounds(250, 300, 100, 30);
        resetButton.setBounds(400, 300, 100, 30);
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);

    }

    public void addComponents() {
        container.add(role);
        container.add(roleTextField);
        container.add(username);
        container.add(usernameTextField);
        container.add(loginButton);
        container.add(resetButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == resetButton) {
            roleTextField.setText("");
            usernameTextField.setText("");
        }

        if(e.getSource() == loginButton) {
            String roleText = roleTextField.getText();
            String usernameText = usernameTextField.getText();

            if(roleText.equals("Student") || roleText.equals("student")) {
                StudentPage studentPage = new StudentPage(catalog, usernameText);
            }
            else if(roleText.equals("Teacher") || roleText.equals("teacher")) {
                TeacherPage teacherPage = new TeacherPage(catalog, usernameText);
            }
        }
    }
}
