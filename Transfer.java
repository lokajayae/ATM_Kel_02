/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class Transfer extends Transaction {
    private int inputRec;
    private int inputAmount;
    private Keypad keypad;
    private Screen screen;
    private BankDatabase bankDatabase;
    Account tujuanTransfer;
    
    public Transfer(int accountNumber, Screen atmScreen,BankDatabase atmBankDatabase){
        super(accountNumber, atmScreen, atmBankDatabase);
    }
    
    
    
    
    
    @Override
    public void execute(){
        keypad=new Keypad();
        screen=getScreen();
        bankDatabase=getBankDatabase();
        boolean end=false;
        
        while(!end){
            screen.displayMessage("Account Destination (0 TO Cancel) : ");
            inputRec=keypad.getInput();
            screen.displayMessage("Amount (0 to Cancel)                : ");
            inputAmount=keypad.getInput();
            
            
            if(inputRec==getAccountNumber()){
                screen.displayMessageLine("INVALID TRANSFER");
                end=true;
            }
            else{
            if(inputRec==0||inputAmount==0){
                end=true;
            }
            else if(inputAmount<0){
                screen.displayMessageLine("Please enter a Positive Amount");
            }
            else if(inputAmount>100){
                screen.displayMessageLine("Exceeding Transfer Limit($100)...");
            }
            else{
                tujuanTransfer=bankDatabase.getAccount(inputRec);

                if(tujuanTransfer!=null){
                    screen.displayMessageLine("======Validation=====");
                    screen.displayMessageLine("Account Number : "+inputRec);
                    screen.displayMessage("Amount : ");
                    screen.displayDollarAmount(inputAmount);
                    int choice;
                    screen.displayMessageLine("\nYour Choice");
                    screen.displayMessageLine("1 : Yes");
                    screen.displayMessageLine("2 : No");
                    choice=keypad.getInput();
                    
                    if(choice==1){
                    bankDatabase.creditTransfer(inputRec, inputAmount);
                    bankDatabase.debit(getAccountNumber(), inputAmount);
                    screen.displayMessageLine("Transfer Succeed");
                    end=true;
                    }
                }
                else{
                    screen.displayMessageLine("Destination Account is Not Available");
                }
               }
            }
        }   
    }
    
    
    
}
