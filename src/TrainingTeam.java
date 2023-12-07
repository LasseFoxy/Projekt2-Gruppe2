import java.util.ArrayList;


public class TrainingTeam {
    private final String holdNavn;
    private final String holdDisciplin;
    private final ArrayList<Member> medlemmer; // Liste af medlemmer, herunder trænere og svømmere

    // Konstruktør
    public TrainingTeam(String holdNavn, String holdDisciplin) {
        this.holdNavn = holdNavn;
        this.holdDisciplin = holdDisciplin;
        this.medlemmer = new ArrayList<>();
    }

    // Getters
    public String getHoldNavn() {
        return holdNavn;
    }

    public String getHoldDisciplin() {
        return holdDisciplin;
    }

    public ArrayList<Member> getMedlemmer() {
        return new ArrayList<>(medlemmer); // Returnerer en kopi for at undgå ekstern ændring
    }

    // Metoder for at tilføje og fjerne medlemmer
    public void addMedlem(Member medlem) {
        this.medlemmer.add(medlem);
    }

    public void removeMedlem(Member medlem) {
        this.medlemmer.remove(medlem);
    }

    @Override
    public String toString() {
        String result = String.format("%-15s %-15s", holdNavn + " Hold", holdDisciplin + ":");

        if (medlemmer.isEmpty()) {
            result += String.format("\t%-35s", "Ingen medlemmer på holdet.");
        } else {
            for (Member medlem : medlemmer) {
                result += String.format("\t%-35s", medlem.getShortInfo());
            }
        }
        return result;
    }
}