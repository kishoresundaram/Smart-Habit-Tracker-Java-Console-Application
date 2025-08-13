import java.io.*;
import java.util.*;

public class HabitTracker {
    private static final String FILE_NAME = "habits.txt";
    private static ArrayList<Habit> habits = new ArrayList<>();

    public static void main(String[] args) {
        loadHabits();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== SMART HABIT TRACKER ===");
            System.out.println("1. Add a new habit");
            System.out.println("2. Complete a habit for today");
            System.out.println("3. View progress");
            System.out.println("4. Save and Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addHabit(sc);
                case 2 -> completeHabit(sc);
                case 3 -> viewProgress();
                case 4 -> {
                    saveHabits();
                    System.out.println("Data saved. Exiting...");
                }
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private static void addHabit(Scanner sc) {
        System.out.print("Enter habit name: ");
        String name = sc.nextLine();
        System.out.print("Enter goal streak: ");
        int goal = sc.nextInt();
        habits.add(new Habit(name, goal));
        System.out.println("Habit added successfully!");
    }

    private static void completeHabit(Scanner sc) {
        if (habits.isEmpty()) {
            System.out.println("No habits to complete!");
            return;
        }
        viewProgress();
        System.out.print("Enter habit number to mark complete: ");
        int index = sc.nextInt() - 1;
        if (index >= 0 && index < habits.size()) {
            habits.get(index).completeHabit();
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private static void viewProgress() {
        if (habits.isEmpty()) {
            System.out.println("No habits to show!");
            return;
        }
        System.out.println("\n=== HABIT PROGRESS ===");
        for (int i = 0; i < habits.size(); i++) {
            System.out.println((i + 1) + ". " + habits.get(i));
        }
    }

    private static void saveHabits() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(habits);
        } catch (IOException e) {
            System.out.println("Error saving habits: " + e.getMessage());
        }
    }

    private static void loadHabits() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            habits = (ArrayList<Habit>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading habits: " + e.getMessage());
        }
    }
}
