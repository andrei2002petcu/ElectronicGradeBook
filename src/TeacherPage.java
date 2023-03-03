import javax.management.DescriptorAccess;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class TeacherPage extends JFrame implements ActionListener, ListSelectionListener {

    Catalog catalog;
    Teacher teacher;
    Course course;
    Container container;
    JButton back;
    JLabel noTeacher;
    JList courseJList;
    JScrollPane scrollPane;
    JLabel courseLabel;
    JScrollPane studentsScrollPane;
    JList studentJList;
    JLabel studentLabel;
    JLabel welcome;
    JLabel gradStudentLabel;
    JList gradStudentJList;
    JScrollPane gradStudentsScrollPane;

    public TeacherPage(Catalog catalog, String teacherName) {
        this.catalog = catalog;
        teacher = isTeacher(teacherName);
        container = getContentPane();
        container.setLayout(null);

        back = new JButton("BACK");
        back.setBounds(650, 450, 100, 30);
        back.addActionListener(this);
        container.add(back);

        if(teacher == null) {
            noTeacher = new JLabel("THIS TEACHER DOES NOT EXIST!!!");
            noTeacher.setBounds(290, 170, 300, 30);
            container.add(noTeacher);
            setGenericSettings();
        }
        else {

            DefaultListModel courses = getCourses(teacherName);

            courseJList = new JList(courses);
            scrollPane = new JScrollPane(courseJList);
            scrollPane.setBounds(100, 110, 300, 150);

            courseLabel = new JLabel("Courses:");
            courseLabel.setBounds(100, 80, 100, 30);
            studentLabel = new JLabel("");
            studentLabel.setBounds(410, 80, 200, 30);

            welcome = new JLabel("WELCOME, " + teacherName + "!");
            welcome.setBounds(300, 30, 300, 30);

            gradStudentLabel = new JLabel("");
            gradStudentLabel.setBounds(250, 300, 200, 30);

            courseJList.addListSelectionListener(this);

            addComponents();
            setGenericSettings();
        }
    }

    public void setGenericSettings() {
        setVisible(true);
        setTitle("Teacher Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 800, 550);
        setResizable(false);
    }

    public void addComponents() {
        container.add(scrollPane);
        container.add(courseLabel);
        container.add(studentLabel);
        container.add(gradStudentLabel);
    }

    public Teacher isTeacher(String teacherName) {
        for(Course i : catalog.courses)
            if(i.getTeacher().toString().equals(teacherName))
                return i.getTeacher();
        return null;
    }

    public DefaultListModel getCourses(String teacherName) {
        DefaultListModel courses = new DefaultListModel();
        int k = 0;
        for(Course i : catalog.courses)
            if(i.getTeacher().toString().equals(teacherName)) {
                courses.add(k, i);
                k++;
            }
        return courses;
    }

    public DefaultListModel getStudents(Course course) {
        DefaultListModel students = new DefaultListModel();
        int k = 0;
        for(Student i : course.getAllStudents()) {
            students.add(k, i);
        }
        return students;
    }

    public DefaultListModel getGraduatedStudents(Course course) {
        DefaultListModel students = new DefaultListModel();
        int k = 0;
        for(Student i : course.getGraduatedStudents()) {
            students.add(k, i);
        }
        return students;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back) {
            dispose();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getSource() == courseJList && !courseJList.isSelectionEmpty()) {
            course = (Course) courseJList.getSelectedValue();
            DefaultListModel students = getStudents(course);
            DefaultListModel GradStudents = getGraduatedStudents(course);

            studentLabel.setText("Full list of Students:");
            studentJList = new JList(students);
            studentsScrollPane = new JScrollPane(studentJList);
            studentsScrollPane.setBounds(410, 110, 300, 150);
            container.add(studentsScrollPane);

            gradStudentLabel.setText("Graduated Students: ");
            gradStudentJList = new JList(GradStudents);
            gradStudentsScrollPane = new JScrollPane(gradStudentJList);
            gradStudentsScrollPane.setBounds(250, 330, 300, 150);
            container.add(gradStudentsScrollPane);

            //refresh
//            setVisible(false);
//            setVisible(true);
        }
    }
}
