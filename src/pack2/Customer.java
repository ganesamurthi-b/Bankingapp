package pack2;
import java.util.*;
import java.io.*;

public class Customer {

    private static int cusidgen;
    static Scanner sc = new Scanner(System.in);

    


    public static int cusidgenfunc() {
        cusidgen += 1;
        return cusidgen;
    }


    int cusid;
    String username;
    String name;
    int age;
    String gender;
    int aadharno;
    int phno;
    Account acc;
    int accno;


    Customer(int cusid, String username, String name, int age, String gender, 
    int aadharno, int phno, int accno, int balance, String passwd) {

        this.cusid = cusid;
        this.username = username;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.aadharno = aadharno;
        this.phno = phno;
        this.accno = accno;
        acc = new Account(accno, balance, passwd);

    }

    Customer (String username, String name, int age, String gender, int aadharno,
    int phno, int balance, String ifsccode, String passwd) {
        this.cusid = cusidgenfunc();
        this.username = username;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.aadharno = aadharno;
        this.phno = phno;
        acc = new Account(balance, passwd);
    }

    public void deposit(int amt) throws Exception {

        Bank.deposit(this, amt);
    }
    public void withdraw(int amt) throws Exception {
        Bank.withdraw(this, amt);
    }

    public void transfer(String to_username, int amt) throws Exception {
        Bank.transfer(this, to_username, amt);
    }

    public void getBalance() throws Exception{
        Bank.getBalance(this);
    }

    public void viewTransaction() throws Exception {
        Bank.viewTransaction(this);
    }
    public void passwdreset() throws Exception {

        System.out.println("Please enter new passwd");
        String newpasswd = sc.next();
        Bank.passwdreset(this, newpasswd);
    }
    
}