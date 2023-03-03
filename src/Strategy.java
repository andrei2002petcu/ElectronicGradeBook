import java.util.TreeSet;

public interface Strategy {
    public Student execStrategy(TreeSet<Grade> grades);
}