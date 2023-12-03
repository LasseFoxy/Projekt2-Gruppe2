import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class BestTimeDataManagement {
    private static final Scanner scanner = new Scanner(System.in);
    public static List<BestTimeData> swimmerTimes = new ArrayList<>(); // Initialiser swimmerTimes som en tom liste i konstruktøren

    public BestTimeDataManagement() {
        swimmerTimes = new ArrayList<>();
    }
    public static boolean isValidDateFormat(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public static boolean isValidTimeFormat(String timeString) {
        // Assuming time format is MM:ss:hh
        return timeString.matches("^([0-5][0-9]):([0-5][0-9]):([0-9][0-9])$");
    }


    public static void addTrainingTime(Member selectedSwimmer, String discipline, LocalDate date, String time) {
        int memberID = selectedSwimmer.getMemberID();

        // Check if the new time is an improvement
        boolean isNewPersonalBest = isNewPersonalBest(
                selectedSwimmer.getFirstName(),
                selectedSwimmer.getLastName(),
                memberID,
                discipline,
                time,
                BestTimeData.TimeType.TRAINING
        );

        if (isNewPersonalBest) {
            // Add the new time as it's an improvement
            BestTimeData record = new BestTimeData(
                    BestTimeData.TimeType.TRAINING,
                    discipline,
                    date,
                    time,
                    selectedSwimmer.getFirstName(),
                    selectedSwimmer.getLastName(),
                    memberID
            );
            swimmerTimes.add(record);
            System.out.println("Træningstid tilføjet for " + selectedSwimmer.getFirstName() + " " + selectedSwimmer.getLastName());
        } else {
            System.out.println("Den indtastede tid er ikke forbedret.");
        }
    }


    public static void addCompetitionResult(Member selectedSwimmer, String discipline, LocalDate date, String time) {
        int memberID = selectedSwimmer.getMemberID();

        // Check if the new time is an improvement
        boolean isNewPersonalBest = isNewPersonalBest(
                selectedSwimmer.getFirstName(),
                selectedSwimmer.getLastName(),
                memberID,
                discipline,
                time,
                BestTimeData.TimeType.COMPETITION
        );

        if (isNewPersonalBest) {
            // Add the new time as it's an improvement
            BestTimeData record = new BestTimeData(
                    BestTimeData.TimeType.COMPETITION,
                    discipline,
                    date,
                    time,
                    selectedSwimmer.getFirstName(),
                    selectedSwimmer.getLastName(),
                    memberID
            );
            swimmerTimes.add(record);
            System.out.println("Konkurrence resultat tilføjet for " + selectedSwimmer.getFirstName() + " " + selectedSwimmer.getLastName());
        } else {
            System.out.println("Den indtastede tid er ikke forbedret.");
        }
    }

    public static void handleTrainingTime() {
        System.out.print("Søg efter medlem (Fornavn, Efternavn eller Medlems ID): ");
        String searchCriteria = scanner.nextLine();
        List<Member> foundMembers = MemberManagement.searchOnlyCompetitionSwimmers(searchCriteria);
        Member selectedMember = MemberManagement.selectMemberFromList(foundMembers);
        System.out.println();

        if (selectedMember != null) {
            System.out.println("Valgt medlem:");
            System.out.println(selectedMember);

            // Indtast disciplinen som et tal
            System.out.println("Tast 1 for Crawl");
            System.out.println("Tast 2 for Rygsvømning");
            System.out.println("Tast 3 for Brystsvømning");
            System.out.println("Tast 4 for Butterfly");
            System.out.print("Vælg nummer for disciplin: ");
            int disciplineNumber = Integer.parseInt(scanner.nextLine());

            // Oversæt disciplinnummer til tekst
            String discipline = translateDiscipline(disciplineNumber);

            if (discipline != null) {
                System.out.print("Indtast dato (dd.MM.yyyy): ");
                String dateString = scanner.nextLine();
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                System.out.print("Indtast tid (MM:ss:hh): ");
                String time = scanner.nextLine();

                addTrainingTime((Swimmer) selectedMember, discipline, date, time);
            } else {
                System.out.println("Ugyldigt disciplinnummer.");
            }
        } else {
            System.out.println("Ingen medlemmer fundet eller ugyldigt valg.");
        }
    }

    public static void addCompetitionResult() {
        System.out.print("Søg efter medlem (Fornavn, Efternavn eller Medlems ID): ");
        String searchCriteria = scanner.nextLine();
        List<Member> foundMembers = MemberManagement.searchOnlyCompetitionSwimmers(searchCriteria);
        Member selectedMember = MemberManagement.selectMemberFromList(foundMembers);
        System.out.println();

        if (selectedMember != null) {
            System.out.println("Valgt medlem:");
            System.out.println(selectedMember);
            System.out.println("Indtast disciplin: ");
            System.out.println("Tast 1 for Crawl");
            System.out.println("Tast 2 for Rygsvømning");
            System.out.println("Tast 3 for Brystsvømning");
            System.out.println("Tast 4 for Butterfly");
            System.out.print("Vælg nummer for disciplin: ");
            String discipline = scanner.nextLine();
            System.out.print("Indtast dato (dd.MM.yyyy): ");
            String dateString = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            System.out.print("Indtast tid (MM:ss:hh): ");
            String time = scanner.nextLine();

            addCompetitionResult((Swimmer) selectedMember, discipline, date, time);
        } else {
            System.out.println("Ingen medlemmer fundet eller ugyldigt valg.");
        }
    }

    public static void displayBestTimes() {
        System.out.print("Søg efter medlem (Fornavn, Efternavn eller Medlems ID): ");
        String searchCriteria = scanner.nextLine();
        List<Member> foundMembers = MemberManagement.searchOnlyCompetitionSwimmers(searchCriteria);
        Member selectedSwimmer = MemberManagement.selectMemberFromList(foundMembers);
        System.out.println();

        if (selectedSwimmer != null) {
            System.out.println("Valgt medlem:");
            System.out.println(selectedSwimmer);

            // Filtrer TimeRecords for den valgte svømmer
            List<BestTimeData> swimmerRecords = swimmerTimes.stream()
                    .filter(timeRecord -> timeRecord.getMemberID() == selectedSwimmer.getMemberID())
                    .toList();

            // Vis svømmerens tider
            System.out.println("Valgt svømmer: " + selectedSwimmer.getFirstName() + " " + selectedSwimmer.getLastName() + " (Medlems ID: " + selectedSwimmer.getMemberID() + ")");
            System.out.println("Time Records:");

            for (BestTimeData timeRecord : swimmerRecords) {
                System.out.println("Type: " + timeRecord.getType());
                System.out.println("Discipline: " + timeRecord.getDiscipline());
                System.out.println("Date: " + timeRecord.getDate());
                System.out.println("Time: " + timeRecord.getTime());
                System.out.println();
            }
        } else {
            System.out.println("Ingen medlemmer fundet eller ugyldigt valg.");
        }
    } // Metode der tjekker om den nye oplyste tid er en ny personly rekord eller ej
    public static boolean isNewPersonalBest(String firstName, String lastName, int memberID, String discipline, String newTime, BestTimeData.TimeType newTimeType) {
        boolean isCompetitionTime = newTimeType == BestTimeData.TimeType.COMPETITION;

        // Find existing records for the same swimmer, discipline, and time type
        List<BestTimeData> existingRecords = swimmerTimes.stream()
                .filter(timeRecord ->
                        timeRecord.getFirstName().equals(firstName) &&
                                timeRecord.getLastName().equals(lastName) &&
                                timeRecord.getMemberID() == memberID &&
                                timeRecord.getDiscipline().equalsIgnoreCase(discipline) &&
                                timeRecord.getType() == newTimeType)
                .collect(Collectors.toList());

        // If no records exist, it's a new personal best
        if (existingRecords.isEmpty()) {
            return true;
        }

        // Compare the new time with existing records
        for (BestTimeData timeRecord : existingRecords) {
            if (compareTimes(newTime, timeRecord.getTime()) >= 0) {
                return false; // New time is slower or equal to an existing record
            }
        }

        return true; // New time is faster than all existing records
    }

    /*public static void displayTop5SwimmersByDiscipline(String discipline, TimeRecord.TimeType timeType) {
        // Filtrer TimeRecords for den specifikke disciplin og tidstype
        List<TimeRecord> filteredRecords = new ArrayList<>();
        for (TimeRecord timeRecord : swimmerTimes) {
            if (timeRecord.getDiscipline().equalsIgnoreCase(discipline) && timeRecord.getType() == timeType) {
                filteredRecords.add(timeRecord);
            }
        }

        // Opret et ArrayList til at gemme den bedste tid for hver svømmer
        List<String> bestTimesList = new ArrayList<>();

        for (TimeRecord timeRecord : filteredRecords) {
            int memberID = timeRecord.getMemberID();
            String currentTime = timeRecord.getTime();

            // Find den bedste tid for hver svømmer og tilføj den til bestTimesList
            boolean foundBestTime = false;
            for (int i = 0; i < bestTimesList.size(); i++) {
                if (bestTimesList.get(i) == null) {
                    bestTimesList.set(i, currentTime);
                    foundBestTime = true;
                    break;
                } else if (compareTimes(currentTime, bestTimesList.get(i)) < 0) {
                    bestTimesList.add(i, currentTime);
                    foundBestTime = true;
                    break;
                }
            }
            if (!foundBestTime) {
                bestTimesList.add(currentTime);
            }
        }

        // Vis de top 5 svømmere med deres bedste tid
        System.out.println("Top 5 svømmere i " + discipline + " (" + timeType + "):");
        for (int i = 0; i < Math.min(5, bestTimesList.size()); i++) {
            String bestTime = bestTimesList.get(i);
            TimeRecord topSwimmerRecord = filteredRecords.get(i);
            String swimmerName = topSwimmerRecord.getFirstName() + " " + topSwimmerRecord.getLastName();
            int memberID = topSwimmerRecord.getMemberID();
            System.out.println(swimmerName + " (Medlems ID: " + memberID + ") - Bedste tid: " + bestTime);
        }
    }*/

    // Hjælpefunktion til at sammenligne to tider i formatet MM:ss:hh
    public static int compareTimes(String time1, String time2) {
        // Håndter tilfælde, hvor en tid er "Ingen tid registreret"
        if (time1.equals("Ingen tid registreret") && time2.equals("Ingen tid registreret")) {
            return 0; // Begge tider er ens
        } else if (time1.equals("Ingen tid registreret")) {
            return 1; // time2 er hurtigere, fordi time1 ikke har nogen tid
        } else if (time2.equals("Ingen tid registreret")) {
            return -1; // time1 er hurtigere, fordi time2 ikke har nogen tid
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

            // Sammenlign tid1 og tid2 sekventielt (minutter, sekunder, hundredeler)
            if (minutes1 < minutes2) {
                return -1;
            } else if (minutes1 > minutes2) {
                return 1;
            } else if (seconds1 < seconds2) {
                return -1;
            } else if (seconds1 > seconds2) {
                return 1;
            } else if (hundredths1 < hundredths2) {
                return -1;
            } else if (hundredths1 > hundredths2) {
                return 1;
            } else {
                return 0; // Tiderne er ens
            }
        }
    }

    public static void displayTop5Swimmers() {
        // Opret en Comparator for at sammenligne TimeRecords baseret på tid
        Comparator<BestTimeData> timeRecordComparator = (record1, record2) -> {
            String time1 = record1.getTime();
            String time2 = record2.getTime();
            return compareTimes(time1, time2);
        };

        // Lav en liste til discipliner
        List<String> disciplines = new ArrayList<>();

        // Lav en liste til tiderne for hver disciplin
        List<List<BestTimeData>> disciplineTimes = new ArrayList<>();

        // Opdel tiderne i discipliner
        for (BestTimeData timeRecord : swimmerTimes) {
            String discipline = timeRecord.getDiscipline();
            int disciplineIndex = disciplines.indexOf(discipline);

            if (disciplineIndex == -1) {
                // Disciplinen er ikke fundet, tilføj den til listerne
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
            List<BestTimeData> times = disciplineTimes.get(i);
            times.sort(timeRecordComparator);

            // Opret en HashSet for at holde styr på, hvilke svømmere der allerede er blevet vist
            Set<Integer> shownSwimmers = new HashSet<>();

            // Vis de top 5 tider for disciplinen
            System.out.println("Top 5 tider for disciplin \"" + discipline + "\":");
            int count = 0;
            for (BestTimeData timeRecord : times) {
                // Tjek om svømmeren allerede er blevet vist
                if (!shownSwimmers.contains(timeRecord.getMemberID())) {
                    String bestTime = timeRecord.getTime();
                    String swimmerName = timeRecord.getFirstName() + " " + timeRecord.getLastName();
                    System.out.println(swimmerName + " (Medlems ID: " + timeRecord.getMemberID() + ") - Bedste tid: " + bestTime);

                    count++;
                    if (count >= 5) {
                        break;
                    }

                    // Marker svømmeren som vist
                    shownSwimmers.add(timeRecord.getMemberID());
                }
            }
            System.out.println(); // En tom linje mellem discipliner
        }
    }



    // Metode til at oversætte disciplinnummer til tekst
    private static String translateDiscipline(int disciplineNumber) {
        return switch (disciplineNumber) {
            case 1 -> "Crawl";
            case 2 -> "Rygsvømning";
            case 3 -> "Brystsvømning";
            case 4 -> "Butterfly";
            default -> null; // Returner null for ugyldige numre
        };
    }
}
