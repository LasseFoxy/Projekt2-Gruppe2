import java.io.*;

public class DataManager {
    // Mappe til at gemme datafiler
    private static final String DATA_FOLDER = "data";

    // Metode til at gemme al data
    public static void saveAllData() {
        createDataFolderIfNeeded();

        saveData("members.dat", MemberManagement.membersList);
        saveData("payments.dat", AnnualPaymentManagement.paymentList);
        saveData("times.dat", BestTimeManagement.swimmerTimes);
        saveData("competitionResults.dat", CompetitionEventManagement.competitionResults);
        saveData("teams.dat", TrainingTeamManagement.holdListe);
        System.exit(0);
    }

    //Metode til at indlæse gemte filer
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

    //Metode der tjekker om en fil eksisterer
    private static boolean fileExists(String fileName) {
        File file = new File(DATA_FOLDER + File.separator + fileName);
        return file.exists();
    }

    //Metode der opretter datamappen hvis den ikke eksisterer
    private static void createDataFolderIfNeeded() {
        File dataFolder = new File(DATA_FOLDER);
        if (!dataFolder.exists()) {
            dataFolder.mkdir(); // Opret mappen, hvis den ikke eksisterer
        }
    }

    //Metode der gemmer data i en fil
    private static void saveData(String fileName, Object data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FOLDER + File.separator + fileName))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Metode der indlæser data fra en fil
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
