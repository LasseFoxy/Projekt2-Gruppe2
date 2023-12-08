import java.io.*;

public class DataManager {
    private static final String DATA_FOLDER = "data"; // Mappe til at gemme datafiler

    public static void saveAllData() {
        createDataFolderIfNeeded(); // Opret data-mappen, hvis den ikke eksisterer

        saveData("members.dat", MemberManagement.membersList);
        saveData("payments.dat", AnnualPaymentManagement.paymentList);
        saveData("times.dat", BestTimeManagement.swimmerTimes);
        saveData("competitionResults.dat", CompetitionEventManagement.competitionResults);
        saveData("teams.dat", TrainingTeamManagement.holdListe);
        System.exit(0);
    }

    public static void loadAllData() {
        createDataFolderIfNeeded(); // Opret data-mappen, hvis den ikke eksisterer

        if (fileExists("members.dat")) {
            MemberManagement.membersList = loadData("members.dat");
        }

        if (fileExists("payments.dat")) {
            AnnualPaymentManagement.paymentList = loadData("payments.dat");
        }

        if (fileExists("times.dat")) {
            BestTimeManagement.swimmerTimes = loadData("times.dat");
        }

        if (fileExists("competitionResults.dat")) {
            CompetitionEventManagement.competitionResults = loadData("competitionResults.dat");
        }

        if (fileExists("teams.dat")) {
            TrainingTeamManagement.holdListe = loadData("teams.dat");
        }
    }

    private static boolean fileExists(String fileName) {
        File file = new File(DATA_FOLDER + File.separator + fileName);
        return file.exists();
    }


    private static void createDataFolderIfNeeded() {
        File dataFolder = new File(DATA_FOLDER);
        if (!dataFolder.exists()) {
            dataFolder.mkdir(); // Opret mappen, hvis den ikke eksisterer
        }
    }

    private static void saveData(String fileName, Object data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FOLDER + File.separator + fileName))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T loadData(String fileName) {
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FOLDER + File.separator + fileName))) {
                return (T) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
