import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;


public class Payment {
    private static final Scanner scanner = new Scanner(System.in);
    public static final ArrayList<Payment> paymentList = new ArrayList<>();
    private Member member;
    private LocalDate paymentDueDate;
    private boolean isPaid;
    private double amount;

    public Payment(Member member, LocalDate paymentDueDate) {
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

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public double getAmount() {
        return amount;
    }

    // Metode til at oprette regning for første kontingentbetaling (betalingsdato = et år efter oprettelse af svømmeren)
    public static void createInitialPayment(Member member) {
        LocalDate paymentDueDate = LocalDate.now().plusYears(1);
        Payment newPayment = new Payment(member, paymentDueDate);
        paymentList.add(newPayment);
    }

    // Metode til at markere kontingent som betalt og skabe ny betaling (ny betalingsdato = betalingsdato + 1 år)
    public static void markAsPaidAndCreateNewPayment() {
        System.out.print("Søg efter medlem (fornavn, efternavn, telefonnummer, medlemsID, e-mail): ");
        String searchCriteria = scanner.nextLine();
        List<Member> foundMembers = MemberManagement.searchOnlySwimmers(searchCriteria);
        Member selectedMember = MemberManagement.selectMemberFromList(foundMembers);

        System.out.println("Valgt medlem:");
        System.out.println(selectedMember);
        System.out.println("1. Marker som betalt");
        System.out.println("2. Gå tilbage");

        System.out.print("Vælg en handling: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                // Her skal du implementere logikken for at markere betalingen som betalt og oprette en ny betaling.
                break;
            case 2:
                break;
            default:
                System.out.println("Ugyldigt valg.");
        }
    }



    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = paymentDueDate.format(formatter);
        return "Payment{" +
                "memberFirstName= " + getMemberFirstName() +'\'' +
                "memberLastName" + getMemberLastName() +
                ", memberID=" + getMemberID() +
                ", paymentDueDate=" + formattedDate +
                ", isPaid=" + isPaid +
                ", amount=" + amount +
                '}';
    }

    // Metode til at beregne kontingentbeløbet
    public void calculateMembershipFee(Member member) {
        int age = Period.between(member.getBirthDate(), LocalDate.now()).getYears();
        boolean isSenior = age >= 60;
        boolean isActive = member instanceof Swimmer && ((Swimmer) member).getMemberType().equals("Aktiv");

        if (isActive) {
            if (age < 18) {
                amount = 1000; // Ungdomssvømmer
            } else {
                amount = isSenior ? 1200 : 1600; // Senior med rabat eller uden rabat
            }
        } else {
            amount = 500; // Passivt medlemskab
        }
    }

    public static void displayUpcomingPayments(ArrayList<Payment> payments) {
        boolean foundUnpaid = false;

        for (Payment payment : payments) {
            if (!payment.isPaid() && payment.getPaymentDueDate().isAfter(LocalDate.now())) {
                String output = String.format("Member ID: %d, Navn: %s %s, Betalingsdato: %s, Betalingsstatus: %s, Beløb: %.2f",
                        payment.getMemberID(),
                        payment.getMemberFirstName(),
                        payment.getMemberLastName(),
                        payment.getPaymentDueDate(),
                        payment.isPaid() ? "Betalt" : "Ikke betalt",
                        payment.getAmount());
                System.out.println(output);
                foundUnpaid = true;
            }
        }

        if (!foundUnpaid) {
            System.out.println("Ingen ubetalte kontingenter.");
        }
    }

    public static void displayPaymentsOverdue() {
        boolean foundOverdue = false;

        for (Payment payment : paymentList) {
            // Tjek om betalingen er forfalden og ikke betalt
            if (!payment.isPaid() && payment.getPaymentDueDate().isBefore(LocalDate.now())) {
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

