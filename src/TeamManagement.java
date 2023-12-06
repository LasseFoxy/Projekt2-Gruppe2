import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeamManagement {
    private static final ArrayList<Team> holdListe = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void CreateTeams() {
        holdListe.add(new Team("Junior", 1));
        holdListe.add(new Team("Junior", 2));
        holdListe.add(new Team("Junior", 3));
        holdListe.add(new Team("Senior", 1));
        holdListe.add(new Team("Senior", 2));
        holdListe.add(new Team("Senior", 3));
        holdListe.add(new Team("Motion", 1));
    }


    public static void handleMemberTeamAssignment() {
        System.out.print("Søg efter Konkurrencesvømmer eller Træner (Fornavn, Efternavn eller Medlems ID): ");
        String searchCriteria = scanner.nextLine();
        List<Member> foundMembers = MemberManagement.searchCompetitionSwimmersAndTrainers(searchCriteria);

        Member selectedMember = MemberManagement.selectMemberFromList(foundMembers);
        if (selectedMember == null) {
            System.out.println("Ingen konkurrencesvømmere eller trænere fundet.");
            return;
        }

        // Tjek for Træner/Konkurrencesvømmer og Junior/Senior
        String memberRole = selectedMember instanceof Trainer ? "Træner" : "Konkurrencesvømmer";
        String ageCategory = selectedMember.getAgeCategory();

        System.out.println("\nValgt medlem:");
        System.out.println(selectedMember.getShortInfo() + " Rolle: " + memberRole + ", Alderskategori: " + ageCategory);

        System.out.println("1. Tilføj til hold");
        System.out.println("2. Fjern fra hold");
        System.out.print("Indtast valg: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                addMemberToTeam(selectedMember);
                break;
            case 2:
                removeMemberFromTeam(selectedMember);
                break;
            default:
                System.out.println("Ugyldigt valg.");
                break;
        }
    }

    private static void addMemberToTeam(Member member) {
        // Vis liste over hold og tillad brugeren at vælge et hold
        System.out.println("Vælg et hold at tilføje medlemmet til:");
        for (int i = 0; i < holdListe.size(); i++) {
            System.out.println((i + 1) + ". " + holdListe.get(i));
        }
        System.out.print("Indtast valg: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= holdListe.size()) {
            Team selectedTeam = holdListe.get(choice - 1);
            selectedTeam.addMedlem(member);
            System.out.println("\n"+member.getShortInfo() + " tilføjet til holdet: " + selectedTeam.getHoldNavn() + " " + selectedTeam.getHoldNummer());
        } else {
            System.out.println("Ugyldigt valg.");
        }
    }

    private static void removeMemberFromTeam(Member member) {
        System.out.println("\nVælg et hold som medlemmet skal fjernes fra: ");
        List<Team> medlemsHold = new ArrayList<>();
        for (Team team : holdListe) {
            if (team.getMedlemmer().contains(member)) {
                medlemsHold.add(team);
                System.out.println((medlemsHold.size()) + ". " + team.getHoldNavn() + " " + team.getHoldNummer());
            }
        }

        if (medlemsHold.isEmpty()) {
            System.out.println("Medlemmet er ikke på nogen hold.");
            return;
        }

        System.out.print("Indtast valg: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Ryd bufferen efter int-input

        if (choice > 0 && choice <= medlemsHold.size()) {
            Team selectedTeam = medlemsHold.get(choice - 1);
            selectedTeam.removeMedlem(member);
            System.out.println(member.getShortInfo() + " fjernet fra holdet: " + selectedTeam.getHoldNavn());
        } else {
            System.out.println("Ugyldigt valg.");
        }
    }

    public static void showTeamsByType() {
        System.out.println("Vælg holdtype at vise:");
        System.out.println("1. Vis juniorhold");
        System.out.println("2. Vis seniorhold");
        System.out.println("3. Vis motionshold");
        System.out.print("Indtast valg: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        switch (choice) {
            case 1:
                showTeams("Junior");
                break;
            case 2:
                showTeams("Senior");
                break;
            case 3:
                showTeams("Motionshold");
                break;
            default:
                System.out.println("Ugyldigt valg.");
                break;
        }
    }

    private static void showTeams(String teamType) {
        for (Team team : holdListe) {
            if (team.getHoldNavn().equalsIgnoreCase(teamType)) {
                System.out.println(team);
            }
        }
    }
}