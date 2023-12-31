import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

//Klasse til at håndtere bedste tider (vise, søge, oprette m.fl.)
public class BestTimeManagement {
    private static final Scanner scanner = new Scanner(System.in);
    public static List<BestTime> swimmerTimes = new ArrayList<>();

    public BestTimeManagement() {
        swimmerTimes = new ArrayList<>();
    }

    //Metode til at tilføje ny bedste træningstid i en disciplin
    public static void addTrainingTime(Member selectedSwimmer, String discipline, LocalDate date, String time) {
        int memberID = selectedSwimmer.getMemberID();

        boolean isNewPersonalBest = isNewPersonalBest(
                selectedSwimmer.getFirstName(),
                selectedSwimmer.getLastName(),
                memberID,
                discipline,
                time,
                BestTime.TimeType.TRAINING
        );

        if (isNewPersonalBest) {
            System.out.println("Ny træningstid registreret: " + discipline + " - " + date + " - " + time);
            int confirm = SearchAndInputMethods.promptForChoice(scanner, "Bekræft tilføjelse af ny tid (1 for Ja, 2 for Nej): ", 1, 2);

            if (confirm == 1) {
                // Fjern den gamle tid
                BestTimeManagement.swimmerTimes.removeIf(t -> t.getMemberID() == memberID &&
                        t.getDiscipline().equalsIgnoreCase(discipline) &&
                        t.getType() == BestTime.TimeType.TRAINING);

                // Tilføj den nye tid
                BestTime record = new BestTime(
                        BestTime.TimeType.TRAINING,
                        discipline,
                        date,
                        time,
                        selectedSwimmer.getFirstName(),
                        selectedSwimmer.getLastName(),
                        memberID
                );
                swimmerTimes.add(record);
                System.out.println("Træningstid bekræftet og tilføjet for " + selectedSwimmer.getShortInfo());
            } else {
                System.out.println("Tilføjelse af ny tid annulleret.");
            }
        } else {
            System.out.println("Den indtastede tid er ikke en forbedring.");
        }
    }

    //Metode til håndtering af træningstid
    public static void handleTrainingTime() {
        System.out.print("Søg efter medlem (Fornavn, Efternavn eller Medlems ID): ");
        String searchCriteria = scanner.nextLine();
        List<Member> foundMembers = SearchAndInputMethods.searchOnlyCompetitionSwimmers(searchCriteria);
        Member selectedMember = SearchAndInputMethods.selectMemberFromList(foundMembers);

        if (selectedMember != null) {
            System.out.println("\nValgt medlem:");
            System.out.println(selectedMember.getShortInfo());

            System.out.println("Tast 1 for Crawl");
            System.out.println("Tast 2 for Rygsvømning");
            System.out.println("Tast 3 for Brystsvømning");
            System.out.println("Tast 4 for Butterfly");
            int disciplineNumber = SearchAndInputMethods.promptForChoice(scanner, "Vælg nummer for disciplin: ", 1, 2, 3, 4);

            String discipline = translateDiscipline(disciplineNumber);

            if (discipline != null) {
                LocalDate date = SearchAndInputMethods.promptForDate(scanner, "Indtast dato (dd.MM.yyyy): ");
                String time = SearchAndInputMethods.promptForTime(scanner, "Indtast tid (MM:ss:hh): ");

                addTrainingTime(selectedMember, discipline, date, time);
            } else {
                System.out.println("Ugyldigt disciplinnummer.");
            }
        } else {
            System.out.println("Ingen medlemmer fundet eller ugyldigt valg.");
        }
    }

    //Metode til at vise bedste tider for en svømmer.
    public static void displayBestTimes() {
        System.out.print("Søg efter medlem (Fornavn, Efternavn eller Medlems ID): ");
        String searchCriteria = scanner.nextLine();
        List<Member> foundMembers = SearchAndInputMethods.searchOnlyCompetitionSwimmers(searchCriteria);
        Member selectedSwimmer = SearchAndInputMethods.selectMemberFromList(foundMembers);
        System.out.println();

        if (selectedSwimmer != null) {
            // Filtrer tider for den valgte svømmer
            List<BestTime> swimmerRecords = swimmerTimes.stream()
                    .filter(timeRecord -> timeRecord.getMemberID() == selectedSwimmer.getMemberID())
                    .sorted(Comparator.comparing(BestTime::getDiscipline)
                            .thenComparing(BestTime::getType))
                    .toList();

            System.out.println("Tidsrekorder for " + selectedSwimmer.getShortInfo() + ":");
            for (BestTime timeRecord : swimmerRecords) {
                System.out.println(timeRecord.toString());
            }
            System.out.println("Tryk på Enter for at fortsætte...");
            scanner.nextLine();
        } else {
            System.out.println("Ingen medlemmer fundet eller ugyldigt valg.");
        }
    }

    // Metode der tjekker om den nye oplyste tid er en ny personly rekord eller ej
    public static boolean isNewPersonalBest(String firstName, String lastName, int memberID, String discipline, String newTime, BestTime.TimeType newTimeType) {

        //Find tidligere rekorder for svømmeren med disciplin og tidstype
        List<BestTime> existingRecords = swimmerTimes.stream()
                .filter(timeRecord ->
                        timeRecord.getFirstName().equals(firstName) &&
                                timeRecord.getLastName().equals(lastName) &&
                                timeRecord.getMemberID() == memberID &&
                                timeRecord.getDiscipline().equalsIgnoreCase(discipline) &&
                                timeRecord.getType() == newTimeType)
                .toList();

        // Hvis ingen rekorder eksisterer, registrer som ny bedste tid.
        if (existingRecords.isEmpty()) {
            return true;
        }

        // Sammenlign rekorder
        for (BestTime timeRecord : existingRecords) {
            if (compareTimes(newTime, timeRecord.getTime()) >= 0) {
                return false; // New time is slower or equal to an existing record
            }
        }

        return true;
    }

    // Hjælpefunktion til at sammenligne to tider i formatet MM:ss:hh
    public static int compareTimes(String time1, String time2) {
        if (time1.equals("Ingen tid registreret") && time2.equals("Ingen tid registreret")) {
            return 0;
        } else if (time1.equals("Ingen tid registreret")) {
            return 1;
        } else if (time2.equals("Ingen tid registreret")) {
            return -1;
        } else {
            // Del tidene op i minutter, sekunder og hundredeler
            String[] parts1 = time1.split(":");
            String[] parts2 = time2.split(":");

            int minutes1 = Integer.parseInt(parts1[0]);
            int seconds1 = Integer.parseInt(parts1[1]);
            int hundredths1 = Integer.parseInt(parts1[2]);

            int minutes2 = Integer.parseInt(parts2[0]);
            int seconds2 = Integer.parseInt(parts2[1]);
            int hundredths2 = Integer.parseInt(parts2[2]);

            // Sammenlign tid1 og tid2 hver for sig (minutter, sekunder, hundredeler)
            if (minutes1 < minutes2) {
                return -1;
            } else if (minutes1 > minutes2) {
                return 1;
            } else if (seconds1 < seconds2) {
                return -1;
            } else // Tiderne er ens
                if (seconds1 > seconds2) {
                return 1;
            } else return Integer.compare(hundredths1, hundredths2);
        }
    }

    public static void displayTop5Swimmers() {
        // Opret en Comparator for at sammenligne svømmeres bedste tid (træningstid vs. konkurrence)
        Comparator<BestTime> timeRecordComparator = (record1, record2) -> {
            String time1 = record1.getTime();
            String time2 = record2.getTime();
            return compareTimes(time1, time2);
        };

        // Lav en liste til discipliner
        List<String> disciplines = new ArrayList<>();

        // Lav en liste til tiderne for hver disciplin
        List<List<BestTime>> disciplineTimes = new ArrayList<>();

        // Opdel tiderne i discipliner
        for (BestTime timeRecord : swimmerTimes) {
            String discipline = timeRecord.getDiscipline();
            int disciplineIndex = disciplines.indexOf(discipline);

            if (disciplineIndex == -1) {
                disciplines.add(discipline);
                disciplineTimes.add(new ArrayList<>());
                disciplineIndex = disciplines.size() - 1;
            }

            // Tilføj tiden til den relevante liste for disciplinen
            disciplineTimes.get(disciplineIndex).add(timeRecord);
        }

        // Sorter tiderne for hver disciplin baseret på tid i stigende rækkefølge
        for (int i = 0; i < disciplines.size(); i++) {
            String discipline = disciplines.get(i);
            List<BestTime> times = disciplineTimes.get(i);
            times.sort(timeRecordComparator);

            // Opret en HashSet for at holde styr på, hvilke svømmere der allerede er blevet vist
            Set<Integer> shownSwimmers = new HashSet<>();

            // Vis de top 5 tider for disciplinen
            System.out.println("Top 5 tider for disciplin \"" + discipline + "\":");
            int count = 0;
            for (BestTime timeRecord : times) {
                if (!shownSwimmers.contains(timeRecord.getMemberID())) {
                    String formattedRecord = String.format("%-20s (Medlems ID: %d)  \t  Bedste tid: %-8s",
                            timeRecord.getFirstName() + " " + timeRecord.getLastName(),
                            timeRecord.getMemberID(),
                            timeRecord.getTime());
                    System.out.println(formattedRecord);

                    count++;
                    if (count >= 5) break;
                    shownSwimmers.add(timeRecord.getMemberID());
                }
            }
            System.out.println();
        }
    }

    // Metode til at oversætte disciplin casenummer til tekst
    public static String translateDiscipline(int disciplineNumber) {
        return switch (disciplineNumber) {
            case 1 -> "Crawl";
            case 2 -> "Rygsvømning";
            case 3 -> "Brystsvømning";
            case 4 -> "Butterfly";
            default -> null; //
        };
    }
}