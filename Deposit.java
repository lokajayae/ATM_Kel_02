public class Deposit extends Transaction {
   private double amount; // amount to deposit
   private Keypad keypad; // reference to keypad
   private DepositSlot depositSlot; // reference to deposit slot
   private final static int CANCELED = 0; // constant for cancel option

   // Deposit constructor
   public Deposit(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      DepositSlot atmDepositSlot) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);

   } 

   // perform transaction
   @Override
   public void execute() {
        BankDatabase bankDatabase = getBankDatabase();    //get Reference from bankDatabase
        Screen screen = getScreen();
       amount=promptForDepositAmount();
       
       if(amount==CANCELED){
           screen.displayMessageLine("Caceling Transaction...");
       }
       else{
       screen.displayMessageLine("Please insert a deposit envelope containing $20.00");
       screen.displayMessageLine("Your envelope Has Been Recceived");
       screen.displayMessageLine("NOTE : The Money You Just Deposited wont be available until we verify the amount of any enclosed cash and your checks clear");
       bankDatabase.credit(getAccountNumber(), amount);    
       }
   }

   // prompt user to enter a deposit amount in cents 
   private double promptForDepositAmount() {
      Screen screen = getScreen(); // get reference to screen
      keypad=new Keypad();
      // display the prompt
      screen.displayMessage("\nPlease enter a deposit amount in " + 
         "CENTS (or 0 to cancel): ");
         int input = keypad.getInput(); // receive input of deposit amount
      
      // check whether the user canceled or entered a valid amount
      if (input == CANCELED) {
         return CANCELED;
      }
      else {
         return (double) input / 100; // return dollar amount
      }
   }
} 
