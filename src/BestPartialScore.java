import java.util.TreeSet;

public class BestPartialScore implements Strategy {
    @Override
    public Student execStrategy(TreeSet<Grade> grades) {
        Grade bestGrade = grades.first();
        for(Grade i : grades)
            if(i.getPartialScore() > bestGrade.getPartialScore())
                bestGrade = i;
        return bestGrade.getStudent();
    }
}
