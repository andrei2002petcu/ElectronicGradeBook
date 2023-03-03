import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentPage extends JFrame implements ActionListener, ListSelectionListener {

    Catalog catalog;
    Student student;
    Container container;
    JLabel noStudent;
    JList courseJList;
    JScrollPane scrollPane;
    JLabel examScore;
    JLabel partialScore;
    JLabel totalScore;
    JLabel courseLabel;
    JLabel gradesLabel;
    JLabel teacherLabel;
    JLabel courseAssistantsLabel;
    JLabel groupAssistantLabel;
    JLabel infoLabel;
    JLabel welcome;
    JLabel bestStudent;
    JButton back;
    public StudentPage(Catalog catalog, String studentName) {

        this.catalog = catalog;
        student = isStudent(studentName);
        container = getContentPane();
        container.setLayout(null);

        back = new JButton("BACK");
        back.setBounds(650, 450, 100, 30);
        back.addActionListener(this);
        container.add(back);

        if(student == null) {
            noStudent = new JLabel("THIS STUDENT DOES NOT EXIST!!!");
            noStudent.setBounds(290, 170, 300, 30);
            container.add(noStudent);
            setGenericSettings();
        }
        else {

            DefaultListModel courses = getCourses(studentName);

            courseJList = new JList(courses);
            scrollPane = new JScrollPane(courseJList);
            scrollPane.setBounds(100, 110, 300, 150);

            courseLabel = new JLabel("Courses:");
            courseLabel.setBounds(100, 80, 100, 30);
            gradesLabel = new JLabel("");
            gradesLabel.setBounds(420, 80, 100, 30);

            examScore = new JLabel("");
            partialScore = new JLabel("");
            totalScore = new JLabel("");
            partialScore.setBounds(420, 120, 150, 30);
            examScore.setBounds(420, 170, 150, 30);
            totalScore.setBounds(420, 220, 150, 30);

            groupAssistantLabel = new JLabel("");
            teacherLabel = new JLabel("");
            courseAssistantsLabel = new JLabel("");
            bestStudent = new JLabel("");
            groupAssistantLabel.setBounds(200, 300, 400, 30);
            teacherLabel.setBounds(200, 350, 400, 30);
            courseAssistantsLabel.setBounds(200, 400, 400, 30);
            bestStudent.setBounds(200, 450, 400, 30);

            infoLabel = new JLabel();
            infoLabel.setBounds(100, 275, 400, 30);
            welcome = new JLabel("WELCOME, " + studentName + "!");
            welcome.setBounds(300, 30, 300, 30);

            courseJList.addListSelectionListener(this);
            addComponents();
            setGenericSettings();
        }
    }

    public void setGenericSettings() {
        setVisible(true);
        setTitle("Student Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 800, 550);
        setResizable(false);
    }

    public void addComponents() {
        container.add(scrollPane);
        container.add(courseLabel);
        container.add(gradesLabel);
        container.add(partialScore);
        container.add(examScore);
        container.add(totalScore);
        container.add(groupAssistantLabel);
        container.add(teacherLabel);
        container.add(courseAssistantsLabel);
        container.add(bestStudent);
        container.add(infoLabel);
        container.add(welcome);
    }

    public Student isStudent(String studentName) {
        for(Course i : catalog.courses)
            for(Student j : i.getAllStudents())
                if(j.toString().equals(studentName))
                    return j;
        return null;
    }

    public DefaultListModel getCourses(String studentName) {
        DefaultListModel courses = new DefaultListModel();
        int k = 0;
        for(Course i : catalog.courses)
            for(Student j : i.getAllStudents())
                if(j.toString().equals(studentName)) {
                    courses.add(k, i);
                    k++;
                    break;
                }
        return courses;
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
            Course course = (Course) courseJList.getSelectedValue();
            Grade grade = course.getGrade(student);
            partialScore.setText("Partial Score: " + grade.getPartialScore().toString());
            examScore.setText("Exam Score: " + grade.getExamScore().toString());
            totalScore.setText("Total Score: " + grade.getTotal().toString());
            gradesLabel.setText("Grades:");

            groupAssistantLabel.setText("Student's Assistant: " + course.getStudentAssistant(student));
            teacherLabel.setText("Course Teacher: " + course.getTeacher());
            courseAssistantsLabel.setText("Course Assistants: " + course.getAssistants());
            bestStudent.setText("Best Student: " + course.getBestStudent());
            infoLabel.setText("Info about the Course:");

        }
    }
}
