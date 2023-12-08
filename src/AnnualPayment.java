import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

/*AnnualPayment klassen repræsenterer medlemskontingentbetalinger og indeholder oplysninger om medlemmet,
  forfaldsdato, betalingsstatus og beløb.*/
public class AnnualPayment implements Serializable {
    private final Member member;
    private LocalDate paymentDueDate;
    private boolean isPaid;
    private final double amount;

    //Konstruktør
    public AnnualPayment(Member member, LocalDate paymentDueDate) {
        this.member = member;
        this.paymentDueDate = paymentDueDate;
        this.isPaid = false;
        this.amount = AnnualPaymentManagement.calculateMembershipFee(member);
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

    //To String metode
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
}

