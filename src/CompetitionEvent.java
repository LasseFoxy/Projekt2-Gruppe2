import java.io.Serializable;
import java.time.LocalDate;

//Klasse der omhandler konkurrence events med stævnenavn, dato, tid,placering m.fl.
public class CompetitionEvent implements Serializable {
    private final String meetName;
    private final LocalDate meetDate;
    private final String swimmerName;
    private final int swimmerID;
    private final String discipline;
    private final String time;
    private final int placement;

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
}