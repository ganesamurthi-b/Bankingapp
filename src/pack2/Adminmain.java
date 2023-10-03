package pack2;
import java.util.*;
import java.io.*;
import java.sql.*;





public class Adminmain {

    static Scanner sc = new Scanner(System.in);





    public static void go() throws Exception {


        Admin adminobj;
        int choice;

        while(true) {

            System.out.println("Press 1 to continue or 0 to exit");

            choice = sc.nextInt();
            if(choice == 0) break;

            adminobj = authenticate();

            if(adminobj == null) continue;

            else 
                operations(adminobj);

        }



    }

    private static Admin authenticate() throws Exception {

        int adminid;
        String adminpasswd;
        //Admin adminref;

        System.out.println();
        System.out.println();
        System.out.println("enter admin id ");
        adminid = sc.nextInt();
        System.out.println("enter admin password");
        adminpasswd = sc.next();

        Connection con = DriverManager.getConnection(Bank.url, Bank.user, Bank.passwd);
        Statement st = con.createStatement();
        String query = "select * from employee where empid = "+adminid;
        ResultSet rs = st.executeQuery(query);

        if(rs.next() == false) {
            System.out.println("Please enter valid credentials");
        }
        else if(!rs.getString("role").contains("a")) {
            System.out.println("You dont have Admin access! Please contact admin");
        }
        else if(!rs.getString("passwd").equals(adminpasswd)) {
            System.out.println("Incorrect Password");
        }
        else {
            int id = Integer.parseInt(rs.getString("empid"));
            String name = rs.getString("empname");
            String passwd = rs.getString("passwd");
            String role = rs.getString("role");
            Admin admin = new Admin(id, name, passwd, role);
            return admin;
            
        }
        
        return null;



    }


    public static void operations(Admin adminobj) throws Exception {
        int choice;

        System.out.println();
        System.out.println();
        System.out.println("welcome " +adminobj.empname+ " "+ adminobj.empid);
        boolean accountdetails, bankdetails, accountcreation, admincreation, employeecreation;

        Connection con = DriverManager.getConnection(Bank.url, Bank.user, Bank.passwd);

        Statement st = con.createStatement();

        String query = "select access.accountdetails, access.bankdetails, access.accountcreation, access.admincreation, access.employeecreation, access.customerpasswordreset from access, employee where employee.role = access.role and employee.empid = "+adminobj.empid;          

        ResultSet rs = st.executeQuery(query);

        rs.next();

        accountdetails = rs.getBoolean(1);
        bankdetails = rs.getBoolean(2);
        accountcreation = rs.getBoolean(3);
        admincreation = rs.getBoolean(4);
        employeecreation = rs.getBoolean(5);
        boolean custpasswdreset = rs.getBoolean(6);


        


        while(true) {
            System.out.println("press ");
            System.out.println("1. Account Details");
            System.out.println("2. Bank Details");
            System.out.println("3. Create customer acc");
            System.out.println("4. admin or emp acc creation");
            System.out.println("5. Forgot passwd");
            System.out.println("6. Exit");
            choice = sc.nextInt();

            if(choice == 1) {
                //Customer Account Details
                if(!accountdetails) {
                    System.out.println("you dont have access");
                    return;
                }
                adminobj.accountdetails();

            }
            else if(choice == 2) {
            /*     int accno;

                Customer customerobj;

                // view customer code;
                System.out.println("enter account details");
                accno = sc.nextInt();

                if(Customermain.accountmap.containsKey(accno)) {
                    customerobj = Customermain.accountmap.get(accno);
                    // System.out.println("Name "+customerobj.name);
                    // System.out.println("Acc no" + customerobj.acc.accno);
                    // System.out.println("Customer id " + customerobj.cusid );
                    // System.out.println(customerobj.acc.balance);
                    // System.out.println(customerobj.transactionlist);
                    adminobj.viewcustomer(customerobj);
                }
                else {
                    System.out.println("enter valid accno or press 3 to exit");
                } */

                //Bank Details
                if(!bankdetails){
                    System.out.println("you dont have access");
                }

                query = "select amount from bank";
                rs =  st.executeQuery(query);
                rs.next();
                System.out.println("Amount is "+rs.getString(1));

                
            } 

            else if(choice == 3) {
                if(!accountcreation){
                    System.out.println("you dont have access"); return;
                }
                // Create customer acc
                adminobj.createcustomer();

                
            }
            else if(choice == 4 ) {
                // Admin acc create
                if(!admincreation){
                    System.out.println("you dont have access"); return;
                }

                String role;
                System.out.println("Enter role \n For Employee press e and for admin press a \n For specific roles press any char");
                role = sc.next();
                adminobj.createEmployee(role);

            }

            else if(choice == 5) {

                //forgot password code
                if(!custpasswdreset){
                    System.out.println("you dont have access"); return;
                }
                adminobj.customerpasswordresest();


            }
            else if(choice == 6) {
                break;
            }

            
            else {
                System.out.println("please enter valid input");
                continue;
            }


        }



    }
    
}




