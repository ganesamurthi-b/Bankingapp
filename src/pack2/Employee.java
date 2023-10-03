package pack2;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Employee {

    int empid;
    String empname;
    String passwd;
    String role = "e";

    static Connection con;
    static Statement st;
    static ResultSet rs;
    static Scanner sc = new Scanner(System.in);

    static {
        try {
            con = DriverManager.getConnection(Bank.url, Bank.user, Bank.passwd);
            st = con.createStatement();
            String query = "select cusidgen, accidgen from initializers";
            rs = st.executeQuery(query);
            rs.next();
            cusidgen = Integer.parseInt(rs.getString(1));
            accidgen = Integer.parseInt(rs.getString(2));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
    }

    Employee(int empid, String empname, String passwd, String role) {
        this.empid = empid;
        this.empname = empname;
        this.passwd = passwd;
        this.role = role;
    }

    

    public static int cusidgen;
    public static int accidgen;

    public static int cusidgenfunc() {
        cusidgen += 1;
        return cusidgen;
    }

    public static int accidgenfunc() {
        accidgen += 1;
        return accidgen;
    }




    public void createcustomer() throws Exception {
        String username, name, gender, passwd, query;
        int cusid, age, aadharno, phno, balance, accid;

        while(true) {
            System.out.println("\n\n\nPlease enter a new username for your account");

            username = sc.next();

            
            query = "select username from customer where username='"+username+"'";

            rs = st.executeQuery(query);

            if(rs.next() == false) {
                System.out.println("\n Name");
                name = sc.next();
                System.out.println("age");
                age = sc.nextInt();
                System.out.println("gender");
                gender = sc.next();
                System.out.println("aadharno");
                aadharno = sc.nextInt();


                System.out.println("phno");
                phno = sc.nextInt();
                System.out.println("enter password for your account");
                passwd = sc.next();
                System.out.println("enter initial balance");
                balance = sc.nextInt();
                cusid = Employee.cusidgenfunc();
                accid = Employee.accidgenfunc();

                query = "insert into customer values ("+cusid+", '"+username+"', '"+name+"',"+age+",'"+gender+"',"+aadharno+","+phno+")";

                st.executeUpdate(query);

                query = "insert into account values ("+accid+", "+balance+",'MPBANK0000', '"+passwd+"',"+cusid+")";

                st.executeUpdate(query);

                System.out.println("Customer Created with customer id "+cusid+" Account no "+accid);
                break;

                
            }
            else {
                System.out.println("User name already exists ");
            }

        }

    }

    public void accountdetails() throws Exception {

        int accno;

        System.out.println("please enter account number");
        accno = sc.nextInt();
        String query = "select * from customer, account, where customer.cusid = account.cusid and account.accno = "+accno;
        rs = st.executeQuery(query);

        if(rs.next() == false) {
            System.out.println("please enter valid accno ");
            return;
        }
        int i=1;
        while(i<=12) {
            System.out.println(rs.getString(i));
        }
    }
    
}