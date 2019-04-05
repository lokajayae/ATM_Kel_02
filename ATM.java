public class ATM {
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private BankDatabase bankDatabase; // account information database
   private DepositSlot depositSlot;
   private Admin admin;

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int DEPOSIT = 3;
   private static final int CHANGEPIN=4;
   private static final int TRANSFER = 5;
   private static final int EXIT = 6; 

   // no-argument ATM constructor initializes instance variables
   public ATM() {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
      depositSlot=new DepositSlot();
      admin=new Admin();
   }

   // start ATM 
   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         // loop while user is not yet authenticated
         while (!userAuthenticated) {
            screen.displayMessageLine("\nWelcome!");       
            authenticateUser(); // authenticate user
         }
         
         performTransactions(); // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine("\nThank you! Goodbye!");
      }
   }

   // attempts to authenticate user against database
   private void authenticateUser() {
      int tries=0;
      
      screen.displayMessage("\nPlease enter your account number: ");
      int accountNumber = keypad.getInput(); // input account number
      
      while(tries<3){
      screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
      int pin = keypad.getInput(); // input PIN
      
     
      // set userAuthenticated to boolean value returned by database
      userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);
      
      // check whether authentication succeeded
      if (userAuthenticated) {
          if(admin.validateAdmin(accountNumber, pin)){
              admin.adminRun(bankDatabase, cashDispenser);
              userAuthenticated=false;
              break;
          }
          if(bankDatabase.getStatus(accountNumber)){
              screen.displayMessageLine("YOUR ACCOUNT IS BLOCKED");
              userAuthenticated=false;
              tries=5;
          }
          else{
             currentAccountNumber = accountNumber; // save user's account #
             tries=5; 
          }
      } 
      else {
         screen.displayMessageLine("WRONG PIN...");
         tries++;
      }
      
      if(tries==3){
          screen.displayMessageLine("KESALAHAN TIGA KALI....");
          screen.displayMessageLine("BLOCKED...");
          bankDatabase.setStatus(accountNumber, true);
      }
      }
   } 

   // display the main menu and perform transactions
   private void performTransactions() {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch (mainMenuSelection) {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:        
               // initialize as new object of chosen type
               currentTransaction = createTransaction(mainMenuSelection);
               currentTransaction.execute(); // execute transaction
               break; 
               
            case WITHDRAWAL:
                currentTransaction=createTransaction(mainMenuSelection);
                currentTransaction.execute();
                break;
                
            case DEPOSIT :
                currentTransaction=createTransaction(mainMenuSelection);
                currentTransaction.execute();
                break;
                
            case CHANGEPIN :
                currentTransaction=createTransaction(mainMenuSelection);
                currentTransaction.execute();
                break;
                
            case TRANSFER:
                currentTransaction=createTransaction(mainMenuSelection);
                currentTransaction.execute();
                break;
                
            case EXIT: // user chose to terminate session
               screen.displayMessageLine("\nExiting the system...");
               userExited = true; // this ATM session should end
               break;
               
            default: //user choose not in the menu
               screen.displayMessageLine(
                  "\nYou did not enter a valid selection. Try again.");
               break;
         }
      } 
   } 

   // display the main menu and return an input selection
   private int displayMainMenu() {
      screen.displayMessageLine("\nMain menu:");
      screen.displayMessageLine("1 - View my balance");
      screen.displayMessageLine("2 - Withdraw cash");
      screen.displayMessageLine("3 - Deposit funds");
      screen.displayMessageLine("4 - Change PIN");
      screen.displayMessageLine("5 - Transfer");
      screen.displayMessageLine("6 - Exit\n");
      screen.displayMessage("Enter a choice: ");
      return keypad.getInput(); // return user's selection
   } 
         
   private Transaction createTransaction(int type) {
      Transaction temp = null; 
          
      switch (type) {
         case BALANCE_INQUIRY: 
            temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
            break;
            
         case WITHDRAWAL:
             temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser );
             break;
             
         case DEPOSIT:
             temp=new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
             break;
             
         case CHANGEPIN:
             temp=new changePIN(currentAccountNumber, screen, bankDatabase);
             break;
             
         case TRANSFER:
             temp=new Transfer(currentAccountNumber, screen, bankDatabase);
             break;
      }

      return temp;
   } 
}