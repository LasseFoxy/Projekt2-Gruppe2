import java.io.*;
import java.util.ArrayList;

public class DataManager {
    private static final String MEMBERS_FILE = "members.dat";
    private static final String PAYMENTS_FILE = "payments.dat";
    private static final String TIMES_FILE = "times.dat";
    private static final String COMPETITION_RESULTS_FILE = "competitionResults.dat";
    private static final String TEAMS_FILE = "teams.dat";

    public static void saveAllData() {
        saveData(MEMBERS_FILE, MemberManagement.membersList);
        saveData(PAYMENTS_FILE, AnnualPaymentManagement.paymentList);
        saveData(TIMES_FILE, BestTimeManagement.swimmerTimes);
        saveData(COMPETITION_RESULTS_FILE, CompetitionEventManagement.competitionResults);
        saveData(TEAMS_FILE, TrainingTeamManagement.holdListe);
    }


    public static void loadAllData() {
        boolean membersFileExists = fileExists(MEMBERS_FILE);
        boolean paymentsFileExists = fileExists(PAYMENTS_FILE);
        boolean timesFileExists = fileExists(TIMES_FILE);
        boolean competitionResultsFileExists = fileExists(COMPETITION_RESULTS_FILE);
        boolean teamsFileExists = fileExists(TEAMS_FILE);

        if (membersFileExists) {
            MemberManagement.membersList = loadData(MEMBERS_FILE);
        }
        if (paymentsFileExists) {
            AnnualPaymentManagement.paymentList = loadData(PAYMENTS_FILE);
        }
        if (timesFileExists) {
            BestTimeManagement.swimmerTimes = loadData(TIMES_FILE);
        }
        if (competitionResultsFileExists) {
            CompetitionEventManagement.competitionResults = loadData(COMPETITION_RESULTS_FILE);
        }
        if (teamsFileExists) {
            TrainingTeamManagement.holdListe = loadData(TEAMS_FILE);
        }

        // Opret de tomme filer, hvis de ikke eksisterer
        if (!membersFileExists) {
            createEmptyFile(MEMBERS_FILE);
        }
        if (!paymentsFileExists) {
            createEmptyFile(PAYMENTS_FILE);
        }
        if (!timesFileExists) {
            createEmptyFile(TIMES_FILE);
        }
        if (!competitionResultsFileExists) {
            createEmptyFile(COMPETITION_RESULTS_FILE);
        }
        if (!teamsFileExists) {
            createEmptyFile(TEAMS_FILE);
        }
    }

    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    private static void createEmptyFile(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(null);  // Skriv en tom liste til filen
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveData(String fileName, Object data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T loadData(String fileName) {
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
                return (T) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}