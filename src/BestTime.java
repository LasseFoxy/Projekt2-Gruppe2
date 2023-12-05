import java.time.LocalDate;

// Klasse der repræsenterer bedste svømmetider med information om medlem, tidstype, disciplin, dato og tid.
public class BestTime {
    public enum TimeType {
        TRAINING, COMPETITION
    }
    private String firstName;
    private String lastName;
    private int memberID;
    private TimeType type;
    private String discipline;
    private LocalDate date;
    private String time; // Tid som en streng i formatet MM:ss.hh

    //Konstruktør
    public BestTime(TimeType type, String discipline, LocalDate date, String time, String firstName, String lastName, int memberID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberID = memberID;
        this.type = type;
        this.discipline = discipline;
        this.date = date;
        this.time = time;

    }

    // Getters og setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter og setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public TimeType getType() {
        return type;
    }

    public void setType(TimeType type) {
        this.type = type;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
