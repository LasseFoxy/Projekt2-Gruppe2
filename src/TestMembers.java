import java.time.LocalDate;
import java.util.Random;

// Denne klasse har til formål at teste de forskellige funktioner i programmet
public class TestMembers {

    public static void initializeTestMembers() {
        // Her er tilføjet 10 svømmere som kan testes
        MemberManagement.membersList.add(new Swimmer("Lars", "Jensen", LocalDate.of(2001, 6, 15), "12345678", "lars@example.com", 1, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Sofia", "Larsen", LocalDate.of(2003, 4, 20), "23456789", "sofia@example.com", 2, "Aktiv", "Fritidssvømmer"));
        MemberManagement.membersList.add(new Swimmer("Mads", "Nielsen", LocalDate.of(2004, 5, 22), "34567890", "mads@example.com", 3, "Passiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Emma", "Jensen", LocalDate.of(2002, 7, 18), "45678901", "emma@example.com", 4, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Frederik", "Hansen", LocalDate.of(2002, 3, 14), "56789012", "frederik@example.com", 5, "Passiv", "Fritidssvømmer"));
        MemberManagement.membersList.add(new Swimmer("Ida", "Christensen", LocalDate.of(1970, 8, 19), "67890123", "ida@example.com", 6, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Anders", "Larsen", LocalDate.of(2001, 2, 10), "78901234", "anders@example.com", 7, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Mette", "Nielsen", LocalDate.of(2000, 1, 5), "89012345", "mette@example.com", 8, "Passiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Peter", "Jensen", LocalDate.of(1960, 12, 30), "90123456", "peter@example.com", 9, "Aktiv", "Konkurrencesvømmer"));
        MemberManagement.membersList.add(new Swimmer("Anne", "Hansen", LocalDate.of(1998, 11, 25), "01234567", "anne@example.com", 10, "Passiv", "Fritidssvømmer"));

        // Her er tilføjet 10 trænere som kan testes
        MemberManagement.membersList.add(new Trainer("Thomas", "Andersen", LocalDate.of(1980, 1, 5), "11223344", "thomas@example.com", 11, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Maria", "Christensen", LocalDate.of(1985, 3, 10), "22334455", "maria@example.com", 12, "Junior Træner"));
        MemberManagement.membersList.add(new Trainer("Christian", "Petersen", LocalDate.of(1945, 5, 15), "33445566", "christian@example.com", 13, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Louise", "Johansen", LocalDate.of(1982, 7, 20), "44556677", "louise@example.com", 14, "Junior Træner"));
        MemberManagement.membersList.add(new Trainer("Jesper", "Olsen", LocalDate.of(1978, 9, 25), "55667788", "jesper@example.com", 15, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Camilla", "Mortensen", LocalDate.of(1983, 11, 30), "66778899", "camilla@example.com", 16, "Junior Træner"));
        MemberManagement.membersList.add(new Trainer("Mikkel", "Larsen", LocalDate.of(1979, 2, 4), "77889900", "mikkel@example.com", 17, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Sara", "Knudsen", LocalDate.of(1981, 4, 9), "88990011", "sara@example.com", 18, "Junior Træner"));
        MemberManagement.membersList.add(new Trainer("Martin", "Sørensen", LocalDate.of(1984, 6, 14), "99001122", "martin@example.com", 19, "Senior Træner"));
        MemberManagement.membersList.add(new Trainer("Julie", "Rasmussen", LocalDate.of(1986, 8, 19), "00112233", "julie@example.com", 20, "Junior Træner"));

        //En for-løkke der genererer en betaling for alle svømmere et år ude i fremtiden (Til Test)
        for (Member member : MemberManagement.membersList) {
            if (member instanceof Swimmer) {
                LocalDate paymentDueDate = LocalDate.now().plusYears(1);
                AnnualPayment payment = new AnnualPayment(member, paymentDueDate);
                AnnualPaymentManagement.paymentList.add(payment);
            }
        }

        //Ændrer nogle af betalingerne i arraylisten til at være overskredet (restance) eller tæt på nuværende dato.
        AnnualPaymentManagement.paymentList.get(0).setPaymentDueDate(LocalDate.now().minusMonths(1));
        AnnualPaymentManagement.paymentList.get(1).setPaymentDueDate(LocalDate.now().minusDays(10));
        AnnualPaymentManagement.paymentList.get(2).setPaymentDueDate(LocalDate.now().minusDays(20));
        AnnualPaymentManagement.paymentList.get(3).setPaymentDueDate(LocalDate.now().plusDays(20));
        AnnualPaymentManagement.paymentList.get(4).setPaymentDueDate(LocalDate.now().plusDays(4));
    }

    //Metode der genererer tider for konkurrencesvømmere
    public static void generateTimesForCompetitiveSwimmers() {
        Random random = new Random();
        String[] disciplines = {"Crawl", "Rygsvømning", "Brystsvømning", "Butterfly"};
        BestTime.TimeType[] timeTypes = {BestTime.TimeType.TRAINING, BestTime.TimeType.COMPETITION};

        for (Member member : MemberManagement.membersList) {
            if (member instanceof Swimmer swimmer && ((Swimmer) member).getActivityType().equals("Konkurrencesvømmer")) {

                for (String discipline : disciplines) {
                    for (BestTime.TimeType timeType : timeTypes) {
                        int minutes = random.nextInt(2); // Random minut fra 0-1
                        int seconds = random.nextInt(60); // Random sekund fra 0 til 59
                        int hundredths = random.nextInt(100); // Random hundrededele fra 0 til 99

                        String time = String.format("%02d:%02d:%02d", minutes, seconds, hundredths);

                        boolean isNewPersonalBest = BestTimeManagement.isNewPersonalBest(
                                swimmer.getFirstName(),
                                swimmer.getLastName(),
                                swimmer.getMemberID(),
                                discipline,
                                time,
                                BestTime.TimeType.COMPETITION
                        );

                        if (timeType == BestTime.TimeType.COMPETITION && isNewPersonalBest) {
                            BestTime timeRecord = new BestTime(
                                    timeType,
                                    discipline,
                                    LocalDate.now(),
                                    time,
                                    swimmer.getFirstName(),
                                    swimmer.getLastName(),
                                    swimmer.getMemberID()
                            );
                            BestTimeManagement.swimmerTimes.add(timeRecord);
                        } else if (timeType == BestTime.TimeType.TRAINING) {

                            BestTime timeRecord = new BestTime(
                                    timeType,
                                    discipline,
                                    LocalDate.now(),
                                    time,
                                    swimmer.getFirstName(),
                                    swimmer.getLastName(),
                                    swimmer.getMemberID()
                            );
                            BestTimeManagement.swimmerTimes.add(timeRecord);
                        }
                    }
                }
            }
        }
    }
}