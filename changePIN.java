/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class changePIN extends Transaction {
    private int newPIN;
    private int newPINVal;
    private Keypad keypad;
    private Screen screen;
    private BankDatabase bankDatabase;
    
    private final static int CANCELED=0;
    
    //Constructor
    public changePIN(int accountNumber, Screen ATMscreen, BankDatabase atmBankDatabase){
        super(accountNumber, ATMscreen, atmBankDatabase);
    }
    
    
    @Override
    public void execute(){
    int PIN=getNewPIN();
    
    if(PIN==0){
        screen.displayMessageLine("YOUR TRANSACTION CANCELED....");
    }
    else{
        bankDatabase.setPIN(getAccountNumber(), PIN);
    }
    
}
    
    private int getNewPIN(){
        screen= getScreen();
        keypad= new Keypad();
        boolean status;
        bankDatabase=getBankDatabase();
        
        screen.displayMessageLine("Insert your new PIN");
        screen.displayMessage("New PIN : ");
        newPIN=keypad.getInput();
        screen.displayMessage("Validate Your New PIN :");
        newPINVal=keypad.getInput();
        status=bankDatabase.validatePIN(newPIN, getAccountNumber());
        if(newPIN==newPINVal){
            if(status!=true){
                return newPIN;
            }
            else{
                screen.displayMessageLine("PIN BARU SAMA DENGAN PIN LAMA...");
                return CANCELED;
            }
        }
        else{
            screen.displayMessageLine("PIN DAN VALIDASI TIDAK SESUAI...");
            newPIN=CANCELED;
        }
        return newPIN;
    }
    
}
