import java.util.Scanner;

public class Menu {
        private Scanner scanner = new Scanner(System.in);
        private MemberManagement memberManagement = new MemberManagement(); // Tilføjer MemberManagement

        public void displayMainMenu() {
                System.out.println();
                System.out.println("Hovedmenu:");
                System.out.println("1. Medlemsskaber");
                System.out.println("2. Afslut");
        }

        public void displayMemberMenu() {
                System.out.println();
                System.out.println("Medlemmer:");
                System.out.println("1. Opret svømmer");
                System.out.println("2. Opret træner");
                System.out.println("3. Håndter medlem (Vis/slet/rediger)");
                System.out.println("4. Tilbage til hovedmenu");
        }

        public void runMenu() {
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
                                        memberManagement.createSwimmer();
                                        break;
                                case "2":
                                        memberManagement.createTrainer();
                                        break;
                                case "3":
                                        memberManagement.handleMember();
                                        break;
                                case "4":
                                        //displayMember();
                                        break;
                                case "5":
                                        //deleteMember();
                                        break;
                                case "6":
                                        memberMenuRunning = false;
                                        break;
                                default:
                                        System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                }
        }
}
