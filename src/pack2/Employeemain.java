package pack2;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Employeemain {

    static Scanner sc = new Scanner(System.in);
    static Connection con;
    static Statement st;
    static ResultSet rs;

    static {
        try{
            con = DriverManager.getConnection(Bank.url, Bank.user, Bank.passwd);
            st = con.createStatement();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void go() throws Exception {


        Employee empobj;
        int choice;

        while(true) {

            System.out.println("Press 1 to continue or 0 to exit");

            choice = sc.nextInt();
            if(choice == 0) break;

            empobj = authenticate();

            if(empobj == null) continue;

            else 
                operations(empobj);

        }



    }


    private static Employee authenticate() throws Exception {

        int empid;
        String emppasswd;
        //Admin adminref;

        System.out.println();
        System.out.println();
        System.out.println("Enter Employee id ");
        empid = sc.nextInt();
        System.out.println("Enter Employee password");
        emppasswd = sc.next();

        Connection con = DriverManager.getConnection(Bank.url, Bank.user, Bank.passwd);
        Statement st = con.createStatement();
        String query = "select * from employee where empid = "+empid;
        ResultSet rs = st.executeQuery(query);

        if(rs.next() == false) {
            System.out.println("Please enter valid credentials");
        }
        else if(!rs.getString("role").contains("e")) {
            System.out.println("You dont have Employee access! Please contact admin");
        }
        else if(!rs.getString("passwd").equals(emppasswd)) {
            System.out.println("Incorrect Password");
        }
        else {
            int id = Integer.parseInt(rs.getString("empid"));
            String name = rs.getString("empname");
            String passwd = rs.getString("passwd");
            String role = rs.getString("role");
            Employee emp = new Admin(id, name, passwd, role);
            return emp;

        }
        
        return null;



    }


    public static void operations(Employee empobj) throws Exception {
        int choice;

        System.out.println();
        System.out.println();
        System.out.println("welcome " +empobj.empname+ " "+ empobj.empid);
        boolean accountdetails, bankdetails, accountcreation, admincreation, employeecreation;

        Connection con = DriverManager.getConnection(Bank.url, Bank.user, Bank.passwd);

        Statement st = con.createStatement();

        String query = "select access.accountdetails, access.bankdetails, access.accountcreation, access.admincreation, access.employeecreation from access, employee where employee.role = access.role and employee.empid = "+empobj.empid;          

        ResultSet rs = st.executeQuery(query);

        rs.next();

        accountdetails = rs.getBoolean(1);
        bankdetails = rs.getBoolean(2);
        accountcreation = rs.getBoolean(3);
        admincreation = rs.getBoolean(4);
        employeecreation = rs.getBoolean(5);


        


        while(true) {
            System.out.println("press ");
            System.out.println("1. Account Details");
            System.out.println("2. Create customer acc");
           
            System.out.println("3. Exit");

            choice = sc.nextInt();

            if(choice == 1) {
                //Customer Account Details
                if(!accountdetails) {
                    System.out.println("you dont have access");
                    return;
                }
                empobj.accountdetails();

            }

            else if(choice == 2) {
                if(!accountcreation){
                    System.out.println("you dont have access"); return;
                }
                // Create customer acc
                empobj.createcustomer();

                
            }
            else if(choice == 3) {
                break;
            }
            else {
                System.out.println("please enter valid input");
                continue;
            }


        }



    }

    



    
}
