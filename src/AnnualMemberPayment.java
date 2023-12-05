import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;

//Klasse for årligt kontingentbetaling (inkluderer arrayliste med betalinger betalt/ubetalt/restance)
public class AnnualMemberPayment {
    private static final Scanner scanner = new Scanner(System.in);
    public static final ArrayList<AnnualMemberPayment> paymentList = new ArrayList<>();
    private final Member member;
    private LocalDate paymentDueDate;
    private boolean isPaid;
    private double amount;

    //Konstruktør
    public AnnualMemberPayment(Member member, LocalDate paymentDueDate) {
        this.member = member;
        this.paymentDueDate = paymentDueDate;
        this.isPaid = false;
        calculateMembershipFee(member);
    }

    // Getters og setters
    public String getMemberFirstName() {
        return member.getFirstName();
    }

    public String getMemberLastName() {
        return member.getLastName();
    }

    public int getMemberID() {
        return member.getMemberID();
    }

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    public Member getMember() {
        return member;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }


    public double getAmount() {
        return amount;
    }

    // Metode til at oprette regning for første kontingentbetaling (betalingsdato = et år efter dato for oprettelse af svømmeren)
    public static void createInitialPayment(Member member) {
        LocalDate paymentDueDate = LocalDate.now().plusYears(1);
        AnnualMemberPayment newPayment = new AnnualMemberPayment(member, paymentDueDate);
        paymentList.add(newPayment);
    }

    // Metode til at oprette en ny betaling når regning er betalt (Betalingsdato tidligere betalingsdato + 1 år)
    private static void createNewPayment(AnnualMemberPayment payment) {
        LocalDate nextPaymentDate = payment.getPaymentDueDate().plusYears(1);
        AnnualMemberPayment newPayment = new AnnualMemberPayment(payment.getMember(), nextPaymentDate);
        paymentList.add(newPayment);
        System.out.println("Ny betaling oprettet med forfaldsdato: " + nextPaymentDate);
    }

    // Metode til at markere betaling som betalt
    private static void markAsPaid(AnnualMemberPayment payment) {
        payment.setIsPaid(true); // Antag at Payment klassen har en metode setPaid
        System.out.println("Betaling for medlemmet " + payment.getMemberFirstName() + " " + payment.getMemberLastName() + " er markeret som betalt.");
    }

    // Metode til at søge efter payments (søgning efter Navn eller Medlems ID)
    private static List<AnnualMemberPayment> searchPayments(String searchCriteria) {
        List<AnnualMemberPayment> foundPayments = new ArrayList<>();
        for (AnnualMemberPayment payment : paymentList) {
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
    private static AnnualMemberPayment selectPaymentFromList(List<AnnualMemberPayment> payments) {
        if (payments.size() == 1) {
            return payments.get(0);
        }

        for (int i = 0; i < payments.size(); i++) {
            System.out.println((i + 1) + ". " + payments.get(i));
        }

        System.out.print("Vælg en betaling: \n");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > payments.size()) {
            System.out.println("Ugyldigt valg.");
            return null;
        }

        return payments.get(choice - 1);
    }

    // Metode til at håndtere betalingsprocessen
    public static void handlePayment() {
        System.out.print("Søg efter betaling (Fornavn, Efternavn eller Medlems ID): ");
        String searchCriteria = scanner.nextLine();
        System.out.println();
        List<AnnualMemberPayment> foundPayments = searchPayments(searchCriteria);

        if (foundPayments.isEmpty()) {
            System.out.println("Ingen betalinger fundet.");
            return;
        }

        System.out.println("Betalinger: ");
        AnnualMemberPayment selectedPayment = selectPaymentFromList(foundPayments);

        if (selectedPayment == null) {
            System.out.println("Ingen betaling valgt.");
            return;
        }

        System.out.println("Valgt betaling:");
        System.out.println(selectedPayment);
        System.out.println("1. Marker som betalt");
        System.out.println("0. Gå tilbage");

        System.out.print("Vælg en handling: ");
        int action = scanner.nextInt();
        scanner.nextLine();

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

    //To String
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = paymentDueDate.format(formatter);
        String paidStatus = isPaid ? "Betalt" : "Ikke betalt";
        String fullName = getMemberFirstName() + " " + getMemberLastName();
        return "Medlems ID: " + getMemberID() +
                ", Navn: " + fullName +
                ", Betalingsdato: " + formattedDate +
                ", Betalingsstatus: " + paidStatus +
                ", Beløb: " + String.format("%.2f", amount);
    }

    // Metode til at beregne kontingentbeløbet
    public void calculateMembershipFee(Member member) {
        int age = Period.between(member.getBirthDate(), LocalDate.now()).getYears();
        boolean isElderlySenior = age >= 60;
        boolean isActive = member instanceof Swimmer && ((Swimmer) member).getMemberType().equals("Aktiv");

        if (isActive) {
            if (age < 18) {
                amount = 1000; // Juniorsvømmer
            } else {
                amount = isElderlySenior ? 1200 : 1600; // Senior eller ældre senior (rabat for personer >= 60 år)
            }
        } else {
            amount = 500; // Passivt medlemskab
        }
    }

    //Metode til at vise fremtidige betalinger
    public static void displayUpcomingPayments(ArrayList<AnnualMemberPayment> payments) {
        boolean foundUnpaid = false;
        System.out.println("Betalinger: ");

        for (AnnualMemberPayment payment : payments) {
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

        for (AnnualMemberPayment payment : paymentList) {
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

