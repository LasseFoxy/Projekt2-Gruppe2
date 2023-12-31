import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SearchAndInputMethods {
    private static final Scanner scanner = new Scanner(System.in);

    //Hovedmetode for at søge efter medlemmer med søgekriterier (Se booleans argumenter)
    static List<Member> searchMembers(String searchCriteria, boolean includeSwimmers, boolean includeTrainers, boolean includeCompetitiveSwimmers, boolean searchAllFields) {
        List<Member> foundMembers = new ArrayList<>();
        for (Member member : MemberManagement.membersList) {
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


    //Metode til at søge i alle medlemmer (alle kriterier/argumenter true)
    static List<Member> searchAllMembers(String searchCriteria) {
        return searchMembers(searchCriteria, true, true, true, true);
    }

    //Metode til at søge efter kun konkurrencesvømmere (kun inkludering af konkurrencesvømmere er true)
    public static List<Member> searchOnlyCompetitionSwimmers(String searchCriteria){
        return searchMembers(searchCriteria, false, false, true, false);
    }

    //Metode til at søge efter konkurrencesvømmere og trænere (bruges til hold)
    public static List<Member> searchCompetitionSwimmersAndTrainers(String searchCriteria){
        return searchMembers(searchCriteria, false,true,true,false);
    }

    //Metode til at vælge medlem fra liste
    public static Member selectMemberFromList(List<Member> members) {
        if (members.isEmpty()) {
            return null;
        }

        System.out.println("\nMedlemmer: ");
        for (int i = 0; i < members.size(); i++) {
            System.out.println((i + 1) + ". " + members.get(i).getShortInfo());
        }

        // Genererer et array af gyldige valgmuligheder baseret på medlemslistens størrelse plus en for "gå tilbage" muligheden
        int[] validChoices = new int[members.size() + 1];
        for (int i = 0; i <= members.size(); i++) {
            validChoices[i] = i;
        }

        int choice = promptForChoice(scanner, "Vælg et nummer eller tryk 0 for at gå tilbage: ", validChoices);

        if (choice == 0) {
            System.out.println("Handling afbrudt.");
            return null;
        }

        return members.get(choice - 1);
    }

    // Metode til at søge efter payments (søgning efter Navn eller Medlems ID)
    static List<AnnualPayment> searchPayments(String searchCriteria) {
        List<AnnualPayment> foundPayments = new ArrayList<>();
        for (AnnualPayment payment : AnnualPaymentManagement.paymentList) {
            boolean matchesCriteria = payment.getMemberFirstName().equalsIgnoreCase(searchCriteria) ||
                    payment.getMemberLastName().equalsIgnoreCase(searchCriteria) ||
                    Integer.toString(payment.getMemberID()).equalsIgnoreCase(searchCriteria);

            if (matchesCriteria) {
                foundPayments.add(payment);
            }
        }
        return foundPayments;
    }

    // Metode til at vælge betaling fra den fremviste søgeliste
    static AnnualPayment selectPaymentFromList(List<AnnualPayment> payments) {
        if (payments.size() == 1) {
            return payments.get(0);
        }

        for (int i = 0; i < payments.size(); i++) {
            System.out.println((i + 1) + ". " + payments.get(i));
        }

        int[] validPaymentChoices = new int[payments.size()];
        for (int i = 0; i < payments.size(); i++) {
            validPaymentChoices[i] = i + 1;
        }
        int choice = promptForChoice(scanner, "Vælg en betaling: \n", validPaymentChoices);


        if (choice < 1 || choice > payments.size()) {
            System.out.println("Ugyldigt valg.");
            return null;
        }

        return payments.get(choice - 1);
    }

    //Metode der indhenter et gyldigt valg fra brugeren
    public static int promptForChoice(Scanner scanner, String prompt, int... validChoices) {
        int choice;
        boolean isValidChoice;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Det skal være et gyldigt tal. Prøv igen.");
                System.out.print(prompt);
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            isValidChoice = false;
            for (int validChoice : validChoices) {
                if (choice == validChoice) {
                    isValidChoice = true;
                    break;
                }
            }

            if (!isValidChoice) {
                System.out.println("Ugyldigt valg. Prøv igen.");
            }
        } while (!isValidChoice);
        return choice;
    }

    //Metode der indhenter en gyldig dato fra brugeren
    public static LocalDate promptForDate(Scanner scanner, String prompt) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            String dateString = scanner.nextLine();
            try {
                date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig indtastning, prøv igen.");
            }
        }
        return date;
    }

    //Metode der indhenter en gyldig tid i formatet MM:ss:hh fra brugeren
    public static String promptForTime(Scanner scanner, String prompt) {
        String time;
        do {
            System.out.print(prompt);
            time = scanner.nextLine();

            // Validerer om tiden matcher formatet MM:ss:hh
            if (!time.matches("^([0-5][0-9]):([0-5][0-9]):([0-9][0-9])$")) {
                System.out.println("Ugyldigt tidsformat. Prøv igen (MM:ss:hh): ");
                time = null; // Nulstiller time for at fortsætte løkken
            }
        } while (time == null);

        return time;
    }

    //Metode der indhenter korrekt tlf nummer (8 cifre) fra brugeren.
    public static String promptForPhoneNumber(Scanner scanner) {
        String phoneNumber = null;
        while (phoneNumber == null) {
            System.out.print("Indtast telefonnummer: ");
            phoneNumber = scanner.nextLine();
            if (!phoneNumber.matches("\\d{8}")) {
                System.out.println("Forkert telefonnummer. Indtast 8 cifret telefonnummer.");
                phoneNumber = null;
            }
        }
        return phoneNumber;
    }
}
