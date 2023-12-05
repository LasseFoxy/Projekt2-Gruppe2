import java.util.ArrayList;


public class Team {
    private final String holdNavn;
    private final int holdNummer;
    private final ArrayList<Member> medlemmer; // Liste af medlemmer, herunder trænere og svømmere

    // Konstruktør
    public Team(String navn, int nummer) {
        this.holdNavn = navn;
        this.holdNummer = nummer;
        this.medlemmer = new ArrayList<>();
    }

    // Getters
    public String getHoldNavn() {
        return holdNavn;
    }

    public int getHoldNummer() {
        return holdNummer;
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
        String result = holdNavn + " Hold " + holdNummer + ":";
        if (medlemmer.isEmpty()) {
            result += "Ingen medlemmer på holdet.";
        } else {
            result += " ";
            for (Member medlem : medlemmer) {
                result += medlem.getShortInfo()+" , ";
            }
        }
        return result;
    }
}