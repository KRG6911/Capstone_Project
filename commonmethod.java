package ReuseMethod;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class commonmethod {
    public static String readPropertyFile(){

        String aadharNumber = null;
        try {
            // Load the properties file
            FileReader reader = new FileReader("C:\\Users\\girishkr\\IdeaProjects\\API_Training\\Capstone_Project\\Addhar.properties");
            Properties properties = new Properties();
            properties.load(reader);

            aadharNumber = properties.getProperty("Aadhar");

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aadharNumber;
    }
    public static List<String> getAadharDetailsFromDB(String aadharNo) {
        Connection con = null;

        List<String> detailsFromDb = new ArrayList<>();
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Welcome@9999");
            if (con != null) {
                System.out.println("Database server is connected to get the data from database");
            }

            Statement statement = con.createStatement();
            statement.execute("use Bank_Account");

            String selectQuery = "SELECT * FROM Aadhar_Record WHERE Aadhar_Number = ?";
            PreparedStatement ps = con.prepareStatement(selectQuery);
            ps.setString(1,aadharNo);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                detailsFromDb.add(resultSet.getString("Fname"));
                detailsFromDb.add(resultSet.getString("Lname"));
                detailsFromDb.add(resultSet.getString("Aadhar_Number"));
                detailsFromDb.add(resultSet.getString("Address"));
                detailsFromDb.add(resultSet.getString("Phone"));

            }
        } catch (Exception e) {
            System.out.println("Exception is " + e.getMessage());
        }
        return detailsFromDb;
    }
    public String createJsonBody(String firstName,String lastName,String AadharNumber,String Address,String PhoneNumber) {
        String body = "{\n" +
                "    \"Fname\": \""+firstName+"\",\n" +
                "    \"Lname\": \""+lastName+"\",\n" +
                "    \"Aadhar_No\": \""+AadharNumber+"\",\n" +
                "    \"Address\": \""+Address+"\",\n" +
                "    \"Phone\": \""+PhoneNumber+"\",\n" +
                "    \"AccountId\": \"123456789\"\n" +
                "}";
        return body;
    }
}


