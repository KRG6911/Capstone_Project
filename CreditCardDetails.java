package carddetails;

import database_creditcard.DataBaseCreditCardDetails;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static commom.method.ReadDataFromExcel.createJsonBody;
import static commom.method.ReadDataFromExcel.readingXlFileData;
import static io.restassured.RestAssured.given;

public class CreditCardDetails {

    @Parameters({"url"})
    @Test(priority = 0)
    public void GetAllCreditCardDetails(String url) {
        List<String> creditCardNumbers = readingXlFileData();
        validateCardDetailsWithDB(url, creditCardNumbers);
    }

    public void validateCardDetailsWithDB(String url, List<String> creditCardNumbers) {

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "Akshu@8147");
            if (con != null) {
                System.out.println("Database server is connected");
            }

            Statement statement = con.createStatement();
            statement.execute("use CreditCard_Details");

            String selectQuery = "SELECT cc.Name, cc.Year, cc.CreditCardNumber, cc.CreditLimit, cc.ExpDate, cc.CardType, pn.PanNumber " +
                    "FROM creditcard_details.credit_cards cc " +
                    "JOIN Card_With_PanNumber pn ON cc.CreditCardNumber = pn.CardNumber " +
                    "WHERE cc.CreditCardNumber = ?";

            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);

            for (String cardNumber : creditCardNumbers) {
                preparedStatement.setString(1, cardNumber);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (!resultSet.next()) {
                        System.out.println("Credit card details not found: " + cardNumber);
                    }

                    String name = resultSet.getString("Name");
                    int year = resultSet.getInt("Year");
                    String creditcardNo = resultSet.getString("CreditCardNumber");
                    double creditLimit = resultSet.getDouble("CreditLimit");
                    String expdate = resultSet.getString("ExpDate");
                    String cardType = resultSet.getString("CardType");
                    String panNumber = resultSet.getString("PanNumber");

                    System.out.println("Retrieved details from database: " + "Name: " + name + ", " + "Year: " + year + ", " + "Credit Card Number: " + creditcardNo + ", " +
                            "Credit Limit: " + creditLimit + ", " + "EXP Date: " + expdate + ", " + "Card Type: " + cardType + ", " + "PAN Number: " + panNumber);

                    postCallRequestToCreateCreditCard(url, name, year, creditcardNo, creditLimit, expdate, cardType, panNumber);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception is " + e.getMessage());
        }
    }

    public void postCallRequestToCreateCreditCard(String url, String name, int year, String creditCardNumber, double limit, String expDate, String cardType, String panNumber) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(createJsonBody(name, year, creditCardNumber, limit, expDate, cardType))
                .when()
                .post(url);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        System.out.println("Status code:" + statuscode);
        System.out.println("Response from API:" + responseBody);


        Map<String, Object> responseData = response.jsonPath().getMap("data");

        compareDataResponseWithDatabase(responseData,name, year, creditCardNumber, limit, expDate, cardType, panNumber);
    }

    private void compareDataResponseWithDatabase(Map<String, Object> responseData,String cardHoldername,String name, int year, String creditCardNumber, double limit, String expDate, String cardType, String panNumber) {
        try {
            Assert.assertEquals(responseData.get("year"), year);
            Assert.assertEquals(responseData.get("Credit Card Number"), creditCardNumber);
            double actualLimit = Double.parseDouble((String) responseData.get("Limit"));
            Assert.assertEquals(actualLimit, limit, 0.001);
            Assert.assertEquals(responseData.get("EXP Date"), expDate);
            Assert.assertEquals(responseData.get("Card Type"), cardType);

        } catch (AssertionError e) {
            logger.fail(e.getMessage());
        }

        validateResponseCreditCardNumberMappedToPanInDatabase(creditCardNumber);
    }
	
    public void validateResponseCreditCardNumberMappedToPanInDatabase(String cardNumber){
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/CreditCard_Details", "root", "Akshu@8147");

            Statement statement = con.createStatement();
            statement.execute("use CreditCard_Details");

            String selectQuery = "SELECT * FROM Card_With_PanNumber WHERE CardNumber = ?";
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            preparedStatement.setString(1, cardNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String panNumber = resultSet.getString("PanNumber");

                System.out.println("card number mappint to pan card");
      
            } else {
                System.out.println("No PAN Card mapping found:"+cardNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
