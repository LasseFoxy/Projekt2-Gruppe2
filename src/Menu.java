import java.util.Scanner;

/*Menuklassen styrer brugergrænsefladen og adgangen til forskellige funktioner
i programmet ved at præsentere menuer og håndtere brugerinput.*/
public class Menu {
        private final Scanner scanner = new Scanner(System.in);

        //Hovedmenu
        public void displayMainMenu() {
                System.out.println();
                System.out.println("Hovedmenu:");
                System.out.println("1. Medlemskaber");
                System.out.println("2. Økonomi");
                System.out.println("3. Svømmer data");
                System.out.println("4. Holdadministration");
                System.out.println("5. Gem og afslut programmet");
                System.out.println("0. Annuller ændringer og afslut programmet");
        }

        //Medlemskaber menu
        public void displayMemberMenu() {
                System.out.println();
                System.out.println("Medlemskaber:");
                System.out.println("1. Opret svømmer");
                System.out.println("2. Opret træner");
                System.out.println("3. Håndter medlem (Vis/slet/rediger)");
                System.out.println("0. Tilbage til hovedmenu");
        }

        //Økonomi menu
        public void displayFinanceMenu() {
                System.out.println();
                System.out.println("Økonomi:");
                System.out.println("1. Kommende betalinger");
                System.out.println("2. Betalinger i restance");
                System.out.println("3. Søg efter kontingentbetalinger/Marker som betalt");
                System.out.println("0. Tilbage til hovedmenu");

        //Svømmer data menu
        } public void displaySwimmerDataMenu() {
                System.out.println();
                System.out.println("Svømmer data:");
                System.out.println("1. Tilføj ny bedste træningstid");
                System.out.println("2. Tilføj konkurrence resultater");
                System.out.println("3. Vis konkurrenceresultater");
                System.out.println("4. Vis bedste tider for svømmer (Træning og Konkurrence)");
                System.out.println("5. Vis top 5 svømmere efter disciplin");
                System.out.println("0. Tilbage til hovedmenu");
        }

        //Holdadministration menu
        public void displayTeamManagementMenu() {
                System.out.println();
                System.out.println("Holdadministration:");
                System.out.println("1. Tilmeld/Fjern medlem fra hold");
                System.out.println("2. Se svømmehold");
                System.out.println("0. Tilbage til hovedmenu");
        }

        //Metode der starter menuen
        public void runMenu() {
                //Kalder en metode der genererer testmedlemmer og bedste tider, hvis de ikke allerede eksisterer
                if (MemberManagement.membersList == null || MemberManagement.membersList.isEmpty()) {
                        TestMembers.initializeTestMembers(); // Opretter Testmedlemmer
                }

                if (BestTimeManagement.swimmerTimes == null || BestTimeManagement.swimmerTimes.isEmpty()) {
                        TestMembers.generateTimesForCompetitiveSwimmers(); // Generer "Bedste tider" for konkurrencesvømmere
                }

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

                                case "4":
                                        runTeamManagementMenu();
                                        break;
                                case "5":
                                        DataManager.saveAllData();
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
                                        MemberManagement.handleMemberInfo();
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
                                        AnnualPaymentManagement.displayUpcomingPayments(AnnualPaymentManagement.paymentList);
                                        break;
                                case "2":
                                        AnnualPaymentManagement.displayPaymentsOverdue();
                                        break;

                                case "3":
                                        AnnualPaymentManagement.handlePayment();
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
                                        BestTimeManagement.handleTrainingTime();
                                        break;
                                case "2":
                                        CompetitionEventManagement.handleCompetitionEvent();
                                        break;
                                case "3":
                                        CompetitionEventManagement.searchAnDisplayCompetitionResults();
                                        break;

                                case "4":
                                        BestTimeManagement.displayBestTimes();
                                        break;

                                case "5":
                                        BestTimeManagement.displayTop5Swimmers();
                                        break;

                                case "0":
                                        swimmerDataMenuRunning = false;
                                        break;
                                default:
                                        System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                }
        }

        private void runTeamManagementMenu() {
                boolean teamManagementMenuRunning = true;
                TrainingTeamManagement.CreateTeams();

                while (teamManagementMenuRunning) {
                        displayTeamManagementMenu();
                        System.out.print("Vælg en underkategori: ");
                        String choice = scanner.nextLine();
                        System.out.println();

                        switch (choice) {
                                case "1":
                                        TrainingTeamManagement.handleMemberTeamAssignment();
                                        break;
                                case "2":
                                        TrainingTeamManagement.showTeamsByType();
                                        break;
                                case "0":
                                        teamManagementMenuRunning = false;
                                        break;
                                default:
                                        System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                }
        }
}