public class Main {
    public static void main(String[] args) {
        DataManager.loadAllData();
        Menu menu = new Menu();
        menu.runMenu();
    }
}