// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction {
   private double amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser
   

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 7;

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);
      
   }

   // perform transaction
   @Override
   public void execute() {
      BankDatabase bankDatabase = getBankDatabase();    //get Reference from bankDatabase
      Screen screen = getScreen();
      boolean success=false;
      
      while(!success){
       amount=displayMenuOfAmounts();
       
       if(amount==7){
           screen.displayMessageLine("Cancelling Transaction....");
           success=true;
       }
       else if(amount<0){
           screen.displayMessageLine("Please Enter Positive Amount");
       }
       else if(amount>bankDatabase.getAvailableBalance(getAccountNumber())){
           screen.displayMessageLine("Insufficient funds in your account.");
           screen.displayMessageLine("Please choose smaller amounts.");
       }
       else if(amount%20!=0){
           screen.displayMessageLine("Please Enter Valid Amount");
           screen.displayMessageLine("Only $20 Bills Available");
       }
       else if(cashDispenser.isSufficientCashAvailable((int) amount)){
           screen.displayMessageLine("Insufficient funds in cash dispenser.");
           screen.displayMessageLine("Please choose smaller amounts.");
       }
       else{
       bankDatabase.debit(getAccountNumber(), amount);
       cashDispenser.dispenseCash((int) amount);
       screen.displayMessageLine("Your cash has been dispended. Please take your cash now");
       success=true;
       }
       }
   }

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts() {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      keypad=new Keypad();
      
      // array of amounts to correspond to menu numbers
      int[] amounts = {0, 20, 40, 60, 100, 200};

      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the withdrawal menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - $20");
         screen.displayMessageLine("2 - $40");
         screen.displayMessageLine("3 - $60");
         screen.displayMessageLine("4 - $100");
         screen.displayMessageLine("5 - $200");
         screen.displayMessageLine("6 - Other");
         screen.displayMessageLine("7 - Cancel transaction");
         screen.displayMessage("\nChoose a withdrawal amount: ");

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch (input) {
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
            case 3: // corresponding amount from amounts array
            case 4:
            case 5:
               userChoice = amounts[input]; // save user's choice
               break;
            case 6:
                screen.displayMessage("Amount : ");
                int inputAmount=keypad.getInput();
                
                if(inputAmount>200){
                    screen.displayMessageLine("MELEBIHI LIMIT ($200)");
                    userChoice=0;
                }
                else{
                    userChoice=(int) inputAmount;
                }
                break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine(
                  "\nInvalid selection. Try again.");
         } 
      } 

      return userChoice; // return withdrawal amount or CANCELED
   }
} 