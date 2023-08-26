import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 10;
        int score = 0;
        
        boolean playAgain = true;
        
        while (playAgain) {
            int targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;
            boolean guessedCorrectly = false;
            
            System.out.println("Welcome to the Number Guessing Game!");
            System.out.println("I've picked a number between " + minRange + " and " + maxRange + ". Try to guess it!");
            
            while (!guessedCorrectly && attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;
                
                if (userGuess < targetNumber) {
                    System.out.println("Too low! Try again.");
                } else if (userGuess > targetNumber) {
                    System.out.println("Too high! Try again.");
                } else {
                    guessedCorrectly = true;
                    System.out.println("Congratulations! You've guessed the correct number " + targetNumber + " in " + attempts + " attempts.");
                }
            }
            
            if (!guessedCorrectly) {
                System.out.println("Sorry, you've run out of attempts. The correct number was " + targetNumber + ".");
            }
            
            score += guessedCorrectly ? 1 : 0;
            System.out.println("Your current score: " + score);
            
            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainInput = scanner.next();
            playAgain = playAgainInput.equalsIgnoreCase("yes");
        }
        
        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
