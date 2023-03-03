import java.lang.reflect.Array;
import java.util.ArrayList;

public class Catalog {
    private static Catalog obj = new Catalog();
    ArrayList<Course> courses = new ArrayList<>();

    private Catalog(){}

    public static Catalog getInstance() {
        return obj;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
}
