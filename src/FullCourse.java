import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FullCourse extends Course{
    public FullCourse(FullCourseBuilder fullCourseBuilder) {
        super(fullCourseBuilder);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        HashMap<Student, Grade> studentGradeHashMap = getAllStudentGrades();
        ArrayList<Student> GraduatedStudents = new ArrayList<>();
        Iterator i = studentGradeHashMap.keySet().iterator();
        while(i.hasNext()){
            Student student = (Student) i.next();
            if(studentGradeHashMap.get(student).getPartialScore() >= 3 && studentGradeHashMap.get(student).getExamScore() >= 2)
                GraduatedStudents.add(student);
        }
        return GraduatedStudents;
    }

    public static class FullCourseBuilder extends Course.CourseBuilder {

        public FullCourseBuilder(String name) {
            super(name);
        }

        @Override
        public Course build() {
            return new FullCourse(this);
        }
    }
}
