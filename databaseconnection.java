package database;

import org.example.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class databaseconnection {
    Connection con = null;

    public void createNewDatabase() {

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Welcome@9999");
            if (con != null) {
                System.out.println("Database serve connected successfully");
            }
            Statement statement = con.createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS Bank_Account");
            System.out.println("Database created successfully");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createNewTable() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Welcome@9999");
            Statement statement = con.createStatement();
            statement.execute("use Bank_Account");

            //Creating a table
            statement.execute("CREATE TABLE IF NOT EXISTS Bank_Account.Aadhar_Record (\n" +
                    "  Fname VARCHAR(45) NULL,\n" +
                    "  Lname VARCHAR(45) NULL,\n" +
                    "  Aadhar_Number VARCHAR(45) NULL,\n" +
                    "  Address VARCHAR(45) NULL,\n" +
                    "  Phone VARCHAR(45) NULL);");

            System.out.println("Table created successfully.");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertDataIntoTable() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Welcome@9999");
            Statement statement = con.createStatement();

            statement.execute("use Bank_Account");

            statement.execute("DELETE FROM Bank_Account.Aadhar_Record");

            statement.execute("Insert into Aadhar_Record(Fname,Lname,Aadhar_Number,Address,Phone)values ('Ravi','Naik','100987654345','KR Puram Bangalore','8978654421') ,\n" +
                    "('Raju','Bhat','111987654340','KR Puram Bangalore','8978654334'),\n" +
                    "('Ramya','Naik','123987654345','KR Puram Bangalore','8970654421'),\n" +
                    "('Giri','Shanbag','133987654345','KR Puram Bangalore','8979694420'),\n" +
                    "('Soumya','Ambig','234987654345','KR Puram Bangalore','8867865442'),\n" +
                    "('Sridhar','Bhat','112987654378','KR Puram Bangalore','9978654490'),\n" +
                    "('Manju','Ambig','234567898900','KR Puram Bangalore','8876453412'),\n" +
                    "('Meena','Bhat','134566777888','KR Puram Bangalore','8876123451'),\n" +
                    "('Khushi','Ambig','111987654123','KR Puram Bangalore','9087675645'),\n" +
                    "('Jeni','Bhat','134567546341','KR Puram Bangalore','9007645341');");

            System.out.println("Insert the data into a table \n");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        databaseconnection dc = new databaseconnection();
        dc.createNewDatabase();
        dc.createNewTable();
        dc.insertDataIntoTable();
    }

}

