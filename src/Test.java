import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException, ParseException, CloneNotSupportedException {

        Catalog catalog = Catalog.getInstance();

        JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader("src/catalog.json"));

        JSONArray jaCourses = (JSONArray) jo.get("courses");
        Iterator i = jaCourses.iterator();
        Iterator j;

        while(i.hasNext()) {
            j = ((Map) i.next()).entrySet().iterator();

            while (j.hasNext()) {
                HashSet<Assistant> assistants = new HashSet<Assistant>();
                HashMap<String, Group> groups = new HashMap<>();

                Map.Entry pair = (Map.Entry) j.next(); //asistenti
                JSONArray jaAssistants = (JSONArray) pair.getValue();
                Iterator k = jaAssistants.iterator();

                while (k.hasNext()) {
                    Iterator l = ((Map) k.next()).entrySet().iterator();

                    while (l.hasNext()) {
                        Map.Entry pairAssistantFirstName = (Map.Entry) l.next();
                        String AssistantFirstName = (String) pairAssistantFirstName.getValue();

                        Map.Entry pairAssistantLastName = (Map.Entry) l.next();
                        String AssistantLastName = (String) pairAssistantLastName.getValue();

                        Assistant assistant = (Assistant) UserFactory.getUser("Assistant", AssistantFirstName, AssistantLastName);
                        assistants.add(assistant);
                    }
                }
                pair = (Map.Entry) j.next(); //teacher
                Map teacherMap = (Map) pair.getValue();
                k = teacherMap.entrySet().iterator();

                Map.Entry pairTeacherFirstName = (Map.Entry) k.next();
                String TeacherFirstName = (String) pairTeacherFirstName.getValue();
                Map.Entry pairTeacherLastName = (Map.Entry) k.next();
                String TeacherLastName = (String) pairTeacherLastName.getValue();

                Teacher teacher = (Teacher) UserFactory.getUser("Teacher", TeacherFirstName, TeacherLastName);

                pair = (Map.Entry) j.next(); //nume curs
                String name = (String) pair.getValue();

                pair = (Map.Entry) j.next(); //grupe
                JSONArray jaGroups = (JSONArray) pair.getValue();
                k = jaGroups.iterator();

                while (k.hasNext()) {
                    Map groupMap = (Map) k.next();
                    Iterator l = groupMap.entrySet().iterator();

                    Map.Entry assistant = (Map.Entry) l.next();
                    Map assistantMap = (Map) assistant.getValue();
                    Iterator m = assistantMap.entrySet().iterator();

                    Map.Entry pairAssistantFirstName = (Map.Entry) m.next();
                    String AssistantFirstName = (String) pairAssistantFirstName.getValue();
                    Map.Entry pairAssistantLastName = (Map.Entry) m.next();
                    String AssistantLastName = (String) pairAssistantLastName.getValue();
                    Assistant groupAssistant = (Assistant) UserFactory.getUser("Assistant", AssistantFirstName, AssistantLastName);

                    Map.Entry students = (Map.Entry) l.next();
                    JSONArray jaStudents = (JSONArray) students.getValue();
                    m = jaStudents.iterator();

                    Map.Entry pairID = (Map.Entry) l.next();
                    String ID = (String) pairID.getValue();

                    Group group = new Group(ID, groupAssistant);

                    while (m.hasNext()) {
                        Map studentsMap = (Map) m.next();
                        Iterator n = studentsMap.entrySet().iterator();

                        Map.Entry pairStudentFirstName = (Map.Entry) n.next();
                        String StudentFirstName = (String) pairStudentFirstName.getValue();
                        Map.Entry pairStudentLastName = (Map.Entry) n.next();
                        String StudentLastName = (String) pairStudentLastName.getValue();
                        ;

                        group.add((Student) UserFactory.getUser("Student", StudentFirstName, StudentLastName));
                    }
                    groups.put(ID, group);
                }
                pair = (Map.Entry) j.next(); //type
                String type = (String) pair.getValue();

                pair = (Map.Entry) j.next(); //strategy
                String strategy = (String) pair.getValue();

                Course course;
                if (type.equals("Fullcourse")) {
                    course = new FullCourse.FullCourseBuilder(name)
                            .teacher(teacher)
                            .assistants(assistants)
                            .groups(groups)
                            .build();
                }
                else {
                    course = new PartialCourse.PartialCourseBuilder(name)
                            .teacher(teacher)
                            .assistants(assistants)
                            .groups(groups)
                            .build();
                }
                if(strategy.equals("BestExamScore"))
                    course.setStrategy(new BestExamScore());
                else if(strategy.equals("BestPartialScore"))
                    course.setStrategy(new BestPartialScore());
                else course.setStrategy(new BestTotalScore());

                catalog.addCourse(course);
            }
        }
        JSONArray jaExamScore = (JSONArray) jo.get("examScores");
        i = jaExamScore.iterator();

        HashSet<Grade> grades = new HashSet<>();
        while(i.hasNext()) {
            j = ((Map) i.next()).entrySet().iterator();

            while(j.hasNext()) {
                Grade grade = new Grade();

                Map.Entry pair = (Map.Entry) j.next(); //teacher
                Map teacherMap = (Map) pair.getValue();
                Iterator k = teacherMap.entrySet().iterator();

                Map.Entry pairTeacherFirstName = (Map.Entry) k.next();
                String TeacherFirstName = (String) pairTeacherFirstName.getValue();
                Map.Entry pairTeacherLastName = (Map.Entry) k.next();
                String TeacherLastName = (String) pairTeacherLastName.getValue();
                Teacher teacher = (Teacher) UserFactory.getUser("Teacher", TeacherFirstName, TeacherLastName);

                pair = (Map.Entry) j.next(); //student
                Map studentMap = (Map) pair.getValue();
                k = studentMap.entrySet().iterator();

                Map.Entry pairStudentFirstName = (Map.Entry) k.next();
                String StudentFirstName = (String) pairStudentFirstName.getValue();
                Map.Entry pairStudentLastName = (Map.Entry) k.next();
                String StudentLastName = (String) pairStudentLastName.getValue();
                Student student = (Student) UserFactory.getUser("Student", StudentFirstName, StudentLastName);
                grade.setStudent(student);

                pair = (Map.Entry) j.next();
                grade.setExamScore((Double) pair.getValue());

                pair = (Map.Entry) j.next();
                grade.setCourse((String) pair.getValue());

                grades.add(grade);
            }
        }
        JSONArray jaPartialScore = (JSONArray) jo.get("partialScores");
        i = jaPartialScore.iterator();

        while(i.hasNext()) {
            j = ((Map) i.next()).entrySet().iterator();

            while(j.hasNext()) {
                Grade grade = new Grade();

                Map.Entry pair = (Map.Entry) j.next(); //student
                Map studentMap = (Map) pair.getValue();
                Iterator k = studentMap.entrySet().iterator();

                Map.Entry pairStudentFirstName = (Map.Entry) k.next();
                String StudentFirstName = (String) pairStudentFirstName.getValue();
                Map.Entry pairStudentLastName = (Map.Entry) k.next();
                String StudentLastName = (String) pairStudentLastName.getValue();
                Student student = (Student) UserFactory.getUser("Student", StudentFirstName, StudentLastName);
                grade.setStudent(student);

                pair = (Map.Entry) j.next(); //assistant

                pair = (Map.Entry) j.next();
                grade.setPartialScore((Double) pair.getValue());

                pair = (Map.Entry) j.next();
                grade.setCourse((String) pair.getValue());

                for(Grade itr : grades)
                    if(itr.getStudent().toString().equals(grade.getStudent().toString()) && itr.getCourse().equals(grade.getCourse()))
                        itr.setPartialScore(grade.getPartialScore());
            }
        }
        for(Course m : catalog.courses)
            for(Grade n : grades)
                if(m.getName().equals(n.getCourse()))
                    m.addGrade(n);

        //TEST BACKUP
        catalog.courses.get(0).makeBackup();
        System.out.println(catalog.courses.get(0).getSnapshot());

        catalog.courses.get(0).getGrades().last().setExamScore(1000.0);
        Iterator z = catalog.courses.get(0).getAllStudentGrades().keySet().iterator();
        while(z.hasNext()) {
            Student std = (Student) z.next();
            System.out.println(std + " : " + catalog.courses.get(0).getAllStudentGrades().get(std).getPartialScore());
        }
        catalog.courses.get(0).undo();
        System.out.println("Dupa undo: \n" + catalog.courses.get(0).getSnapshot());

        //GUI
        SearchPage loginPage = new SearchPage(catalog);
    }
}