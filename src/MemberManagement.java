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

        // Metode til at oprette en svømmer
        public static void createSwimmer() {
            Member basicMemberInfo = gatherBasicMemberInfo();

            int memberTypeChoice;
            String memberType;
            do {
                System.out.print("Vælg medlemstype: 1 for Aktiv, 2 for Passiv: ");
                memberTypeChoice = scanner.nextInt();
                if (memberTypeChoice != 1 && memberTypeChoice != 2) {
                    System.out.println("Ugyldigt valg. Prøv igen.");
                }
            } while (memberTypeChoice != 1 && memberTypeChoice != 2);
            memberType = memberTypeChoice == 1 ? "Aktiv" : "Passiv";
            scanner.nextLine();

            int activityTypeChoice;
            String activityType;
            do {
                System.out.print("Vælg aktivitetstype: 1 for Konkurrencesvømmer, 2 for Fritidssvømmer: ");
                activityTypeChoice = scanner.nextInt();
                if (activityTypeChoice != 1 && activityTypeChoice != 2) {
                    System.out.println("Ugyldigt valg. Prøv igen.");
                }
            } while (activityTypeChoice != 1 && activityTypeChoice != 2);
            activityType = activityTypeChoice == 1 ? "Konkurrencesvømmer" : "Fritidssvømmer";
            scanner.nextLine();

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
            System.out.println(trainer.getShortInfo()+" - oprettet som træner");
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
            List<Member> foundMembers = SearchMethods.searchAllMembers(searchCriteria);
            Member selectedMember = SearchMethods.selectMemberFromList(foundMembers);
            System.out.println();

            if (selectedMember == null) {
                System.out.println("Ingen medlemmer fundet");
                System.out.println("1. Søg igen");
                System.out.println("0. Gå tilbage");
                System.out.print("Indtast valg: ");
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

                System.out.print("Indtast valg: ");
                int action = scanner.nextInt();
                scanner.nextLine();

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
                        ((Swimmer) member).setMembershipStatus(scanner.nextLine());
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

    private static void deleteMember(Member member) {
        System.out.println("Er du sikker på, at du vil slette følgende medlem?");
        System.out.println(member);
        System.out.println("1. Ja, slet medlem");
        System.out.println("2. Nej, gå tilbage");
        System.out.print("Indtast valg: ");
        int confirmation = scanner.nextInt();
        scanner.nextLine();

        if (confirmation == 1) {
            membersList.remove(member);
            System.out.println("Medlem slettet.");
        } else {
            System.out.println("Sletning annulleret.");
        }
    }
}