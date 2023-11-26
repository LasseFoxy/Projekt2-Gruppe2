import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Medlemsklasse
class Member {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private int memberID; // ID
    private int age;
    private boolean activestatus;
    private String medlemsNavn;
    private double kontingentBelob;
    private boolean erBetalt;

    // Constructor
    public Member(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email,
            int memberID) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.memberID = memberID;

    }

    public Member(boolean activestatus, int age) {
        this.age = age;
        this.activestatus = activestatus;
    }

    public Member(String medlemsNavn, double kontingentBelob, boolean erBetalt) {
        this.medlemsNavn = medlemsNavn;
        this.kontingentBelob = kontingentBelob;
        this.erBetalt = erBetalt;
    }

    public String getMedlemsNavn() {
        return medlemsNavn;
    }

    public double getKontingentBelob() {
        return kontingentBelob;
    }

    public boolean erBetalt() {
        return erBetalt;
    }

    public void markErBetalt() {
        this.erBetalt = true;
    }

    public void markErUbetal() {
        this.erBetalt = false;
    }

    public int kontingentberegning() {

        if (age < 18) {
            return 1000;
        } else if (age >= 60) {
            int discount = (int) (1600 * 0.25);
            return 1600 - discount;
        } else if (activestatus == false) {
            return 500;
        } else if (age >= 18 && age < 60) {
            return 1600;
        }
        return 0;
    }

    // Getters og Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    // toString metode der kan vise alle stamdata og informationer
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = birthDate.format(formatter);

        return "Indtastede data: " +
                "\nFornavn: " + firstName +
                "\nEfternavn: " + lastName +
                "\nFødselsdato: " + formattedDate +
                "\nTelefonnummer: " + phoneNumber +
                "\nEmail: " + email +
                "\nMedlems ID: " + memberID;
    }

}

class Trainer extends Member {
    private String position; // Juniortræner eller Seniortræner

    public Trainer(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email,
            int memberID, String position) {
        super(firstName, lastName, birthDate, phoneNumber, email, memberID);
        this.position = position;
    }

    // Getters og Setters for 'Position' (Stilling)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nStilling: " + position;
    }
}

class Swimmer extends Member {
    private String memberType; // Aktiv eller Passiv
    private String activityType; // Konkurrencesvømmer eller Fritidssvømmer

    public Swimmer(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email,
            int memberID, String memberType, String activityType) {

        super(firstName, lastName, birthDate, phoneNumber, email, memberID);
        this.memberType = memberType;
        this.activityType = activityType;
    }

    // Getters og Setters for 'memberType' og 'activityType'
    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nMedlemstype: " + memberType +
                "\nAktivitetstype: " + activityType;
    }
}