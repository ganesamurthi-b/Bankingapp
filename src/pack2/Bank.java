package pack2;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Bank {
    

    static String url = "jdbc:mysql://localhost:3306/bankapp";
    static String user = "root";
    static String passwd = "gogulraj@1234";
    static Connection con;
    static Statement st;
    static ResultSet rs;

    static {
        try{
            con = DriverManager.getConnection(url, user, passwd);
            st = con.createStatement();
            String query = "select tidgen from initializers";
            rs = st.executeQuery(query);
            rs.next();
            transactionid = Integer.parseInt(rs.getString(1));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    private static int transactionid;

    private static int transactionidfunc() {
        transactionid+=1;
        return transactionid;
    }

    public static void updateBankbalance(int amt, boolean op) throws Exception {
        String query;
        if(op == true ) {
             query = "update bank set amount = amount + "+amt;
        }
        else {
            query  = "update bank set amount = amount - "+amt;
        }
        
        st.executeUpdate(query);
    }


    public static void deposit(Customer obj, int amt) throws Exception {
        int tid = transactionidfunc();
        String query = "update account set balance = balance+"+amt+" where accno = "+obj.accno;
        System.out.println(query);
        int res = st.executeUpdate(query);
        query = "insert into transactions values ("+tid+",0,'deposit',"+obj.accno+","+obj.accno+","+amt+")";
        ///System.out.println(query);
        st.executeUpdate(query);
        query = "update bank set amount = amount +"+amt;
        st.executeUpdate(query);
        Bank.updateBankbalance(amt, true);
        System.out.println("Amount deposited successfully");
    }

    public static void withdraw(Customer obj, int amt) throws Exception {
        int currentamt;
        int tid = transactionidfunc();

        String query = "select balance from account where accno = "+obj.accno;
        rs = st.executeQuery(query);

        rs.next();
        currentamt = Integer.parseInt(rs.getString(1));

        if(currentamt < amt) {
            System.out.println("insufficient funds ");
            return;
        }
        

        query = "update account set balance = balance-"+amt+" where accno = "+obj.accno;
        int res = st.executeUpdate(query);
        query = "insert into transactions values ("+tid+",1,'withdraw',"+obj.accno+","+obj.accno+","+amt+")";
        st.executeUpdate(query);
        query = "update bank set amount = amount +"+amt;
        st.executeUpdate(query);
        Bank.updateBankbalance(currentamt, false);
        System.out.println("Amount withdrawn successfully");
    }

    public static void transfer(Customer obj, String username, int amt) throws Exception {

        int currentamt;
        int tocusid;
        int toaccno;
        int tid = transactionidfunc();

        String query = "select balance from account where accno = "+obj.accno;
        rs = st.executeQuery(query);

        rs.next();
        currentamt = Integer.parseInt(rs.getString(1));

        if(currentamt < amt) {
            System.out.println("insufficient funds ");
            return;
        }
        

        query = "select customer.cusid, account.accno from customer, account where customer.cusid = account.cusid and customer.username = '"+username+"'";

        ResultSet rs = st.executeQuery(query);

        if(rs.next() == false) {
            System.out.println("Please enter valid account details");
            return;
        }
        else {
            tocusid = Integer.parseInt(rs.getString(1));
            toaccno = Integer.parseInt(rs.getString(2));
            query = "update account set balance = balance + "+amt+" where accno = "+toaccno;
            st.executeUpdate(query);
            query = "update account set balance = balance - "+amt+" where accno = "+obj.accno;
            st.executeUpdate(query);

            query = "insert into transactions values ("+tid+",2,'transfer',"+obj.accno+","+toaccno+","+amt+")";
            st.executeUpdate(query);

            System.out.println("Amount "+amt+ " transfered to "+username+" successfully");

        }

    }

    public static void getBalance(Customer obj) throws Exception {

        String query = "select balance from account where accno = "+obj.accno;
        rs = st.executeQuery(query);

        rs.next();

        System.out.println("your balance is " + rs.getString(1));
    } 

    public static void viewTransaction(Customer obj) throws Exception {
        String query = "select transactions.tid, transactions.message, transactions.accno, transactions.to_accno, transactions.amount from transactions, account where account.accno = transactions.accno and transactions.accno = "+obj.accno;

        rs = st.executeQuery(query);

        while(rs.next()) {
            System.out.println(rs.getString(1) +"   "+
            rs.getString(2)+"   "+
            rs.getString(3)+"   "+
            rs.getString(4)+"   "+
            rs.getString(5)+"   ");
        }
    }

    public static void passwdreset(Customer obj, String newpasswd) throws Exception {

        String query = "update account set passwd = '"+newpasswd+"' where accno = "+obj.accno;

        st.executeUpdate(query);

        System.out.println("Password changed successfully");

    }

    public static void updatestaticvariables()throws Exception {

        String query;

        query = "update initializers set empidgen = "+Admin.empidgen;
        st.executeUpdate(query);
        query = "update initializers set cusidgen = "+Employee.cusidgen;
        st.executeUpdate(query);
        query = "update initializers set accidgen = "+Employee.accidgen;
        st.executeUpdate(query);
        query = "update initializers set tidgen = "+Bank.transactionid;
        st.executeUpdate(query);
    }


}