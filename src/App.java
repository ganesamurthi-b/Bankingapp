import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World! from app");

        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String passwd = null;
        String query = "select * from dummy";


        Connection con = DriverManager.getConnection(url,user,passwd);
        Statement st = con.createStatement();
        ResultSet rs= st.executeQuery(query);
        System.out.println(rs.next());

        System.out.println(rs.getBoolean(1) );


        // query = "select name from customer where cusid = 1000";
        // rs = st.executeQuery(query);
        // System.out.println(rs.next());

    }
}
