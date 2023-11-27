import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        LocalDate birthDate = null;
        String birthDateString = "";
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Indtast fødseldato (dd.mm.yyyy): ");
            birthDateString = scanner.nextLine();
            try {
                birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig indtasting, prøv igen.");
            }
        }
        String phoneNumber;
        boolean isValidPhoneNumber = false;
        do {
            System.out.print("Indtast telefon nummer: ");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.matches("\\d{8}")) {
                isValidPhoneNumber = true;
            } else {
                System.out.println("Forkert telefon nummer. Indtast 8 cifret telefonnummer.");
            }
        } while (!isValidPhoneNumber);
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
            Swimmer swimmer = new Swimmer(firstName, lastName, birthDate, phoneNumber, email, memberID, memberType,
                    activityType);
            membersList.add(swimmer);

            System.out.println(
                    firstName + " " + lastName + " tilføjet som Svømmer med Medlems ID: " + swimmer.getMemberID());
        } else {
            System.out.println("Oprettelse annulleret.");
        }
    }

    // Metode til at oprette en træner
    public void createTrainer() {
        System.out.print("Indtast fornavn: ");
        String firstName = scanner.nextLine();
        System.out.print("Indtast efternavn: ");
        String lastName = scanner.nextLine();
        LocalDate birthDate = null;
        String birthDateString = "";
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Indtast fødseldato (dd.mm.yyyy): ");
            birthDateString = scanner.nextLine();
            try {
                birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig indtasting, prøv igen.");
            }
        }
        String phoneNumber;
        boolean isValidPhoneNumber = false;
        do {
            System.out.print("Indtast telefon nummer: ");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.matches("\\d{8}")) {
                isValidPhoneNumber = true;
            } else {
                System.out.println("Forkert telefon nummer. Indtast 8 cifret telefonnummer.");
            }
        } while (!isValidPhoneNumber);
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

            System.out.println(
                    firstName + " " + lastName + " tilføjet som Træner med Medlems ID: " + trainer.getMemberID());
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

    public void createKontigentBetaling() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Indtast alder: ");
        int age = scanner.nextInt();

        System.out.println(" Aktiv ja/nej :");
        boolean activestatus = scanner.next().equalsIgnoreCase("ja");

        Member member = new Member(activestatus, age);
        int kontigent = member.kontingentberegning();

        System.out.println("Kontingentet er:" + kontigent + "kr. årligt");

        // System.out.print("\nTryk 1 for at bekræfte eller 2 for at annullere: ");
        int confirmation = scanner.nextInt();
        // scanner.nextLine();

        if (confirmation == 1) {
            // KontigentBetaling kontigentBetaling = new KontigentBetaling(memberID,
            // kontigentbeloeb, payed);
            // System.out.println(kontigentBetaling);
            // System.out.println("Kontigentoplysninger tilføjet til Medlems ID: " +
            // kontigentBetaling.getMemberID());
        } else {
            System.out.println("Oprettelse annulleret.");

        }
    }

    // Markér betalte/ubetalte kontingenter
    private static void markerBetaling(ArrayList<Member> kontingentListe, String medlemsNavn,
            boolean betalt) {
        for (Member medlem : kontingentListe) {
            if (medlem.getMedlemsNavn().equalsIgnoreCase(medlemsNavn)) {
                if (betalt) {
                    medlem.markErBetalt();
                    System.out.println("Kontingent markeret som betalt for " + medlemsNavn);
                } else {
                    medlem.markErUbetal();
                    System.out.println("Kontingent markeret som ubetalt for " + medlemsNavn);
                }
                return;
            }
        }
        System.out.println("Medlem ikke fundet.");
    }

    public static void visKontingentMenu(ArrayList<Member> kontingentListe) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n*** KONTINGENTMENU ***");
            System.out.println("1. Vis tilmeldete medlemmer og kontingent info");
            System.out.println("2. Marker kontingent som betalt");
            System.out.println("3. Marker kontingent som ubetalt");
            System.out.println("4. Afslut programmet");

            int valg = scanner.nextInt();

            switch (valg) {
                case 1:
                    // Vis alle medlemmer og kontingent info
                    for (Member medlem : kontingentListe) {
                        System.out.println("Medlemsnavn: " + medlem.getMedlemsNavn());
                        System.out.println("Kontingentbeløb: " + medlem.getKontingentBelob());
                        System.out.println("Er betalt: " + medlem.erBetalt());
                        System.out.println("------------------------");
                    }
                    break;
                case 2:
                    // Marker kontingent som betalt
                    System.out.println("Indtast medlemsnavn for at markere kontingent som betalt:");
                    String medlemsNavnBetalt = scanner.next();
                    markerBetaling(kontingentListe, medlemsNavnBetalt, true);
                    break;
                case 3:
                    // Marker kontingent som ubetalt
                    System.out.println("Indtast medlemsnavn for at markere kontingent som ubetalt:");
                    String medlemsNavnUbetal = scanner.next();
                    markerBetaling(kontingentListe, medlemsNavnUbetal, false);
                    break;
                case 4:
                    // Afslut programmet
                    System.out.println("Programmet afsluttes.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    public void handleMember() {
        System.out.print("Indtast søgekriterie (fornavn, efternavn, telefonnummer, medlemsID, e-mail): ");
        String searchCriteria = scanner.nextLine();
        List<Member> foundMembers = searchMembers(searchCriteria);

        if (foundMembers.isEmpty()) {
            System.out.println("Ingen medlemmer fundet.");
            return;
        }

        for (int i = 0; i < foundMembers.size(); i++) {
            System.out.println((i + 1) + ". " + memberSummary(foundMembers.get(i)));
        }

        System.out.print("Vælg et nummer eller tryk 0 for at gå tilbage: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0)
            return;
        if (choice < 1 || choice > foundMembers.size()) {
            System.out.println("Ugyldigt valg.");
            return;
        }

        Member selectedMember = foundMembers.get(choice - 1);
        System.out.println();
        System.out.println("Valgt medlem:");
        System.out.println(selectedMember);
        System.out.println("1. Rediger medlem");
        System.out.println("2. Slet medlem");
        System.out.println("3. Gå tilbage");

        System.out.print("Vælg en handling: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                editMember(selectedMember);
                break;
            case 2:
                membersList.remove(selectedMember);
                System.out.println("Medlem slettet.");
                break;
            case 3:
                break;
            default:
                System.out.println("Ugyldigt valg.");
        }
    }

    // Metode til at søge efter medlemmer
    private List<Member> searchMembers(String searchCriteria) {
        List<Member> foundMembers = new ArrayList<>();
        for (Member member : membersList) {
            if (member.getFirstName().equalsIgnoreCase(searchCriteria) ||
                    member.getLastName().equalsIgnoreCase(searchCriteria) ||
                    member.getPhoneNumber().equalsIgnoreCase(searchCriteria) ||
                    String.valueOf(member.getMemberID()).equalsIgnoreCase(searchCriteria) ||
                    member.getEmail().equalsIgnoreCase(searchCriteria)) {
                foundMembers.add(member);
            }
        }
        return foundMembers;
    }

    private String memberSummary(Member member) {
        return String.format("ID: %d - Navn: %s %s - Tlf: %s - Email: %s",
                member.getMemberID(),
                member.getFirstName(),
                member.getLastName(),
                member.getPhoneNumber(),
                member.getEmail());
    }

    private void editMember(Member member) {
        boolean editing = true;

        while (editing) {
            System.out.println("Vælg en information at redigere:");
            System.out.println("1. Fornavn");
            System.out.println("2. Efternavn");
            System.out.println("3. Fødselsdato");
            System.out.println("4. Telefonnummer");
            System.out.println("5. Email");

            if (member instanceof Swimmer) {
                System.out.println("6. Medlemstype (Aktiv/Passiv)");
                System.out.println("7. Aktivitetstype (Konkurrencesvømmer/Fritidssvømmer)");
                System.out.println("8. Afslut redigering");
            } else if (member instanceof Trainer) {
                System.out.println("6. Stilling (Junior/Senior Træner)");
                System.out.println("7. Afslut redigering");
            } else {
                System.out.println("6. Afslut redigering");
            }

            System.out.print("Vælg et nummer: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Rydder scannerens buffer

            switch (choice) {
                case 1:
                    System.out.print("Indtast nyt fornavn: ");
                    member.setFirstName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Indtast nyt efternavn: ");
                    member.setLastName(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Indtast ny fødselsdato (dd.mm.yyyy): ");
                    String birthDateString = scanner.nextLine();
                    LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    member.setBirthDate(birthDate);
                    break;
                case 4:
                    System.out.print("Indtast nyt telefonnummer: ");
                    member.setPhoneNumber(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Indtast ny email: ");
                    member.setEmail(scanner.nextLine());
                    break;
                case 6:
                    if (member instanceof Swimmer) {
                        System.out.print("Indtast ny medlemstype (Aktiv/Passiv): ");
                        ((Swimmer) member).setMemberType(scanner.nextLine());
                    } else if (member instanceof Trainer) {
                        System.out.print("Indtast ny stilling (Junior/Senior Træner): ");
                        ((Trainer) member).setPosition(scanner.nextLine());
                    } else {
                        editing = false;
                    }
                    break;
                case 7:
                    if (member instanceof Swimmer) {
                        System.out.print("Indtast ny aktivitetstype (Konkurrencesvømmer/Fritidssvømmer): ");
                        ((Swimmer) member).setActivityType(scanner.nextLine());
                    } else {
                        editing = false;
                    }
                    break;
                case 8:
                    editing = false;
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }

            if (editing) {
                System.out.println("Ændringer foretaget. Nuværende information:");
                System.out.println(member);
            }
        }
    }
}
