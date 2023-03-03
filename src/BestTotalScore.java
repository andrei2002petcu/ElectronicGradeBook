import java.util.TreeSet;

public class BestTotalScore implements Strategy {
    @Override
    public Student execStrategy(TreeSet<Grade> grades) {
        Grade bestGrade = grades.first();
        for(Grade i : grades)
            if(i.getTotal() > bestGrade.getTotal())
                bestGrade = i;
        return bestGrade.getStudent();
    }
}