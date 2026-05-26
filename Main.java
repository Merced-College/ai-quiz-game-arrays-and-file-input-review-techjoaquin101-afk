import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static final int NUMBER_OF_QUESTIONS = 10; // Stores how many questions the quiz will use
    public static final int NUMBER_OF_CHOICES = 4;    // Stores how many answer choices each question has

    public static void main(String[] args) {

        // Array that holds all quiz questions
        String[] questions = new String[NUMBER_OF_QUESTIONS];

        // 2D array that holds all answer choices
        String[][] answers = new String[NUMBER_OF_QUESTIONS][NUMBER_OF_CHOICES];

        // Array that stores the correct answer index for each question
        int[] correctAnswers = new int[NUMBER_OF_QUESTIONS];

        // Load quiz data from the CSV file
        readQuizFile(questions, answers, correctAnswers);

        // Scanner used to read user input
        Scanner input = new Scanner(System.in);

        boolean playAgain = true; // Enhancement: allows replaying the quiz

        while (playAgain) {

            // Tracks how many questions the user gets correct
            int score = 0;

            System.out.println("Welcome to the AI Quiz Game!");
            System.out.println("Choose the correct answer by entering 1, 2, 3, or 4.\n");

            // Loop through each question in the quiz
            for (int i = 0; i < questions.length; i++) {

                // Display the current question
                System.out.println("Question " + (i + 1) + ": " + questions[i]);

                // Loop through all answer choices for this question
                for (int j = 0; j < answers[i].length; j++) {

                    // Print each answer choice
                    System.out.println((j + 1) + ". " + answers[i][j]);
                }

                // Ask the user for their answer
                System.out.print("Your answer: ");

                // Convert user input to zero-based index
                int userAnswer = input.nextInt() - 1;

                // Check if the user's answer is correct
                if (userAnswer == correctAnswers[i]) {
                    System.out.println("Correct!\n");

                    // Add one point to the score for a correct answer
                    score++;
                } else {
                    System.out.println("Incorrect.");

                    // Show the correct answer when the user is wrong
                    System.out.println("The correct answer was: " + answers[i][correctAnswers[i]] + "\n");
                }
            }

            // Print final quiz completion message
            System.out.println("Quiz complete!");

            // Display the user's final score
            System.out.println("Your final score is: " + score + " out of " + questions.length);

            // Enhancement: Ask if the user wants to play again
            System.out.print("Do you want to play again? (y/n): ");
            String again = input.next();

            if (!again.equalsIgnoreCase("y")) {
                playAgain = false; // End the loop
            }
        }

        // Close the scanner to prevent memory leaks
        input.close();
    }

    public static void readQuizFile(String[] questions, String[][] answers, int[] correctAnswers) {
        try {

            // Open the CSV file
            File file = new File("ai_quiz_questions.csv");

            // Scanner used to read the CSV file
            Scanner fileReader = new Scanner(file);

            // Skip the header line in the CSV
            fileReader.nextLine();

            // Tracks which question number we are filling
            int index = 0;

            // Loop through each line of the CSV file
            while (fileReader.hasNextLine() && index < questions.length) {

                // Read one full line from the CSV
                String line = fileReader.nextLine();

                // Split the line into question and answers
                String[] data = line.split(",");

                // Store the question text
                questions[index] = data[0];

                // Loop through answer choices
                for (int i = 0; i < NUMBER_OF_CHOICES; i++) {

                    // Store each answer choice
                    answers[index][i] = data[i + 1];
                }

                // Set the correct answer index (first choice)
                correctAnswers[index] = 0;

                // Move to the next question
                index++;
            }

            // Close the CSV reader
            fileReader.close();

        } catch (FileNotFoundException e) {

            // Error message if file is missing
            System.out.println("The quiz file could not be found.");
        }
    }
}