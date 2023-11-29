import java.time.*;
import java.time.format.*;
import java.util.*;

public class MemberManagement {

    private static final Scanner scanner = new Scanner(System.in);
    public static final ArrayList<Member> membersList = new ArrayList<>();

    //Metode til at indsamle Stamdata (Fornavn, Efternavn, Fødselsdato, Telefonnummer, E-mail)
    public static Member gatherBasicMemberInfo() {
            System.out.print("Indtast fornavn: ");
            String firstName = scanner.nextLine();
            System.out.print("Indtast efternavn: ");
            String lastName = scanner.nextLine();

            LocalDate birthDate = null;
            while (birthDate == null) {
                System.out.print("Indtast fødseldato (dd.mm.yyyy): ");
                String birthDateString = scanner.nextLine();
                try {
                    birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                } catch (DateTimeParseException e) {
                    System.out.println("Ugyldig indtasting, prøv igen.");
                }
            }

            String phoneNumber = null;
            while (phoneNumber == null) {
                System.out.print("Indtast telefonnummer: ");
                phoneNumber = scanner.nextLine();
                if (!phoneNumber.matches("\\d{8}")) {
                    System.out.println("Forkert telefonnummer. Indtast 8 cifret telefonnummer.");
                    phoneNumber = null;
                }
            }

            System.out.print("Indtast email: ");
            String email = scanner.nextLine();

            int memberID = 0;
            return new Member(firstName, lastName, birthDate, phoneNumber, email, memberID);
        }

    public static String memberSummary(Member member) {
        return String.format("ID: %d - Navn: %s %s - Tlf: %s - Email: %s",
                member.getMemberID(),
                member.getFirstName(),
                member.getLastName(),
                member.getPhoneNumber(),
                member.getEmail());
    }

        // Metode til at oprette en svømmer
        public static void createSwimmer() {
            Member basicMemberInfo = gatherBasicMemberInfo();
            System.out.print("Vælg medlemstype: 1 for Aktiv, 2 for Passiv: ");
            int memberTypeChoice = scanner.nextInt();
            String memberType = memberTypeChoice == 1 ? "Aktiv" : "Passiv";
            scanner.nextLine();

            System.out.print("Vælg aktivitetstype: 1 for Konkurrencesvømmer, 2 for Fritidssvømmer: ");
            int activityTypeChoice = scanner.nextInt();
            String activityType = activityTypeChoice == 1 ? "Konkurrencesvømmer" : "Fritidssvømmer";
            scanner.nextLine();

            LocalDate birthDate = basicMemberInfo.getBirthDate();
            int age = Period.between(birthDate, LocalDate.now()).getYears();
            String ageCategory = age < 18 ? "Junior" : "Senior";

            System.out.println("\nIndtastede data:");
            System.out.println(basicMemberInfo.basicInfoToString());
            System.out.println("Medlemstype: " + memberType);
            System.out.println("Aktivitetstype: " + activityType);

            System.out.print("Tryk 1 for at bekræfte eller 2 for at annullere: ");
            int confirmation = scanner.nextInt();
            scanner.nextLine();

            if (confirmation == 1) {
                int memberID = findFirstAvailableMemberID();
                Swimmer swimmer = new Swimmer(basicMemberInfo.getFirstName(), basicMemberInfo.getLastName(), basicMemberInfo.getBirthDate(), basicMemberInfo.getPhoneNumber(), basicMemberInfo.getEmail(), memberID, memberType, activityType);
                membersList.add(swimmer);
                System.out.println(basicMemberInfo.getFirstName() + " " + basicMemberInfo.getLastName() + " tilføjet som Svømmer med Medlems ID: " + swimmer.getMemberID());

                //metode der skaber ny payment
                AnnualMemberPayment.createInitialPayment(swimmer);
            }
            else {
                System.out.println("Oprettelse annulleret.");
            }
        }

    // Metode til at oprette en træner
    public static void createTrainer() {
        Member basicMemberInfo = gatherBasicMemberInfo();
        System.out.print("Indtast stilling: 1 for Junior Træner, 2 for Senior Træner: ");
        int positionChoice = scanner.nextInt();
        scanner.nextLine();
        String position = positionChoice == 1 ? "Junior Træner" : "Senior Træner";

        System.out.println("\nIndtastede data:");
        System.out.println(basicMemberInfo.basicInfoToString());
        System.out.println("Stilling: " + position);

        System.out.print("\nTryk 1 for at bekræfte eller 2 for at annullere: ");
        int confirmation = scanner.nextInt();
        scanner.nextLine();

        if (confirmation == 1) {
            int memberID = findFirstAvailableMemberID();
            Trainer trainer = new Trainer(basicMemberInfo.getFirstName(), basicMemberInfo.getLastName(), basicMemberInfo.getBirthDate(), basicMemberInfo.getPhoneNumber(), basicMemberInfo.getEmail(), memberID, position);
            membersList.add(trainer);
            System.out.println(basicMemberInfo.getFirstName() + " " + basicMemberInfo.getLastName() + " tilføjet som Træner med Medlems ID: " + trainer.getMemberID());
        } else {
            System.out.println("Oprettelse annulleret.");
        }
    }

    // Metode til at finde og tildele det første ledige medlemsnummer
    private static int findFirstAvailableMemberID() {
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

    //Metode til at håndtere medlem (rediger/slet stamdata og informationer)
    public static void handleMember() {
        while (true) {
            System.out.print("Søg efter medlem (Fornavn, Efternavn, Telefonnummer, E-mail eller Medlems ID): ");
            String searchCriteria = scanner.nextLine();
            List<Member> foundMembers = searchAllMembers(searchCriteria);
            Member selectedMember = selectMemberFromList(foundMembers);
            System.out.println();

            if (selectedMember == null) {
                System.out.println("Ingen medlemmer fundet");
                System.out.println("1. Søg igen");
                System.out.println("0. Gå tilbage");
                System.out.print("Vælg en handling: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    return;
                }

            } else {
                System.out.println("Valgt medlem:");
                System.out.println(selectedMember);
                System.out.println("1. Rediger medlem");
                System.out.println("2. Slet medlem");
                System.out.println("0. Gå tilbage");

                System.out.print("Vælg en handling: ");
                int action = scanner.nextInt();
                scanner.nextLine();

                switch (action) {
                    case 1:
                        editMember(selectedMember);
                        return;
                    case 2:
                        membersList.remove(selectedMember);
                        System.out.println("Medlem slettet.");
                        return;
                    case 0:
                        return;
                    default:
                        System.out.println("Ugyldigt valg.");
                        return;
                }
            }
        }
    }

    //Hovedmetode for at søge efter medlemmer med søgekriterier
    private static List<Member> searchMembers(String searchCriteria, boolean includeSwimmers, boolean includeTrainers, boolean includeCompetitiveSwimmers, boolean searchAllFields) {
        List<Member> foundMembers = new ArrayList<>();
        for (Member member : membersList) {
            boolean matchesCriteria = (searchAllFields && (
                    member.getFirstName().equalsIgnoreCase(searchCriteria) ||
                            member.getLastName().equalsIgnoreCase(searchCriteria) ||
                            member.getPhoneNumber().equalsIgnoreCase(searchCriteria) ||
                            String.valueOf(member.getMemberID()).equalsIgnoreCase(searchCriteria) ||
                            member.getEmail().equalsIgnoreCase(searchCriteria))) ||
                    (!searchAllFields && (
                            member.getFirstName().equalsIgnoreCase(searchCriteria) ||
                                    member.getLastName().equalsIgnoreCase(searchCriteria) ||
                                    String.valueOf(member.getMemberID()).equalsIgnoreCase(searchCriteria)));

            boolean isSwimmer = member instanceof Swimmer;
            boolean isTrainer = member instanceof Trainer;

            if (matchesCriteria &&
                    ((includeSwimmers && isSwimmer) ||
                            (includeTrainers && isTrainer) ||
                            (includeCompetitiveSwimmers && isSwimmer && ((Swimmer) member).getActivityType().equalsIgnoreCase("Konkurrencesvømmer")))) {
                foundMembers.add(member);
            }
        }
        return foundMembers;
    }


    //Metode til at søge i alle medlemmer (alle kriterier true)
    private static List<Member> searchAllMembers(String searchCriteria) {
        return searchMembers(searchCriteria, true, true, true, true);
    }

    //Metode til at søge efter kun konkurrencesvømmere (kun inkludering af konkurrencesvømmere er true)
    public static List<Member> searchOnlyCompetitionSwimmers(String searchCriteria){
        return searchMembers(searchCriteria, false, false, true, false);
    }

    //Metode til at vælge medlem fra liste
    public static Member selectMemberFromList(List<Member> members) {
        if (members.isEmpty()) {
            return null;
        }

        System.out.println("\nMedlemmer: ");
        for (int i = 0; i < members.size(); i++) {
            System.out.println((i + 1) + ". " + memberSummary(members.get(i)));
        }

        System.out.print("Vælg et nummer eller tryk 0 for at gå tilbage: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            System.out.println("Handling afbrudt.");
            return null;
        }

        if (choice < 1 || choice > members.size()) {
            System.out.println("Ugyldigt valg.");
            return null;
        }

        return members.get(choice - 1);
    }

    private static void editMember(Member member) {
        boolean editing = true;

        while (editing) {
            System.out.println();
            System.out.println("Vælg en information at redigere:");
            System.out.println("1. Fornavn");
            System.out.println("2. Efternavn");
            System.out.println("3. Fødselsdato");
            System.out.println("4. Telefonnummer");
            System.out.println("5. Email");

            if (member instanceof Swimmer) {
                System.out.println("6. Medlemstype (Aktiv/Passiv)");
                System.out.println("7. Aktivitetstype (Konkurrencesvømmer/Fritidssvømmer)");
            } else if (member instanceof Trainer) {
                System.out.println("6. Stilling (Junior/Senior Træner)");
            }
                System.out.println("0. Afslut redigering");

            System.out.print("Vælg et nummer: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    }
                    else System.out.println("Ugyldigt valg. Prøv igen.");
                    break;
                case 0:
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


