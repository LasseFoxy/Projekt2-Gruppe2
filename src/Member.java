import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Medlemsklasse
public class Member {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private int memberID;

    // Constructor
    public Member(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email, int memberID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.memberID = memberID;
    }

    // Getters og Setters

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getMemberID() { return memberID; }
    public void setMemberID(int memberID) { this.memberID = memberID; }


    //toString metode der kan vise alle stamdata og informationer
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = birthDate.format(formatter);

        return  "Medlems ID: " + memberID +
                "\nFornavn: " + firstName +
                "\nEfternavn: " + lastName +
                "\nFødselsdato: " + formattedDate +
                "\nTelefonnummer: " + phoneNumber +
                "\nEmail: " + email;
    }
}

class Trainer extends Member {
    private String position; // Juniortræner eller Seniortræner

    public Trainer(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email, int memberID, String position) {
        super(firstName, lastName, birthDate, phoneNumber, email, memberID);
        this.position = position;
    }

    // Getters og Setters for 'Position' (Stilling)
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    @Override
    public String toString() {
        return super.toString() +
                "\nStilling: " + position + "\n";
    }
}

class Swimmer extends Member {
    private String memberType; // Aktiv eller Passiv
    private String activityType; // Konkurrencesvømmer eller Fritidssvømmer

    public Swimmer(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email, int memberID, String memberType, String activityType) {
        super(firstName, lastName, birthDate, phoneNumber, email, memberID);
        this.memberType = memberType;
        this.activityType = activityType;
    }

    // Getters og Setters for 'memberType' og 'activityType'
    public String getMemberType() { return memberType; }
    public void setMemberType(String memberType) { this.memberType = memberType; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    @Override
    public String toString() {
        return super.toString() +
                "\nMedlemstype: " + memberType +
                "\nAktivitetstype: " + activityType + "\n";
    }
}
