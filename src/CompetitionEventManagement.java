import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

//Klasse med metoder der vedrører konkurrence events
public class CompetitionEventManagement {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<CompetitionEvent> competitionResults = new ArrayList<>();

    public static void handleCompetitionEvent() {
        System.out.print("Indtast stævnenavn: ");
        String meetName = scanner.nextLine();
        System.out.print("Indtast stævnedato (dd.MM.yyyy): ");
        LocalDate meetDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        boolean addingResults = true;
        while (addingResults) {
            System.out.print("Søg efter konkurrencesvømmer (Fornavn, Efternavn eller Medlems ID): ");
            String searchCriteria = scanner.nextLine();
            List<Member> foundMembers = SearchMethods.searchOnlyCompetitionSwimmers(searchCriteria);
            Member selectedSwimmer = SearchMethods.selectMemberFromList(foundMembers);

            if (selectedSwimmer != null) {
                addSwimmerResults(meetName, meetDate, selectedSwimmer);
            }

            System.out.println("\n1. Tilføj resultater for andre svømmere til dette stævne");
            System.out.println("2. Færdig med at tilføje resultater for dette stævne");
            System.out.print("Indtast valg: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            addingResults = (choice == 1);
        }

        displayThisCompetitionResults(meetName, meetDate);
        System.out.println("Tryk på enter for at afslutte...");
        scanner.nextLine();
    }

    private static void addSwimmerResults(String meetName, LocalDate meetDate, Member swimmer) {
        boolean addingDisciplines = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        while (addingDisciplines) {
            System.out.println("\nVælg disciplin: ");
            System.out.println("1. Crawl");
            System.out.println("2. Rygsvømning");
            System.out.println("3. Brystsvømning");
            System.out.println("4. Butterfly");
            System.out.print("Indtast valg: ");
            int disciplineChoice = scanner.nextInt();
            scanner.nextLine();
            String discipline = BestTimeManagement.translateDiscipline(disciplineChoice);

            System.out.print("\nIndtast tid (MM:ss:hh): ");
            String time = scanner.nextLine();
            System.out.print("Indtast placering: ");
            int placement = scanner.nextInt();
            scanner.nextLine();

            String formattedDate = meetDate.format(formatter);
            System.out.println("Registreret: " + meetName + " - " + formattedDate + ": " + swimmer.getFirstName() + " " + swimmer.getLastName() + " - Disciplin: " + discipline + " - Tid: " + time + " - Placering: " + placement);
            System.out.println("\n1.Bekræft");
            System.out.println("2.Annuller");
            System.out.print("Indtast valg: ");
            int confirm = scanner.nextInt();
            scanner.nextLine();

            if (confirm == 1) {
                CompetitionEvent result = new CompetitionEvent(meetName, meetDate, swimmer.getFirstName() + " " + swimmer.getLastName(), swimmer.getMemberID(), discipline, time, placement);
                competitionResults.add(result);
                addCompetitionBestTime(swimmer, discipline, meetDate, time);
                System.out.println("Resultat bekræftet og tilføjet.");
            } else {
                System.out.println("Resultat ikke tilføjet.");
            }

            System.out.println("\nValgmuligheder for svømmeren ved dette stævne:");
            System.out.println("1. Vil du tilføje flere resultater for denne svømmer i andre discipliner?");
            System.out.println("2. Afslut tilføjelse af resultater for denne svømmer ");
            System.out.print("Indtast valg: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            addingDisciplines = (choice == 1);
        }
    }
    //Metode til at tilføje ny bedste konkurrencetid i en disciplin
    public static void addCompetitionBestTime(Member selectedSwimmer, String discipline, LocalDate date, String time) {
        int memberID = selectedSwimmer.getMemberID();
        boolean isNewPersonalBest = BestTimeManagement.isNewPersonalBest(
                selectedSwimmer.getFirstName(),
                selectedSwimmer.getLastName(),
                memberID,
                discipline,
                time,
                BestTime.TimeType.COMPETITION
        );

        if (isNewPersonalBest) {
            // Fjern den gamle tid
            BestTimeManagement.swimmerTimes.removeIf(t -> t.getMemberID() == memberID &&
                    t.getDiscipline().equalsIgnoreCase(discipline) &&
                    t.getType() == BestTime.TimeType.COMPETITION);

            // Tilføj den nye tid
            BestTime record = new BestTime(
                    BestTime.TimeType.COMPETITION,
                    discipline,
                    date,
                    time,
                    selectedSwimmer.getFirstName(),
                    selectedSwimmer.getLastName(),
                    memberID
            );
            BestTimeManagement.swimmerTimes.add(record);
        }
    }

    public static void searchAnDisplayCompetitionResults() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        System.out.println("Søg efter stævneresultater baseret på:");
        System.out.println("1. Turneringsnavn");
        System.out.println("2. Svømmer (Fornavn, Efternavn, Medlems ID)");
        System.out.print("Vælg en søgemetode: ");
        int searchType = scanner.nextInt();
        scanner.nextLine(); // Ryd scannerens buffer

        System.out.print("Indtast søgekriterium: ");
        String searchCriteria = scanner.nextLine().toLowerCase();
        System.out.println();

        boolean foundResults = false;
        for (CompetitionEvent result : competitionResults) {
            String formattedDate = result.getMeetDate().format(formatter);
            String swimmerName = result.getSwimmerName().toLowerCase();
            String meetName = result.getMeetName().toLowerCase();
            String swimmerID = String.valueOf(result.getSwimmerID());

            if ((searchType == 1 && meetName.contains(searchCriteria)) ||
                    (searchType == 2 && (swimmerName.contains(searchCriteria) || swimmerID.equals(searchCriteria)))) {
                System.out.println(result.getMeetName() + " - " + formattedDate + ": " +
                        result.getSwimmerName() + " (Medlems ID: " + result.getSwimmerID() +
                        ") - Disciplin: " + result.getDiscipline() + " - Tid: " +
                        result.getTime() + " - Placering: " + result.getPlacement());
                foundResults = true;
            }
        }
        if (!foundResults) {
            System.out.println("Ingen resultater fundet for det angivne søgekriterium.");
        }
        System.out.println("Tryk på enter for at afslutte...");
        scanner.nextLine();
    }

    private static void displayThisCompetitionResults(String meetName, LocalDate meetDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = meetDate.format(formatter);

        System.out.println("\nRegistrerede resultater for " + meetName + " den " + formattedDate + ":");
        for (CompetitionEvent result : competitionResults) {
            if (result.getMeetName().equalsIgnoreCase(meetName) && result.getMeetDate().equals(meetDate)) {
                System.out.println(result.getSwimmerName() + " (Medlems ID: " + result.getSwimmerID() +
                        ") - Disciplin: " + result.getDiscipline() + " - Tid: " +
                        result.getTime() + " - Placering: " + result.getPlacement());
            }
        }
    }
}