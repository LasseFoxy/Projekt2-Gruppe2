import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Klasse der repræsenterer bedste svømmetider med information om medlem, tidstype, disciplin, dato og tid.
public class BestTime implements Serializable {
    public enum TimeType {
        TRAINING, COMPETITION
    }
    private final String firstName;
    private final String lastName;
    private final int memberID;
    private final TimeType type;
    private final String discipline;
    private final LocalDate date;
    private final String time; // Tid som en streng i formatet MM:ss.hh

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

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getMemberID() {
        return memberID;
    }

    public TimeType getType() {
        return type;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String typeInDanish = this.type == TimeType.TRAINING ? "Træning" : "Konkurrence";
        return String.format("Disciplin: %-12s \t Type: %-10s \t Dato: %-10s \t Tid: %-8s",
                this.discipline,
                typeInDanish,
                this.date.format(formatter),
                this.time);
    }
}
