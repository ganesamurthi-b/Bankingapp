/* 
 * JDBC Steps
 * import java.sql
 * load and register the driver --> "com.mysql.jdbc.Driver"
 * create Connection
 * create a Statement
 * execute the query
 * process the results
 * close()()()
 */


package pack2;
import java.sql.*;
import java.util.*;
import java.io.*;



public class application {


    static {
        // try {

        //     Class.forName("Adminmain");
        //     Class.forName("Bank");
        //     Class.forName("Employee");
        //     Class.forName("Employeemain");
        //     Class.forName("Admin");
        // }
        // catch(Exception e) {
        //     e.printStackTrace();
        // }

    }

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {

  /*      String url = "jdbc:mysql://localhost:3306/bank";

        String uname = "root"; String passwd = null;
        //Class.forName("com.mysql.jdbc.Driver");

        String query = "select * from customer";

        Connection con = DriverManager.getConnection(url, uname, passwd);

        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(query);

        while(rs.next()) {
            System.out.println(rs.getInt("cusid") + "   " + rs.getString("name") + "   "+rs.getInt("age") +
             "   "+rs.getString("gender") + "   " + rs.getInt("aadharno"));
        }


        System.out.println();
        System.out.println();
        System.out.println();


        query = "select customer.cusid, customer.name, customer.age, account.accno, account.balance from customer,account where customer.cusid = account.cusid";

        rs = st.executeQuery(query);

        while(rs.next()) {
            System.out.println(
                rs.getInt("cusid")+"   "+
                rs.getString("customer.name")+"   "+
                rs.getInt("customer.age")+"   "+
                rs.getInt("account.accno") +"   "+
                rs.getInt("account.balance")
            );

        }
        */
        
        System.out.println();
        System.out.println();
        System.out.println();
        
        System.out.println("Welcome to MIEUPROBANK");
        System.out.println();

        int choice;

        while(true) {
            System.out.println("Press");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Customer");
            System.out.println("4. Exit");

            choice = sc.nextInt();
            
            if(choice == 1) {
                Adminmain.go();
                
            }
            else if(choice == 2) {
                Employeemain.go();

            } 
            else if(choice == 3) {
                Customermain.go();

            }
            else if(choice == 4) {

                // Customermain.writecustomers();
                // Bank.writestaticinitializers();
                // break;
                Bank.updatestaticvariables();
                break;

            }
            else{
                System.out.println("Press 4 to exit");
                System.out.println("Please enter valid input");
            }
        }
    
    }
}