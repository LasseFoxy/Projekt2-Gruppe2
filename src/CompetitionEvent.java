import java.time.LocalDate;

public class CompetitionEvent {
    private String meetName;
    private LocalDate meetDate;
    private String swimmerName;
    private int swimmerID;
    private String discipline;
    private String time;
    private int placement;

    // Konstruktør
    public CompetitionEvent(String meetName, LocalDate meetDate, String swimmerName, int swimmerID,
                            String discipline, String time, int placement) {
        this.meetName = meetName;
        this.meetDate = meetDate;
        this.swimmerName = swimmerName;
        this.swimmerID = swimmerID;
        this.discipline = discipline;
        this.time = time;
        this.placement = placement;
    }

    // Getters
    public String getMeetName() {
        return meetName;
    }

    public LocalDate getMeetDate() {
        return meetDate;
    }

    public String getSwimmerName() {
        return swimmerName;
    }

    public int getSwimmerID() {
        return swimmerID;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getTime() {
        return time;
    }

    public int getPlacement() {
        return placement;
    }

    // Setters
    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public void setMeetDate(LocalDate meetDate) {
        this.meetDate = meetDate;
    }

    public void setSwimmerName(String swimmerName) {
        this.swimmerName = swimmerName;
    }

    public void setSwimmerID(int swimmerID) {
        this.swimmerID = swimmerID;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }
}
