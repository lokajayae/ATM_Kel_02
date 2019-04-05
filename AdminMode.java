/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class AdminMode {
    
    public void run(BankDatabase bankDatabase, CashDispenser cashDispenser){
        Keypad keypad;
        Screen screen;
        Account A;
        boolean end=false;
        keypad=new Keypad();
        screen=new Screen();
        
        while(!end){
        screen.displayMessageLine("===========ADMIN MODE===========");
        screen.displayMessageLine("1 : Unblock User");
        screen.displayMessageLine("2 : Cash Dispenser Amount");
        screen.displayMessageLine("3 : Add Cas Dispenser Amount");
        screen.displayMessageLine("4 : Exit Admin Mode");
        screen.displayMessage("Choice : ");
        int input=keypad.getInput();
        
        switch(input){
            case 1:
                screen.displayMessage("Account Number : ");
                int inputRec=keypad.getInput();
                A=bankDatabase.getAccount(inputRec);
                
                if(A==null){
                    screen.displayMessageLine("User Not Found");
                }
                else{
                    bankDatabase.setStatus(inputRec, false);
                    screen.displayMessageLine("User Unblocked");
                }
            break;
            
            case 2:
                screen.displayMessage("Money in Cash Dispenser : ");
                screen.displayDollarAmount(cashDispenser.getBills());
                screen.displayMessageLine("");
            break;
            
            case 3:
                screen.displayMessage("Number of $20 Money : ");
                int count=keypad.getInput();
                cashDispenser.setBills(count);
                screen.displayDollarAmount(count*20);
                screen.displayMessageLine(" has been added to Cash Dispenser");
            break;
            
            case 4:
                end=true;
            break;
        }
    }
    }
}
