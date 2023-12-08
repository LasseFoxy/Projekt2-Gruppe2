import java.time.*;
import java.util.*;

public class MemberManagement {
    private static final Scanner scanner = new Scanner(System.in);
    public static ArrayList<Member> membersList = new ArrayList<>();

    //Metode til at indsamle Stamdata (Fornavn, Efternavn, Fødselsdato, Telefonnummer, E-mail)
    public static Member gatherBasicMemberInfo() {
            System.out.print("Indtast fornavn: ");
            String firstName = scanner.nextLine();
            System.out.print("Indtast efternavn: ");
            String lastName = scanner.nextLine();

            Scanner scanner = new Scanner(System.in);
            LocalDate birthDate = SearchAndInputMethods.promptForDate(scanner, "Indtast fødselsdato (dd.mm.yyyy): ");
            String phoneNumber = SearchAndInputMethods.promptForPhoneNumber(scanner);
            System.out.print("Indtast email: ");
            String email = scanner.nextLine();

            int memberID = 0;
            return new Member(firstName, lastName, birthDate, phoneNumber, email, memberID);
        }

        // Metode til at oprette en svømmer
        public static void createSwimmer() {
            Member basicMemberInfo = gatherBasicMemberInfo();

            int memberTypeChoice = SearchAndInputMethods.promptForChoice(scanner, "Vælg medlemstype: 1 for Aktiv, 2 for Passiv: ", 1, 2);
            String memberType = memberTypeChoice == 1 ? "Aktiv" : "Passiv";

            int activityTypeChoice = SearchAndInputMethods.promptForChoice(scanner, "Vælg aktivitetstype: 1 for Konkurrencesvømmer, 2 for Fritidssvømmer: ", 1, 2);
            String activityType = activityTypeChoice == 1 ? "Konkurrencesvømmer" : "Fritidssvømmer";

            System.out.println("\nIndtastede data:");
            System.out.println(basicMemberInfo.basicInfoToString());
            System.out.println("Medlemstype: " + memberType);
            System.out.println("Aktivitetstype: " + activityType);
            int confirmation = SearchAndInputMethods.promptForChoice(scanner, "Tryk 1 for at bekræfte eller 2 for at annullere: ", 1, 2);

            if (confirmation == 1) {
                int memberID = findFirstAvailableMemberID();
                Swimmer swimmer = new Swimmer(basicMemberInfo.getFirstName(), basicMemberInfo.getLastName(), basicMemberInfo.getBirthDate(), basicMemberInfo.getPhoneNumber(), basicMemberInfo.getEmail(), memberID, memberType, activityType);
                membersList.add(swimmer);
                System.out.println(swimmer.getShortInfo()+" - oprettet som medlem");

                //Metode der skaber ny payment ved oprettelse af svømmer (1 år ude i fremtiden)
                AnnualPaymentManagement.createInitialPayment(swimmer);
            }
            else {
                System.out.println("Oprettelse annulleret.");
            }
        }

    // Metode til at oprette en træner
    public static void createTrainer() {
        Member basicMemberInfo = gatherBasicMemberInfo();

        int positionChoice = SearchAndInputMethods.promptForChoice(scanner, "Indtast stilling: 1 for Junior Træner, 2 for Senior Træner: ", 1, 2);
        String position = positionChoice == 1 ? "Junior Træner" : "Senior Træner";

        System.out.println("\nIndtastede data:");
        System.out.println(basicMemberInfo.basicInfoToString());
        System.out.println("Stilling: " + position);

        int confirmation = SearchAndInputMethods.promptForChoice(scanner, "\nTryk 1 for at bekræfte eller 2 for at annullere: ", 1, 2);

        if (confirmation == 1) {
            int memberID = findFirstAvailableMemberID();
            Trainer trainer = new Trainer(basicMemberInfo.getFirstName(), basicMemberInfo.getLastName(), basicMemberInfo.getBirthDate(), basicMemberInfo.getPhoneNumber(), basicMemberInfo.getEmail(), memberID, position);
            membersList.add(trainer);
            System.out.println(trainer.getShortInfo() + " - oprettet som træner");
        } else {
            System.out.println("Oprettelse annulleret.");
        }
    }

    // Metode til at finde og tildele det første ledige medlemsnummer startende fra 1 og s
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
    public static void handleMemberInfo() {
        while (true) {
            System.out.print("Søg efter medlem (Fornavn, Efternavn, Telefonnummer, E-mail eller Medlems ID): ");
            String searchCriteria = scanner.nextLine();
            List<Member> foundMembers = SearchAndInputMethods.searchAllMembers(searchCriteria);
            Member selectedMember = SearchAndInputMethods.selectMemberFromList(foundMembers);
            System.out.println();

            if (selectedMember == null) {
                System.out.println("Ingen medlemmer fundet");
                System.out.println("1. Søg igen");
                System.out.println("0. Gå tilbage");

                int choice = SearchAndInputMethods.promptForChoice(scanner, "Indtast valg: ", 0, 1);

                if (choice == 0) {
                    return;
                }
            }
            else {
                System.out.println("Valgt medlem:");
                System.out.println(selectedMember);
                System.out.println("1. Rediger medlem");
                System.out.println("2. Slet medlem");
                System.out.println("0. Gå tilbage");

                int action = SearchAndInputMethods.promptForChoice(scanner, "Indtast valg: ", 0, 1, 2);

                switch (action) {
                    case 1:
                        editMember(selectedMember);
                        return;
                    case 2:
                        deleteMember(selectedMember);
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

            System.out.print("Indtast valg: ");
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
                    LocalDate birthDate = SearchAndInputMethods.promptForDate(scanner, "Indtast ny fødselsdato (dd.mm.yyyy): ");
                    member.setBirthDate(birthDate);
                    break;
                case 4:
                    String newPhoneNumber = SearchAndInputMethods.promptForPhoneNumber(scanner);
                    member.setPhoneNumber(newPhoneNumber);
                    break;
                case 5:
                    System.out.print("Indtast ny email: ");
                    member.setEmail(scanner.nextLine());
                    break;
                case 6:
                    if (member instanceof Swimmer) {
                        System.out.println("Vælg ny medlemstype:");
                        System.out.println("1. Aktiv");
                        System.out.println("2. Passiv");
                        int membershipChoice = SearchAndInputMethods.promptForChoice(scanner, "Indtast valg: ", 1, 2);
                        String membershipStatus = membershipChoice == 1 ? "Aktiv" : "Passiv";
                        ((Swimmer) member).setMembershipStatus(membershipStatus);
                    } else if (member instanceof Trainer) {
                        System.out.println("Vælg ny stilling:");
                        System.out.println("1. Junior Træner");
                        System.out.println("2. Senior Træner");
                        int positionChoice = SearchAndInputMethods.promptForChoice(scanner, "Indtast valg: ", 1, 2);
                        String position = positionChoice == 1 ? "Junior Træner" : "Senior Træner";
                        ((Trainer) member).setPosition(position);
                    } else {
                        editing = false;
                    }
                    break;
                case 7:
                    if (member instanceof Swimmer) {
                        System.out.println("Vælg ny aktivitetstype:");
                        System.out.println("1. Konkurrencesvømmer");
                        System.out.println("2. Fritidssvømmer");
                        int activityTypeChoice = SearchAndInputMethods.promptForChoice(scanner, "Indtast valg: ", 1, 2);
                        String activityType = activityTypeChoice == 1 ? "Konkurrencesvømmer" : "Fritidssvømmer";
                        ((Swimmer) member).setActivityType(activityType);
                    } else {
                        System.out.println("Ugyldigt valg. Prøv igen.");
                    }
                    break;
                case 0:
                    editing = false;
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }

            if (editing) {
                System.out.println();
                System.out.println("Ændringer foretaget. Nuværende information:");
                System.out.println(member);
                System.out.println("Tryk på enter for at gå videre...");
                scanner.nextLine();
            }
        }
    }

    private static void deleteMember(Member member) {
        System.out.println("Er du sikker på, at du vil slette følgende medlem?");
        System.out.println(member);
        System.out.println("1. Ja, slet medlem");
        System.out.println("2. Nej, gå tilbage");

        int confirmation = SearchAndInputMethods.promptForChoice(scanner, "Indtast valg: ", 1, 2);

        if (confirmation == 1) {
            membersList.remove(member);
            System.out.println("Medlem slettet.");
        } else {
            System.out.println("Sletning annulleret.");
        }
    }
}