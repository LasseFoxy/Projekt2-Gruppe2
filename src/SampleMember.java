import java.time.LocalDate;

public class SampleMember {

    public static void initializeSampleMembers() {
        // Tilføj 10 svømmere
        MemberManagement.membersList.add(new Swimmer("Lars", "Jensen", LocalDate.of(2005, 6, 15), "12345678", "lars@example.com", 1, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Sofia", "Larsen", LocalDate.of(2003, 4, 20), "23456789", "sofia@example.com", 2, "Aktiv", "Fritidssvømmer"));
        MemberManagement.membersList.add(new Swimmer("Mads", "Nielsen", LocalDate.of(2004, 5, 22), "34567890", "mads@example.com", 3, "Passiv", "Fritidssvømmer"));
        MemberManagement.membersList.add(new Swimmer("Emma", "Jensen", LocalDate.of(2006, 7, 18), "45678901", "emma@example.com", 4, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Frederik", "Hansen", LocalDate.of(2002, 3, 14), "56789012", "frederik@example.com", 5, "Passiv", "Fritidssvømmer"));
        MemberManagement.membersList.add(new Swimmer("Ida", "Christensen", LocalDate.of(2007, 8, 19), "67890123", "ida@example.com", 6, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Anders", "Larsen", LocalDate.of(2001, 2, 10), "78901234", "anders@example.com", 7, "Aktiv", "Fritidssvømmer"));
        MemberManagement.membersList.add(new Swimmer("Mette", "Nielsen", LocalDate.of(2000, 1, 5), "89012345", "mette@example.com", 8, "Passiv", "Fritidssvømmer"));
        MemberManagement.membersList.add(new Swimmer("Peter", "Jensen", LocalDate.of(1999, 12, 30), "90123456", "peter@example.com", 9, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Anne", "Hansen", LocalDate.of(1998, 11, 25), "01234567", "anne@example.com", 10, "Passiv", "Fritidssvømmer"));

        // Tilføj 10 trænere
        MemberManagement.membersList.add(new Trainer("Thomas", "Andersen", LocalDate.of(1980, 1, 5), "11223344", "thomas@example.com", 11, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Maria", "Christensen", LocalDate.of(1985, 3, 10), "22334455", "maria@example.com", 12, "Junior Træner"));
        MemberManagement.membersList.add(new Trainer("Christian", "Petersen", LocalDate.of(1975, 5, 15), "33445566", "christian@example.com", 13, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Louise", "Johansen", LocalDate.of(1982, 7, 20), "44556677", "louise@example.com", 14, "Junior Træner"));
        MemberManagement.membersList.add(new Trainer("Jesper", "Olsen", LocalDate.of(1978, 9, 25), "55667788", "jesper@example.com", 15, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Camilla", "Mortensen", LocalDate.of(1983, 11, 30), "66778899", "camilla@example.com", 16, "Junior Træner"));
        MemberManagement.membersList.add(new Trainer("Mikkel", "Larsen", LocalDate.of(1979, 2, 4), "77889900", "mikkel@example.com", 17, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Sara", "Knudsen", LocalDate.of(1981, 4, 9), "88990011", "sara@example.com", 18, "Junior Træner"));
        MemberManagement.membersList.add(new Trainer("Martin", "Sørensen", LocalDate.of(1984, 6, 14), "99001122", "martin@example.com", 19, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Julie", "Rasmussen", LocalDate.of(1986, 8, 19), "00112233", "julie@example.com", 20, "Junior Træner"));

        for (Member member : MemberManagement.membersList) {
            if (member instanceof Swimmer) {
                LocalDate paymentDueDate = LocalDate.now().plusYears(1);
                Payment payment = new Payment(member, paymentDueDate);
                Payment.paymentList.add(payment);
            }
        }

        Payment.paymentList.get(0).setPaymentDueDate(LocalDate.now().minusMonths(1));
        Payment.paymentList.get(1).setPaymentDueDate(LocalDate.now().minusDays(10));
        Payment.paymentList.get(2).setPaymentDueDate(LocalDate.now().minusDays(20));
        Payment.paymentList.get(3).setPaymentDueDate(LocalDate.now().plusDays(20));
        Payment.paymentList.get(4).setPaymentDueDate(LocalDate.now().plusDays(4));
    }
}

