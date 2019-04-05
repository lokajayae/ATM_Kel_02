public class Account {
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private double availableBalance; // funds available for withdrawal
   private double totalBalance; // funds available & pending deposits
   private boolean blocked;

   // Account constructor initializes attributes
   public Account(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
      accountNumber = theAccountNumber;
      pin = thePIN;
      availableBalance=theAvailableBalance;
      totalBalance=theTotalBalance;
      blocked=false;
   }

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN) {
       return userPIN == pin;
   } 
   
   public void setStatus(boolean x){
       this.blocked=x;
   }
   
   public boolean getStatus(){
       return this.blocked;
   }
   
   // returns available balance
   public double getAvailableBalance() {
      return availableBalance;
   }
   
   public void setPIN(int PIN){
       this.pin=PIN;
   }
  

   // returns the total balance
   public double getTotalBalance() {
      return totalBalance;
   } 

   public void credit(double amount) {
       totalBalance+=amount;
   }
   
   public void creditTransfer(double amount){
       totalBalance+=amount;
       availableBalance+=amount;
   }

   public void debit(double amount) {
        availableBalance-=amount;
       totalBalance-=amount;
   }

   public int getAccountNumber() {
      return accountNumber;  
   }
} 