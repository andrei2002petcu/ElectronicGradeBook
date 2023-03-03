import java.util.*;

public abstract class Course {
    private String name;
    private Teacher teacher;
    private HashSet<Assistant> assistants;
    private TreeSet<Grade> grades;
    private HashMap<String, Group> groups;
    private int creditPts;
    private Strategy strategy;
    private Snapshot snapshot = new Snapshot();

    public Course(CourseBuilder builder) {
        this.name = builder.name;
        this.teacher = builder.teacher;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
        this.creditPts = builder.creditPts;
        this.strategy = builder.strategy;
    }

    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public int getCreditPts() {
        return creditPts;
    }

    public void addAssistant(String ID, Assistant assistant) {
        assistants.add(assistant);
    }

    public void addStudent(String ID, Student student) {
        groups.get(ID).add(student);
    }

    public void addGroup(Group group) {
        groups.put(group.getID(), group);
    }

    public void addGroup(String ID, Assistant assistant) {
        addGroup(new Group(ID, assistant));
    }

    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        addGroup(new Group(ID, assistant, comp));
    }

    public Grade getGrade(Student student) {
        Iterator<Grade> i = grades.iterator();
        while (i.hasNext()) {
            Grade grade = i.next();
            if (grade.getStudent().toString().equals(student.toString()))
                return grade;
        }
        return null;
    }

    public void addGrade(Grade grade) {
        if(grades == null)
            grades = new TreeSet<>();
        grades.add(grade);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentArrayList = new ArrayList<Student>();
        Iterator i = groups.keySet().iterator();
        while (i.hasNext()) {
            Group j = groups.get(i.next());
            Iterator iterator = j.iterator();
            while(iterator.hasNext())
                studentArrayList.add((Student) iterator.next());
        }
        return studentArrayList;
    }

    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> studentGradeHashMap = new HashMap<Student, Grade>();
        ArrayList<Student> studentArrayList = getAllStudents();

        for (int i = 0; i < studentArrayList.size(); i++)
            studentGradeHashMap.put(studentArrayList.get(i), getGrade(studentArrayList.get(i)));
        return  studentGradeHashMap;
    }

    public Assistant getStudentAssistant(Student student) {
        Iterator i = groups.keySet().iterator();
        while (i.hasNext()) {
            Group group = (Group) groups.get(i.next());
            for(Student j : group)
                if(j.toString().equals(student.toString()))
                    return group.getAssistant();
        }
        return null;
    }

    public HashSet<Assistant> getAssistants() {
        return assistants;
    }

    public TreeSet<Grade> getGrades() {
        return grades;
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Student getBestStudent() {
        return strategy.execStrategy(grades);
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    public static abstract class CourseBuilder{
        private final String name;
        private Teacher teacher;
        private HashSet<Assistant> assistants;
        private TreeSet<Grade> grades;
        private HashMap<String, Group> groups;
        private int creditPts;
        private Strategy strategy;

        public CourseBuilder(String name) {
            this.name = name;
        }

        public CourseBuilder teacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder assistants(HashSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder grades(TreeSet<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBuilder groups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }

        public CourseBuilder creditPts(int creditPts) {
            this.creditPts = creditPts;
            return this;
        }

        public CourseBuilder strategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public abstract Course build();
    };

    private class Snapshot {
        private TreeSet<Grade> backup = new TreeSet<>();

        @Override
        public String toString() {
            String buffer = "";
            for(Grade i : backup)
                buffer += i.getStudent() + " : " + i.getTotal() + "\n";
            return buffer;
        }
    }

    public void makeBackup() throws CloneNotSupportedException {
        for(Grade i : grades) {
            snapshot.backup.add((Grade) i.clone());
        }
    }

    public void undo() {
        grades = snapshot.backup;
    }
}