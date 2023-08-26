import java.util.Scanner;

// Bank Account class
class BankAccount {
    private double balance;

    public BankAccount() {
        balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Invalid amount for withdrawal or insufficient balance.");
        }
    }
}

// User class
class User {
    private String pin;

    public User(String pin) {
        if (pin.length() == 4) {
            this.pin = pin;
        } else {
             throw new IllegalArgumentException("PIN must be 4 characters long.");
        }
        
    }
    public String getPin() {
        return pin;
    }
}

// ATM class
class ATM {
    private BankAccount account;
    private User user;
    private Scanner scanner;

    public ATM(User user, BankAccount account) {
        this.user = user;
        this.account = account;
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("Welcome to the ATM!");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
    }

    public boolean authenticate() {
        System.out.print("Enter your PIN: ");
        String enteredPin = scanner.next();
        return enteredPin.equals(user.getPin());
    }

    public void run() {
        if (!authenticate()) {
            System.out.println("Incorrect PIN. Exiting...");
            return;
        }

        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Current balance: " + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 4);
    }
}

public class ATMMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String newPin;
        do {
            System.out.print("Set up your 4-digit PIN: ");
            newPin = scanner.next();

            try {
                User newUser = new User(newPin);
                System.out.println("PIN set up successfully. Please log in to your account.");
                
                // Proceed with the ATM interaction
                ATM atm = new ATM(newUser, new BankAccount());
                atm.run();

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter a valid 4-digit PIN.");
            }
        } while (newPin.length() != 4);
    }
}
