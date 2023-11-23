import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MemberManagement {

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Member> membersList = new ArrayList<>();

    // Metode til at oprette en svømmer
    public void createSwimmer() {
        System.out.print("Indtast fornavn: ");
        String firstName = scanner.nextLine();
        System.out.print("Indtast efternavn: ");
        String lastName = scanner.nextLine();
        System.out.print("Indtast fødseldato (dd.mm.yyyy): ");
        String birthDateString = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.print("Indtast telefonnummer: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Indtast email: ");
        String email = scanner.nextLine();
        System.out.print("Vælg medlemstype: 1 for Aktiv, 2 for Passiv: ");
        int memberTypeChoice = scanner.nextInt();
        String memberType = memberTypeChoice == 1 ? "Aktiv" : "Passiv";
        scanner.nextLine();
        System.out.print("Vælg aktivitetstype: 1 for Konkurrencesvømmer, 2 for Fritidssvømmer: ");
        int activityTypeChoice = scanner.nextInt();
        String activityType = activityTypeChoice == 1 ? "Konkurrencesvømmer" : "Fritidssvømmer";
        scanner.nextLine();

        int age = Period.between(birthDate, LocalDate.now()).getYears();
        String ageCategory = age < 18 ? "Junior" : "Senior";

        // Viser indtastede data
        System.out.println("\nIndtastede data:");
        System.out.println("Fornavn: " + firstName);
        System.out.println("Efternavn: " + lastName);
        System.out.println("Fødselsdato: " + birthDateString + " (" + ageCategory + ")");
        System.out.println("Telefonnummer: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Medlemstype: " + memberType);
        System.out.println("Aktivitetstype: " + activityType);

        System.out.print("Tryk 1 for at bekræfte eller 2 for at annullere: ");
        int confirmation = scanner.nextInt();
        scanner.nextLine();

        if (confirmation == 1) {
            int memberID = findFirstAvailableMemberID();
            Swimmer swimmer = new Swimmer(firstName, lastName, birthDate, phoneNumber, email, memberID, memberType, activityType);
            membersList.add(swimmer);
            System.out.println(swimmer);
            System.out.println(firstName+" "+lastName+" tilføjet som Svømmer med Medlems ID: "+ swimmer.getMemberID());
        }
        else {
                System.out.println("Oprettelse annulleret.");
        }
    }

    // Metode til at oprette en træner
    public void createTrainer() {
        System.out.print("Indtast fornavn: ");
        String firstName = scanner.nextLine();
        System.out.print("Indtast efternavn: ");
        String lastName = scanner.nextLine();
        System.out.print("Indtast fødselsdato (dd.mm.yyyy): ");
        String birthDateString = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.print("Indtast telefonnummer: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Indtast email: ");
        String email = scanner.nextLine();
        System.out.print("Indtast stilling: 1 for Junior Træner, 2 for Senior Træner: ");
        int positionChoice = scanner.nextInt();
        scanner.nextLine();
        String position = positionChoice == 1 ? "Junior Træner" : "Senior Træner";



        // Viser indtastede data
        System.out.println("\nIndtastede data:");
        System.out.println("Fornavn: " + firstName);
        System.out.println("Efternavn: " + lastName);
        System.out.println("Fødselsdato: " + birthDateString);
        System.out.println("Telefonnummer: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Stilling: " + position);

        System.out.print("\nTryk 1 for at bekræfte eller 2 for at annullere: ");
        int confirmation = scanner.nextInt();
        scanner.nextLine();

        if (confirmation == 1) {
            int memberID = findFirstAvailableMemberID();
            Trainer trainer = new Trainer(firstName, lastName, birthDate, phoneNumber, email, memberID, position);
            membersList.add(trainer);
            System.out.println(trainer);
            System.out.println(firstName + " " + lastName + " tilføjet som Træner med Medlems ID: " + trainer.getMemberID());
        } else {
            System.out.println("Oprettelse annulleret.");
        }
    }

    // Metode til at finde det første ledige medlemsnummer
    private int findFirstAvailableMemberID() {
        int memberID = 1;
        while (true) {
            boolean isTaken = false;
            for (Member member : membersList) {
                if (member.getMemberID() == memberID) {
                    isTaken = true;
                    break;
                }
            }
            if (!isTaken) {
                return memberID;
            }
            memberID++;
        }
    }
}
