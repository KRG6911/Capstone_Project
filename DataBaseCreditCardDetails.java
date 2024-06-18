package database_creditcard;

import com.aventstack.extentreports.ExtentTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBaseCreditCardDetails {
	
    Connection con = null;

    public void createNewDatabase() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Akshu@8147");
         
            Statement statement = con.createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS CreditCard_Details");
            System.out.println("Database created successfully");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createNewTable() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Akshu@8147");
            Statement statement = con.createStatement();
            statement.execute("use CreditCard_Details");

            statement.execute("CREATE TABLE IF NOT EXISTS CreditCard_Details.credit_cards (\n" +
                    "  Name VARCHAR(45) NULL,\n" +
                    "  Year INT,\n" +
                    "  CreditCardNumber VARCHAR(45) NULL,\n" +
                    "  CreditLimit DECIMAL(10,2),\n" +
                    "  ExpDate VARCHAR(45),\n" +
                    "  CardType VARCHAR(20) NULL);");

            System.out.println("Table created successfully.");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertDataIntoTable() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Akshu@8147");
            Statement statement = con.createStatement();
            statement.execute("use CreditCard_Details");

            statement.execute("DELETE FROM CreditCard_Details.credit_cards");

            statement.execute("INSERT INTO CreditCard_Details.credit_cards (Name, Year, CreditCardNumber, CreditLimit, ExpDate, CardType) VALUES\n" +
                    "('Ravi', 2023, '4111 1111 1111 1111', 5000.00, '2025-06-30', 'Visa'),\n" +
                    "('Raju', 2021, '4000 1234 5678 9010', 7500.00, '2024-11-30', 'Visa'),\n" +
                    "('Kumar', 2022, '4012 8888 8888 1881', 10000.00, '2023-09-30', 'Visa'),\n" +
                    "('Dinesh', 2020, '5555 5555 5555 4444', 6000.00, '2025-04-30', 'Master'),\n" +
                    "('Suresh', 2019, '1111 1111 2223 3323', 4000.00, '2024-02-28', 'Master'),\n" +
                    "('Kishore', 2021, '7898 4532 1234 5465', 3000.00, '2023-12-31', 'Master'),\n" +
                    "('Geeta', 2022, '5645 3423 3456 5678', 5000.00, '2026-08-31', 'Master'),\n" +
                    "('Henry', 2023, '1234 5324 4567 7890', 8500.00, '2025-10-31', 'Visa'),\n" +
                    "('Mohit', 2020, '2345 4567 4321 1234', 9000.00, '2024-06-30', 'Visa'),\n" +
                    "('Preetham', 2022, '8900 9008 7876 5432', 11000.00, '2023-11-30', 'Visa'),\n" +
                    "('Suma', 2023, '1234 4567 7654 3452', 7000.00, '2025-07-31', 'Visa'),\n" +
                    "('Meena', 2021, '2345 5678 8900 0098', 6500.00, '2024-01-31', 'Visa'),\n" +
                    "('Meera', 2019, '2344 0009 8800 7890', 5500.00, '2024-03-31', 'Master'),\n" +
                    "('Nayana', 2020, '2900 9008 8009 9007', 8000.00, '2025-09-30', 'Master'),\n" +
                    "('Keerthan', 2022, '9999 8456 6789 2345', 12000.00, '2024-11-30', 'Master'),\n" +
                    "('Pavithra', 2023, '2345 5678 8900 0098', 9500.00, '2025-05-31', 'Master'),\n" +
                    "('Heet', 2021, '1234 4324 3456 6543', 13000.00, '2024-12-31', 'Master'),\n" +
                    "('Abishek', 2020, '1233 3334 3334 3344', 7000.00, '2025-04-30', 'Visa'),\n" +
                    "('Wilson', 2022, '2344 4445 4444 4444', 8500.00, '2023-10-31', 'Visa'),\n" +
                    "('Tina', 2019, '2344 3344 6666 7777', 6000.00, '2024-02-28', 'Visa');");

            System.out.println("data inserted successfully.");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

  
    public void createTableCard_PanNumber() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Akshu@8147");
            if (con != null) {
                System.out.println("Database server is connected---Pan number details");
            }
            Statement statement = con.createStatement();
            statement.execute("use CreditCard_Details");

            //Creating a table
            statement.execute("CREATE TABLE IF NOT EXISTS CreditCard_Details.Card_With_PanNumber (\n" +
                    "  CardNumber VARCHAR(45) NULL,\n" +
                    "  PanNumber VARCHAR(40) NULL);");

            System.out.println("Table-Credit with PanNumber details created successfully.");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertDataIntoPanNumberTable() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Akshu@8147");

            Statement statement = con.createStatement();
            statement.execute("use CreditCard_Details");

            statement.execute("DELETE FROM CreditCard_Details.Card_With_PanNumber");

            statement.execute("INSERT INTO CreditCard_Details.Card_With_PanNumber (CardNumber, PanNumber) VALUES\n" +
                    "('4111 1111 1111 1111', 'DNSPA123456'),\n" +
                    "('4000 1234 5678 9010', 'FREDF123453'),\n" +
                    "('4012 8888 8888 1881', 'YGHJ1234567'),\n" +
                    "('5555 5555 5555 4444', 'FTRE2345679'),\n" +
                    "('1111 1111 2223 3323', 'ERTY3456790'),\n" +
                    "('7898 4532 1234 5465', 'HGFR4567901'),\n" +
                    "('5645 3423 3456 5678', 'RRRT0679012'),\n" +
                    "('1234 5324 4567 7890', 'KJHG9790123'),\n" +
                    "('2345 4567 4321 1234', 'NBGH7901234'),\n" +
                    "('8900 9008 7876 5432', 'HRTY8012345'),\n" +
                    "('1234 4567 7654 3452', 'ERTY7543210'),\n" +
                    "('2345 5678 8900 0098', 'TGFR7654321'),\n" +
                    "('2344 0009 8800 7890', 'HRTY9875432'),\n" +
                    "('2900 9008 8009 9007', 'WERT0987543'),\n" +
                    "('9999 8456 6789 2345', 'BGRF1097654'),\n" +
                    "('2345 5678 8900 0098', 'MJKL2198765'),\n" +
                    "('1234 4324 3456 6543', 'KJUI3109876'),\n" +
                    "('1233 3334 3334 3344', 'WERS4310987'),\n" +
                    "('2344 4445 4444 4444', 'NFRT5321098'),\n" +
                    "('2344 3344 6666 7777', 'WQSD6532109');");
            System.out.println("data inserted successfully.");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
	
	public static void main(String args[]){
		DataBaseCreditCardDetails dc = new DataBaseCreditCardDetails();
		dc.createNewDatabase();
		dc.createNewTable();
		dc.insertDataIntoTable();
		dc.createTableCard_PanNumber();
		dc.insertDataIntoPanNumberTable();
}
