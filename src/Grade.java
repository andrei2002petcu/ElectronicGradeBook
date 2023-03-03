public class Grade implements Comparable<Grade>, Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course; //numele cursului

    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getTotal() {
        return (partialScore + examScore);
    }

    @Override
    public int compareTo(Grade gr) {
        if(this.getTotal() > gr.getTotal())
            return 1;
        if(this.getTotal() < gr.getTotal())
            return -1;
        return 0;
    }

    @Override
    protected Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }
}
