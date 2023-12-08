import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.Scanner;

//Klasse til at håndtere årlig kontingentbetaling
public class AnnualPaymentManagement {
    private static final Scanner scanner = new Scanner(System.in);
    public static ArrayList<AnnualPayment> paymentList = new ArrayList<>();

    // Metode til at oprette regning for første kontingentbetaling (betalingsdato = et år efter dato for oprettelse af svømmeren)
    public static void createInitialPayment(Member member) {
        LocalDate paymentDueDate = LocalDate.now().plusYears(1);
        AnnualPayment newPayment = new AnnualPayment(member, paymentDueDate);
        paymentList.add(newPayment);
    }

    // Metode til at oprette en ny betaling når regning er betalt (Betalingsdato tidligere betalingsdato + 1 år)
    private static void createNewPayment(AnnualPayment payment) {
        LocalDate nextPaymentDate = payment.getPaymentDueDate().plusYears(1);
        AnnualPayment newPayment = new AnnualPayment(payment.getMember(), nextPaymentDate);
        paymentList.add(newPayment);
        System.out.println("Ny betaling oprettet med forfaldsdato: " + nextPaymentDate);
    }

    // Metode til at markere betaling som betalt
    private static void markAsPaid(AnnualPayment payment) {
        payment.setIsPaid(true); // Antag at Payment klassen har en metode setPaid
        System.out.println("Betaling for medlemmet " + payment.getMemberFirstName() + " " + payment.getMemberLastName() + " er markeret som betalt.");
    }

    // Metode til at håndtere betalingsprocessen
    public static void handlePayment() {
        System.out.print("Søg efter betaling (Fornavn, Efternavn eller Medlems ID): ");
        String searchCriteria = scanner.nextLine();
        System.out.println();
        List<AnnualPayment> foundPayments = SearchAndInputMethods.searchPayments(searchCriteria);

        if (foundPayments.isEmpty()) {
            System.out.println("Ingen betalinger fundet.");
            return;
        }

        System.out.println("Betalinger: ");
        AnnualPayment selectedPayment = SearchAndInputMethods.selectPaymentFromList(foundPayments);

        if (selectedPayment == null) {
            System.out.println("Ingen betaling valgt.");
            return;
        }

        System.out.println("Valgt betaling:");
        System.out.println(selectedPayment);
        System.out.println("1. Marker som betalt");
        System.out.println("0. Gå tilbage");

        int action = SearchAndInputMethods.promptForChoice(scanner, "Vælg en handling: ", 0, 1);

        switch (action) {
            case 1:
                markAsPaid(selectedPayment);
                createNewPayment(selectedPayment);
                break;
            case 0:
                break;
            default:
                System.out.println("Ugyldigt valg.");
        }
    }

    // Metode til at beregne kontingentbeløbet
    public static double calculateMembershipFee(Member member) {
        int age = Period.between(member.getBirthDate(), LocalDate.now()).getYears();
        boolean isElderlySenior = age >= 60;
        boolean isActive = member instanceof Swimmer && ((Swimmer) member).getMembershipStatus().equals("Aktiv");

        double amount;
        if (isActive) {
            if (age < 18) {
                amount = 1000; // Juniorsvømmer
            } else {
                amount = isElderlySenior ? 1200 : 1600; // Senior eller ældre senior (rabat for personer >= 60 år)
            }
        } else {
            amount = 500; // Passivt medlemskab
        }
        return amount;
    }

    //Metode til at vise fremtidige betalinger
    public static void displayUpcomingPayments(ArrayList<AnnualPayment> payments) {
        boolean foundUnpaid = false;
        System.out.println("Betalinger: ");

        for (AnnualPayment payment : payments) {
            if (!payment.getIsPaid() && payment.getPaymentDueDate().isAfter(LocalDate.now())) {
                String output = String.format("Member ID: %d, Navn: %s %s, Betalingsdato: %s, Betalingsstatus: %s, Beløb: %.2f",
                        payment.getMemberID(),
                        payment.getMemberFirstName(),
                        payment.getMemberLastName(),
                        payment.getPaymentDueDate(),
                        payment.getIsPaid() ? "Betalt" : "Ikke betalt",
                        payment.getAmount());
                System.out.println(output);
                foundUnpaid = true;
            }
        }

        if (!foundUnpaid) {
            System.out.println("Ingen ubetalte kontingenter.");
        }
    }

    //Metode til at vise betalinger i restance
    public static void displayPaymentsOverdue() {
        boolean foundOverdue = false;

        for (AnnualPayment payment : paymentList) {
            // Tjek om betalingen er forfalden og ikke betalt
            if (!payment.getIsPaid() && payment.getPaymentDueDate().isBefore(LocalDate.now())) {
                String output = String.format("Member ID: %d, Navn: %s %s, Forfalden Betalingsdato: %s, Beløb: %.2f",
                        payment.getMemberID(),
                        payment.getMemberFirstName(),
                        payment.getMemberLastName(),
                        payment.getPaymentDueDate(),
                        payment.getAmount());
                System.out.println(output);
                foundOverdue = true;
            }
        }

        if (!foundOverdue) {
            System.out.println("Ingen betalinger i restance.");
        }
    }
}

