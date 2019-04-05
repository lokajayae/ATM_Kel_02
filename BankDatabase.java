public class BankDatabase {
   private Account[] accounts; // array of Accounts
   private Admin admin=new Admin();
   
   public BankDatabase() {
      accounts = new Account[2]; // just 2 accounts for testing
      accounts[0] = new Account(1234, 4321, 1000.0, 1200.0);
      accounts[1] = new Account(8765, 5678, 0, 200.0);
   }
   
     public Account getAccount(int accountNumber) {
     int i;
         
       for(i=0;i<2;i++){
           if(accounts[i].getAccountNumber()==accountNumber){
                break;
            }
       }
       if(i==2){
            return null; // if no matching account was found, return null
       }
       return accounts[i];
    }

   public boolean authenticateUser(int userAccountNumber, int userPIN) {
      boolean status;
      Account userAccount = getAccount(userAccountNumber);

      // if account exists, return result of Account method validatePIN
      if (userAccount != null) {
         status = userAccount.validatePIN(userPIN);
      }
      else {
          if(userAccountNumber==0000){
              status=true;
          }
          else{
            status=false; // account number not found, so return false
          }
      }
      return status;
   } 
    
   
   public boolean authenticateAdmin(int userAccountNumber, int PIN){
       return admin.validateAdmin(userAccountNumber, PIN);
   }

   public double getAvailableBalance(int userAccountNumber) {
     return getAccount(userAccountNumber).getAvailableBalance();
   } 

   public double getTotalBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getTotalBalance();
   } 

   public void credit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).credit(amount);
   }

   public void debit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).debit(amount);
   } 
   
   public boolean validatePIN(int PIN, int userAccountNumber){
       boolean status=getAccount(userAccountNumber).validatePIN(PIN);
       return status;
   }
   
   public void setPIN(int userAccountNumber, int PIN){
       getAccount(userAccountNumber).setPIN(PIN);
   }
   
   public void creditTransfer(int userAccountNumber, double amount){
       getAccount(userAccountNumber).creditTransfer(amount);
   }
   
   public void setStatus(int userAccountNumber, boolean x){
       getAccount(userAccountNumber).setStatus(x);
   }
   
   public boolean getStatus(int userAccountNumber){
       return getAccount(userAccountNumber).getStatus();
   }
  
   
   
   
} 