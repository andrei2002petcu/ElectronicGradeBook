import java.util.TreeSet;

public class BestExamScore implements Strategy {
    @Override
    public Student execStrategy(TreeSet<Grade> grades) {
        Grade bestGrade = grades.first();
        for(Grade i : grades)
            if(i.getExamScore() > bestGrade.getExamScore())
                bestGrade = i;
        return bestGrade.getStudent();
    }
}