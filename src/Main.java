/*Hej og velkommen til programmet. Vores program kan kort beskrives som et administrativt system,
der styrer forskellige aspekter af svømmeklubben delfinen. Det er opbygget ved hjælp af forskellige
klasser og objekter, såsom medlemsoplysninger (Member), årlige betalinger (AnnualPayment), bedste tider (BestTime),
stævneresultater (CompetitionEvent) og svømmehold (TrainingTeam). For at interagere med disse oplysninger og udføre
handlinger, bruger programmet forskellige styringsklasser som MemberManagement, AnnualPaymentManagement osv.
Hovedmenuen (Menu) giver brugeren mulighed for at udføre forskellige handlinger og administrere klubben på en
struktureret måde. Programmet gemmer og indlæser også data fra filer for at bevare oplysningerne mellem kørsler.
Samtidig er der også en klasse med nyttige hjælpemetoder (SearchAndInputMethods). Der er også en klasse der genererer
testmedlemmer indtil programmet gemmes for første gang. God fornøjelse*/

//Main metode der starter vores program og kalder loadAllData() metoden som indlæser datafiler hvis de eksisterer.
public class Main {
    public static void main(String[] args) {
        DataManager.loadAllData();
        Menu menu = new Menu();
        menu.runMenu();
    }
}