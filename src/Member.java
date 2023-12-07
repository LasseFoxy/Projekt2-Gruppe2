import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

// Medlemsklasse
public class Member {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private final int memberID;

    // Konstruktør for Member
    public Member(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email, int memberID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.memberID = memberID;
    }

    // Getters og Setters for Member
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

    //Metoder der tjekker om det er en junior eller senior
    public String getAgeCategory() {
        int age = Period.between(this.birthDate, LocalDate.now()).getYears();
        return age < 18 ? "Junior" : "Senior";
    }

    //metode der viser stamdata (Basic member info) inden oprettelse (uden memberID)
    public String basicInfoToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = birthDate.format(formatter);

        return "Fornavn: " + firstName +
                "\nEfternavn: " + lastName +
                "\nFødselsdato: " + formattedDate +
                "\nTelefonnummer: " + phoneNumber +
                "\nEmail: " + email;
    }

    //Metode der viser stamdata med MemberID (efter oprettelse)
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = birthDate.format(formatter);

        return "Medlems ID: " + memberID+
                "\nFornavn: " + firstName +
                "\nEfternavn: " + lastName +
                "\nFødselsdato: " + formattedDate +
                "\nTelefonnummer: " + phoneNumber +
                "\nEmail: " + email;
    }

    //Metode der kun viser Fornavn, Efter, Medlems ID og rolle (Svømmer/Træner)
    public String getShortInfo() {
        String role = "Medlem";
        if (this instanceof Trainer) {
            role = "(Træner)";
        } else if (this instanceof Swimmer) {
            role = "(Svømmer)";
        }
        return this.firstName + " " + this.lastName + " (Medlems ID: " + this.memberID + ") - " + role;
    }
}

//Trænerklasse som nedarver fra Member
class Trainer extends Member {
    private String position;

    //Konstruktør for Trainer
    public Trainer(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email, int memberID, String position) {
        super(firstName, lastName, birthDate, phoneNumber, email, memberID);
        this.position = position;
    }

    // Getters og setters specifikke for trænere
    public void setPosition(String position) { this.position = position; }

    //To String tilføjelse for trænere
    @Override
    public String toString() {
        return super.toString() +
                "\nStilling: " + position + "\n";
    }
}

//Svømmerklasse som nedarver fra Member
class Swimmer extends Member {
    private String membershipStatus; // Aktiv eller Passiv
    private String activityType; // Konkurrencesvømmer eller Fritidssvømmer

    //Konstruktør svømmer
    public Swimmer(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email, int memberID, String memberType, String activityType) {
        super(firstName, lastName, birthDate, phoneNumber, email, memberID);
        this.membershipStatus = memberType;
        this.activityType = activityType;
    }

    // Getters og Setters for tilføjelse for svømmere
    public String getMembershipStatus() { return membershipStatus; }
    public void setMembershipStatus(String membershipStatus) { this.membershipStatus = membershipStatus; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    //To String tilføjelse for svømmere
    @Override
    public String toString() {
        return super.toString() +
                "\nMedlemstype: " + membershipStatus +
                "\nAktivitetstype: " + activityType + "\n";
    }
}