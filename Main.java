import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class AccountBank {
    int accountNumber;
    String accountHolderName;
    double balance;
    int cibilScore;
    AccountBank next;

    public AccountBank(int accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.cibilScore = 700;
        this.next = null;
    }

    public void displayAccountDetails() {
        JOptionPane.showMessageDialog(null, "Account Number: " + accountNumber + "\nAccount Holder: " 
                                      + accountHolderName + "\nBalance: " + balance + "\nCIBIL Score: " + cibilScore);
    }
}

class BankManagementSystem {
    private AccountBank head = null;

    public void createAccount(int accountNumber, String accountHolderName, double initialDeposit) {
        if (findAccount(accountNumber) != null) {
            JOptionPane.showMessageDialog(null, "Account with account number " + accountNumber + " already exists.");
            return;
        }
        
        AccountBank newAccount = new AccountBank(accountNumber, accountHolderName, initialDeposit);
        if (head == null) {
            head = newAccount;
        } else {
            AccountBank current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newAccount;
        }
        JOptionPane.showMessageDialog(null, "Account created successfully for " + accountHolderName);
    }

    public void displayAllAccounts() {
        if (head == null) {
            JOptionPane.showMessageDialog(null, "No accounts to display.");
            return;
        }
        AccountBank current = head;
        StringBuilder accountsInfo = new StringBuilder();
        while (current != null) {
            accountsInfo.append("Account Number: ").append(current.accountNumber).append("\nAccount Holder: ")
                        .append(current.accountHolderName).append("\nBalance: ").append(current.balance)
                        .append("\nCIBIL Score: ").append(current.cibilScore).append("\n\n");
            current = current.next;
        }
        JOptionPane.showMessageDialog(null, accountsInfo.toString());
    }

    public void deposit(int accountNumber, double amount) {
        AccountBank account = findAccount(accountNumber);
        if (account != null) {
            if (amount > 100000) {
                amount += 1000;
                account.cibilScore += 10;
                JOptionPane.showMessageDialog(null, "Deposited over ₹1,00,000! Bonus ₹1,000 added.\nYour CIBIL score increased by 10.");
            }
            account.balance += amount;
            account.cibilScore += 5;
            JOptionPane.showMessageDialog(null, "Amount deposited successfully! Your CIBIL score increased.");
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    public void withdraw(int accountNumber, double amount) {
        AccountBank account = findAccount(accountNumber);
        if (account != null) {
            if (amount > 50000) {
                int confirm = JOptionPane.showConfirmDialog(null, "Amount is large. Do you wish to proceed?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            if (account.balance >= amount) {
                account.balance -= amount;
                account.cibilScore -= 5;
                JOptionPane.showMessageDialog(null, "Amount withdrawn successfully! Your CIBIL score decreased slightly.");
            } else {
                account.cibilScore -= 10;
                JOptionPane.showMessageDialog(null, "Insufficient balance. CIBIL score decreased due to insufficient funds.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    public void displayCibilScore(int accountNumber) {
        AccountBank account = findAccount(accountNumber);
        if (account != null) {
            JOptionPane.showMessageDialog(null, "CIBIL Score for Account " + accountNumber + ": " + account.cibilScore);
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    private AccountBank findAccount(int accountNumber) {
        AccountBank current = head;
        while (current != null) {
            if (current.accountNumber == accountNumber) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}

public class Main extends Frame implements ActionListener {
    BankManagementSystem bank = new BankManagementSystem();
    Button createAccBtn, depositBtn, withdrawBtn, displayAllBtn, cibilBtn;

    public Main() {
        setLayout(new FlowLayout());
        
        // Set the background color to light pink
        setBackground(new Color(255, 182, 193)); // Light Pink

        // Title Label
        Label titleLabel = new Label("Bank Management System");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.RED); // Set title color to red
        add(titleLabel);

        // Create Account Button
        createAccBtn = new Button("Create Account");
        createAccBtn.addActionListener(e -> createAccount());
        add(createAccBtn);

        // Deposit Button
        depositBtn = new Button("Deposit");
        depositBtn.addActionListener(e -> deposit());
        add(depositBtn);

        // Withdraw Button
        withdrawBtn = new Button("Withdraw");
        withdrawBtn.addActionListener(e -> withdraw());
        add(withdrawBtn);

        // Display All Accounts Button
        displayAllBtn = new Button("Display All Accounts");
        displayAllBtn.addActionListener(e -> bank.displayAllAccounts());
        add(displayAllBtn);

        // Display CIBIL Score Button
        cibilBtn = new Button("Display CIBIL Score");
        cibilBtn.addActionListener(e -> displayCibilScore());
        add(cibilBtn);

        // Set up the Frame
        setTitle("Bank Management System");
        setSize(400, 300);
        setVisible(true);
        setLocationRelativeTo(null); // Center the window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    private void createAccount() {
        String accNumStr = JOptionPane.showInputDialog("Enter Account Number:");
        if (accNumStr == null) return; // Exit if 'Cancel' is clicked
        
        String holderName = JOptionPane.showInputDialog("Enter Account Holder Name:");
        if (holderName == null) return; // Exit if 'Cancel' is clicked
        
        String initialDepositStr = JOptionPane.showInputDialog("Enter Initial Deposit:");
        if (initialDepositStr == null) return; // Exit if 'Cancel' is clicked

        try {
            int accountNumber = Integer.parseInt(accNumStr);
            double initialDeposit = Double.parseDouble(initialDepositStr);
            bank.createAccount(accountNumber, holderName, initialDeposit);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers.");
        }
    }

    private void deposit() {
        String accNumStr = JOptionPane.showInputDialog("Enter Account Number:");
        if (accNumStr == null) return; // Exit if 'Cancel' is clicked

        String amountStr = JOptionPane.showInputDialog("Enter Deposit Amount:");
        if (amountStr == null) return; // Exit if 'Cancel' is clicked

        try {
            int accountNumber = Integer.parseInt(accNumStr);
            double amount = Double.parseDouble(amountStr);
            bank.deposit(accountNumber, amount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers.");
        }
    }

    private void withdraw() {
        String accNumStr = JOptionPane.showInputDialog("Enter Account Number:");
        if (accNumStr == null) return; // Exit if 'Cancel' is clicked

        String amountStr = JOptionPane.showInputDialog("Enter Withdrawal Amount:");
        if (amountStr == null) return; // Exit if 'Cancel' is clicked

        try {
            int accountNumber = Integer.parseInt(accNumStr);
            double amount = Double.parseDouble(amountStr);
            bank.withdraw(accountNumber, amount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers.");
        }
    }

    private void displayCibilScore() {
        String accNumStr = JOptionPane.showInputDialog("Enter Account Number:");
        if (accNumStr == null) return; // Exit if 'Cancel' is clicked

        try {
            int accountNumber = Integer.parseInt(accNumStr);
            bank.displayCibilScore(accountNumber);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers.");
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks
    }
}
