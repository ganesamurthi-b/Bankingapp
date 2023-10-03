package pack2;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Admin extends Employee {

    String role = "a";

    static Connection con;
    static Statement st;
    static ResultSet rs;
    static Scanner sc = new Scanner(System.in);

    static {
        try {
            con = DriverManager.getConnection(Bank.url, Bank.user, Bank.passwd);
            st = con.createStatement();
            String query = "select empidgen from initializers";
            rs = st.executeQuery(query);
            rs.next();
            empidgen = Integer.parseInt(rs.getString(1));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        
    }

    public static int empidgen;



    public static int empidgenfunc() {
        empidgen += 1;
        return empidgen;
    }


    Admin(int empid, String name, String passwd, String role) {
        super(empid, name, passwd, "e");
        this.role = "a";
    }

    // public void accountdetails() throws Exception {

    //     int accno;

    //     System.out.println("please enter account number");
    //     accno = sc.nextInt();
    //     String query = "select * from customer, account, where customer.cusid = account.cusid and account.accno = "+accno;
    //     rs = st.executeQuery(query);

    //     if(rs.next() == false) {
    //         System.out.println("please enter valid accno ");
    //         return;
    //     }
    //     int i=1;
    //     while(i<=12) {
    //         System.out.println(rs.getString(i));
    //     }
    // }

    public void createEmployee(String role) throws Exception {

        int empid;
        String empname;
        String passwd;

        empid = Admin.empidgenfunc();

        System.out.println("\n\nEnter name");
        empname = sc.next();
        System.out.println("\nEnter passwd");
        passwd = sc.next();

        String query = "insert into employee values ("+empid+", '"+empname+"', '"+passwd+"', '"+role+"')";
        st.executeUpdate(query);

        if(role.equals("e")) 
            System.out.println("Employee created successfully");
        else 
            System.out.println("\n Admin created successfully");

    }

    // public void createEmployee() throws Exception {
    //     int empid;
    //     String empname;
    //     String passwd;
    //     String role = "e";

    //     empid = Admin.empidgenfunc();

    //     System.out.println("\n\\n Enter name");
    //     empname = sc.ne
    // }

    public void customerpasswordresest() throws Exception {

        System.out.println("Enter customer username");
        String username = sc.next();

        String query = "select account.accno from account, customer where customer.cusid = account.cusid and customer.username = '"+username+"';";

        rs = st.executeQuery(query);
        

        if(rs.next() == false ) {
            System.out.println("No customer found");
            return;
        }
        else {
            int accno = Integer.parseInt(rs.getString(1));
            String newpasswd;
            System.out.println("Enter new password");
            newpasswd = sc.next();
            query = "update account set passwd = '"+newpasswd+"'"+"where accno = "+accno+";";
            st.executeUpdate(query);
            System.out.println("password changed successfully");
        }
    }


    
    
}