import java.util.Scanner;

public class Menu {
        private final Scanner scanner = new Scanner(System.in);

        public void displayMainMenu() {
                System.out.println();
                System.out.println("Hovedmenu:");
                System.out.println("1. Medlemsskaber");
                System.out.println("2. Økonomi");
                System.out.println("3. Svømmer data");
                System.out.println("0. Afslut");
        }

        public void displayMemberMenu() {
                System.out.println();
                System.out.println("Medlemmer:");
                System.out.println("1. Opret svømmer");
                System.out.println("2. Opret træner");
                System.out.println("3. Håndter medlem (Vis/slet/rediger)");
                System.out.println("0. Tilbage til hovedmenu");
        }

        public void displayFinanceMenu() {
                System.out.println();
                System.out.println("Økonomi:");
                System.out.println("1. Kommende betalinger");
                System.out.println("2. Betalinger i restance");
                System.out.println("3. Marker kontingentbetaling som betalt");
                System.out.println("0. Tilbage til hovedmenu");

        } public void displaySwimmerDataMenu() {
                System.out.println();
                System.out.println("Svømmer data:");
                System.out.println("1. Tilføj ny bedste træningstid");
                System.out.println("2. Tilføj konkurrence resultater");
                System.out.println("3. Vis bedste tider (Træning og Konkurrence)");
                System.out.println("4. Vis top 5 svømmere efter disciplin");
                System.out.println("0. Tilbage til hovedmenu");
        }



        public void runMenu() {
                TestMembers.initializeTestMembers(); //OPRETTER TESTMEDLEMMER
                TestMembers.generateTimesForCompetitiveSwimmers(); //Generer TESTTIDER FOR KONKURRENCESVØMMERE
                boolean running = true;

                while (running) {
                        displayMainMenu();
                        System.out.print("Vælg en hovedkategori: ");
                        String input = scanner.nextLine();

                        switch (input) {
                                case "1":
                                        runMemberMenu();
                                        break;
                                case "2":
                                        runFinanceMenu();
                                        break;

                                case "3":
                                        runSwimmerDataMenu();
                                        break;
                                case "0":
                                        running = false;
                                        break;
                                default:
                                        System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                }
        }

        private void runMemberMenu() {
                boolean memberMenuRunning = true;

                while (memberMenuRunning) {
                        displayMemberMenu();
                        System.out.print("Vælg en underkategori: ");
                        String choice = scanner.nextLine();
                        System.out.println();

                        switch (choice) {
                                case "1":
                                        MemberManagement.createSwimmer();
                                        break;
                                case "2":
                                        MemberManagement.createTrainer();
                                        break;
                                case "3":
                                        MemberManagement.handleMember();
                                        break;
                                case "0":
                                        memberMenuRunning = false;
                                        break;
                                default:
                                        System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                }
        }

        private void runFinanceMenu() {
                boolean financeMenuRunning = true;

                while (financeMenuRunning) {
                        displayFinanceMenu();
                        System.out.print("Vælg en underkategori: ");
                        String choice = scanner.nextLine();
                        System.out.println();

                        switch (choice) {
                                case "1":
                                        AnnualMemberPayment.displayUpcomingPayments(AnnualMemberPayment.paymentList);
                                        break;
                                case "2":
                                        AnnualMemberPayment.displayPaymentsOverdue();
                                        break;

                                case "3":
                                        AnnualMemberPayment.handlePayment();
                                        break;

                                case "0":
                                        financeMenuRunning = false;
                                        break;
                                default:
                                        System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                }
        }

        private void runSwimmerDataMenu() {
                boolean swimmerDataMenuRunning = true;

                while (swimmerDataMenuRunning) {
                        displaySwimmerDataMenu();
                        System.out.print("Vælg en underkategori: ");
                        String choice = scanner.nextLine();
                        System.out.println();

                        switch (choice) {
                                case "1":
                                        BestTimeDataManagement.handleTrainingTime();
                                        break;
                                case "2":
                                        //Opret metode for indtast konkurrenceresultater
                                        BestTimeDataManagement.addCompetitionResult();
                                        break;

                                case "3":
                                        //Metode til at vise bedste tider i både træning og konkurrence
                                        BestTimeDataManagement.displayBestTimes();
                                        break;

                                case "4":
                                        BestTimeDataManagement.displayTop5Swimmers();
                                        break;

                                case "0":
                                        swimmerDataMenuRunning = false;
                                        break;
                                default:
                                        System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                }
        }
}
