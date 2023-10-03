package pack2;

import java.sql.*;
import java.io.*;
import java.util.*;


public class Customermain {

    static Scanner sc = new Scanner(System.in);

    public static void go() throws Exception{

        Customer customerobj;
        int choice;


        while(true) {

            System.out.println("Press 1 to continue or 0 to exit");

            choice = sc.nextInt();
            if(choice == 0) break;

            customerobj = authenticate();

            if(customerobj == null) continue;

            else 
                operations(customerobj);

        }
    }

    private static Customer authenticate() throws Exception {

        String username;
        String passwd;
        Customer customerobj;

        System.out.println();
        System.out.println();
        System.out.println("Enter User name ");
        username = sc.next();
        System.out.println("Enter Account password");
        passwd = sc.next();

        Connection con = DriverManager.getConnection(Bank.url, Bank.user, Bank.passwd);
        Statement st = con.createStatement();
        String query = "select customer.username, customer.cusid, account.passwd from customer,account where username = '"+username+"' and customer.cusid = account.cusid";
        //System.out.println(query);
        ResultSet rs = st.executeQuery(query);
        
        if(rs.next() == false) {
            System.out.println("Customer not found. Please enter valid Username!!");
        }
        // else {
        //     if(!rs.getString(3).equals(passwd)) {
        //         System.out.println("Wrong username or password. Please try again");
        //     }
        //     else{

        //         query = "select customer.cusid, customer.username, customer.name, customer.age, customer.gender, customer.aadharno, customer.phno, account.accno, account.balance, account.passwd from customer, account where customer.cusid = account.cusid and username = '"+username+"'";
        //         rs = st.executeQuery(query);
        //         rs.next();


        //         customerobj = new Customer(
        //         Integer.parseInt(rs.getString(1)),
        //         rs.getString(2),
        //         rs.getString(3),
        //         Integer.parseInt(rs.getString(4)),
        //         rs.getString(5),
        //         Integer.parseInt(rs.getString(6)),
        //         Integer.parseInt(rs.getString(7)),
        //         Integer.parseInt(rs.getString(8)),
        //         Integer.parseInt(rs.getString(9)),
        //         rs.getString(10)
        //         );

        //         return customerobj;
        //     }
        // }
        else {
            String accpasswd = rs.getString(3);
            //System.out.println(accpasswd); System.out.println(passwd);
            if(accpasswd.equals(passwd)) {
                query = "select customer.cusid, customer.username, customer.name, customer.age, customer.gender, customer.aadharno, customer.phno, account.accno, account.balance, account.passwd from customer, account where customer.cusid = account.cusid and username = '"+username+"'";
                rs = st.executeQuery(query);
                rs.next();


                customerobj = new Customer(
                Integer.parseInt(rs.getString(1)),
                rs.getString(2),
                rs.getString(3),
                Integer.parseInt(rs.getString(4)),
                rs.getString(5),
                Integer.parseInt(rs.getString(6)),
                Integer.parseInt(rs.getString(7)),
                Integer.parseInt(rs.getString(8)),
                Integer.parseInt(rs.getString(9)),
                rs.getString(10)
                );

                return customerobj;

            }
            else {
                System.out.println("wrong credentials pls try again");
            }
        }


        return null;
    }

    private static void operations(Customer customerobj) throws Exception {
        
        int choice;

        System.out.println();
        System.out.println();
        System.out.println("welcome " +customerobj.name+ " "+ customerobj.cusid + " acc no "+customerobj.acc.accno);

        while(true) {
            System.out.println("press ");
            System.out.println("1. deposit");
            System.out.println("2. withdrawal");
            System.out.println("3. transfer");
            System.out.println("4. view balance");
            System.out.println("5. view transactions");
            System.out.println("6. password reset");
            System.out.println("7. exit");
            
            choice = sc.nextInt();
            int amt;

            if(choice == 1 ) {
                // deposit code

                
                System.out.println("enter amoutn");
                amt = sc.nextInt();

                customerobj.deposit(amt);
            }
            else if(choice == 2) {
                // withdrawal code

                
                System.out.println("enter amount");
                amt = sc.nextInt();
                customerobj.withdraw(amt);
            }
            else if(choice == 3) {
                // transfer code
                System.out.println("enter amount");
                amt = sc.nextInt();
                System.out.println("enter username");
                String username = sc.next();
                customerobj.transfer(username, amt);
            }

            else if(choice == 4) {
                // view balance code
                customerobj.getBalance();
            }
            else if(choice == 5) {
                // view transaction code
                customerobj.viewTransaction();
            }
            else if(choice == 6) {
                // password reset code
                customerobj.passwdreset();
            }
            else if(choice == 7) {
                // break;
                break;
            }
            else {
                System.out.println("Please enter valid input");
            }
        }

    }
    
}