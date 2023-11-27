import java.util.Scanner;

public class Menu {
        private final Scanner scanner = new Scanner(System.in);

        public void displayMainMenu() {
                System.out.println();
                System.out.println("Hovedmenu:");
                System.out.println("1. Medlemsskaber");
                System.out.println("2. Økonomi");
                System.out.println("3. Afslut");
        }

        public void displayMemberMenu() {
                System.out.println();
                System.out.println("Medlemmer:");
                System.out.println("1. Opret svømmer");
                System.out.println("2. Opret træner");
                System.out.println("3. Håndter medlem (Vis/slet/rediger)");
                System.out.println("4. Tilbage til hovedmenu");
        }

        public void displayFinanceMenu() {
                System.out.println();
                System.out.println("Økonomi:");
                System.out.println("1. Kommende betalinger");
                System.out.println("2. Betalinger i restance");
                System.out.println("3. Tilbage til hovedmenu");
        }

        public void runMenu() {
                SampleMember.initializeSampleMembers();
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
                                case "4":
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

                        switch (choice) {
                                case "1":
                                        Payment.displayUpcomingPayments(Payment.paymentList);
                                        break;
                                case "2":
                                        Payment.displayPaymentsOverdue();
                                        break;

                                case "3":
                                        Payment.markAsPaidAndCreateNewPayment();
                                        break;

                                case "4":
                                        financeMenuRunning = false;
                                        break;
                                default:
                                        System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                }
        }
}
