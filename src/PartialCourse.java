import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PartialCourse extends Course {

    public PartialCourse(PartialCourseBuilder partialCourseBuilder) {
        super(partialCourseBuilder);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        HashMap<Student, Grade> studentGradeHashMap = getAllStudentGrades();
        ArrayList<Student> GraduatedStudents = new ArrayList<>();
        Iterator i = studentGradeHashMap.keySet().iterator();
        while(i.hasNext()){
            Student student = (Student) i.next();
            if(studentGradeHashMap.get(student).getTotal() >= 5)
                GraduatedStudents.add(student);
        }
        return GraduatedStudents;
    }

    public static class PartialCourseBuilder extends CourseBuilder {

        public PartialCourseBuilder(String name) {
            super(name);
        }

        @Override
        public Course build() {
            return new PartialCourse(this);
        }
    }
}
