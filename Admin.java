/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class Admin {
    private int ID;
    private int PIN;
    
    
    Admin(){
        this.ID=0000;
        this.PIN=0000;
;
    }
    
    public boolean validateAdmin(int ID, int PIN){
        return this.ID==ID && this.PIN==PIN;
    }
    
    public void adminRun(BankDatabase bankDatabase, CashDispenser cashDispenser){
        AdminMode A=new AdminMode();
        A.run(bankDatabase, cashDispenser);
    }
}
