import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class TrainingPlan {
    private String planName;
    private List<String> exercises;
    private Map<String, String> exerciseTimes;

    public TrainingPlan(String planName, List<String> exercises) {
        this.planName = planName;
        this.exercises = exercises;
        this.exerciseTimes = new HashMap<>();
        initializeExerciseTimes();
    }

    private void initializeExerciseTimes() {
        for (String exercise : exercises) {
            exerciseTimes.put(exercise, "Not specified");
        }
    }

    public String getPlanName() {
        return planName;
    }

    public List<String> getExercises() {
        return exercises;
    }

    public Map<String, String> getExerciseTimes() {
        return exerciseTimes;
    }

    public void setExerciseTime(String exercise, String time) {
        if (exerciseTimes.containsKey(exercise)) {
            exerciseTimes.put(exercise, time);
        } else {
            System.out.println("Exercise not found in the training plan.");
        }
    }
}

class SwimmingTeam {
    Map<String, TrainingPlan> trainingPlans;

    public SwimmingTeam() {
        this.trainingPlans = new HashMap<>();
    }

    public void createTrainingPlan(String planName, List<String> exercises) {
        TrainingPlan trainingPlan = new TrainingPlan(planName, exercises);
        trainingPlans.put(planName, trainingPlan);
    }

    public void displayTrainingPlans() {
        System.out.println("Available Training Plans:");
        int index = 1;
        for (String planName : trainingPlans.keySet()) {
            System.out.println(index + ". " + planName);
            index++;
        }
    }

    public void displayTrainingPlanDetails(String planName) {
        TrainingPlan trainingPlan = trainingPlans.get(planName);
        if (trainingPlan != null) {
            System.out.println("Training Plan: " + trainingPlan.getPlanName());
            System.out.println("Exercises:");
            for (String exercise : trainingPlan.getExercises()) {
                System.out.println("- " + exercise + " | Time: " + trainingPlan.getExerciseTimes().get(exercise));
            }
        } else {
            System.out.println("Training Plan not found.");
        }
    }

    public void setExerciseTime(String planName, String exercise, String time) {
        TrainingPlan trainingPlan = trainingPlans.get(planName);
        if (trainingPlan != null) {
            trainingPlan.setExerciseTime(exercise, time);
            System.out.println("Time set successfully.");
        } else {
            System.out.println("Training Plan not found.");
        }
    }
}

public class DelfinTrainingplan {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SwimmingTeam juniorTeam = new SwimmingTeam();
        SwimmingTeam seniorTeam = new SwimmingTeam();
        SwimmingTeam competitionTeam = new SwimmingTeam();

        // Junior Team Training Plan
        List<String> juniorExercises = new ArrayList<>();
        juniorExercises.add("Freestyle");
        juniorExercises.add("Backstroke");
        juniorExercises.add("Breaststroke");
        juniorExercises.add("Butterfly");
        juniorTeam.createTrainingPlan("Junior Training", juniorExercises);

        // Senior Team Training Plan
        List<String> seniorExercises = new ArrayList<>();
        seniorExercises.add("Advanced Freestyle");
        seniorExercises.add("Advanced Backstroke");
        seniorExercises.add("Advanced Breaststroke");
        seniorExercises.add("Advanced Butterfly");
        seniorTeam.createTrainingPlan("Senior Training", seniorExercises);

        // Competition Team Training Plan
        List<String> competitionExercises = new ArrayList<>();
        competitionExercises.add("High-Intensity Drills");
        competitionExercises.add("Start and Turn Techniques");
        competitionExercises.add("Interval Training");
        competitionTeam.createTrainingPlan("Competition Training", competitionExercises);

        // Display menu and test functionality
        int choice;
        do {
            System.out.println("\n1. Display Junior Team Training Plans");
            System.out.println("2. Display Senior Team Training Plans");
            System.out.println("3. Display Competition Team Training Plans");
            System.out.println("4. Set Exercise Time");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    juniorTeam.displayTrainingPlans();
                    System.out.print("Enter the number of the plan to view details: ");
                    int juniorPlanIndex = scanner.nextInt();
                    String juniorPlanName = getPlanNameByIndex(juniorTeam, juniorPlanIndex);
                    juniorTeam.displayTrainingPlanDetails(juniorPlanName);
                    break;
                case 2:
                    seniorTeam.displayTrainingPlans();
                    System.out.print("Enter the number of the plan to view details: ");
                    int seniorPlanIndex = scanner.nextInt();
                    String seniorPlanName = getPlanNameByIndex(seniorTeam, seniorPlanIndex);
                    seniorTeam.displayTrainingPlanDetails(seniorPlanName);
                    break;
                case 3:
                    competitionTeam.displayTrainingPlans();
                    System.out.print("Enter the number of the plan to view details: ");
                    int competitionPlanIndex = scanner.nextInt();
                    String competitionPlanName = getPlanNameByIndex(competitionTeam, competitionPlanIndex);
                    competitionTeam.displayTrainingPlanDetails(competitionPlanName);
                    break;
                case 4:
                    System.out.print("Enter the number of the plan: ");
                    int planIndex = scanner.nextInt();
                    String selectedPlanName = getPlanNameByIndex(juniorTeam, planIndex);
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter the exercise name: ");
                    String exerciseName = scanner.nextLine();
                    System.out.print("Enter the time for the exercise: ");
                    String exerciseTime = scanner.nextLine();
                    juniorTeam.setExerciseTime(selectedPlanName, exerciseName, exerciseTime);
                    seniorTeam.setExerciseTime(selectedPlanName, exerciseName, exerciseTime);
                    competitionTeam.setExerciseTime(selectedPlanName, exerciseName, exerciseTime);
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 5);
    }

    private static String getPlanNameByIndex(SwimmingTeam team, int index) {
        int count = 1;
        for (String planName : team.trainingPlans.keySet()) {
            if (count == index) {
                return planName;
            }
            count++;
        }
        return null;
    }
}