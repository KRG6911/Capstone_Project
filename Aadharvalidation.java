package Demo;

import ReuseMethod.commonmethod;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Aadharvalidation {
    commonmethod m;
    @BeforeClass
    public void setUp(){
        m = new commonmethod();

    }

    @Parameters({"url"})
    @Test(priority = 1)
   public void validatingaadhardetails(String url) {

       String Aadhar_number = null;
       List<String> aadharDetailsFromDB = null;
       boolean matchFound = false;
              Aadhar_number = m.readPropertyFile();
       aadharDetailsFromDB=m.getAadharDetailsFromDB(Aadhar_number);

       matchFound = aadharDetailsFromDB.contains(Aadhar_number);
       if (matchFound){
           System.out.println("Aadhar number matches");
           createBankAccount(url, aadharDetailsFromDB.get(0),aadharDetailsFromDB.get(1),aadharDetailsFromDB.get(2),aadharDetailsFromDB.get(3),aadharDetailsFromDB.get(4));
       }
       else {
           System.out.println("Aadhar number not matches");
       }
   }
    public void createBankAccount(String url,String firstName,String lastName,String aadharNumber,String address,String phoneNumber) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(m.createJsonBody(firstName, lastName, aadharNumber, address, phoneNumber))
                .when()
                .post(url);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        System.out.println(statuscode);
        System.out.println(responseBody);
        validateResponseAndDBDetails(responseBody,firstName, lastName, aadharNumber, address, phoneNumber);
    }
        public static void validateResponseAndDBDetails(String responseBody,String firstName,String lastName,String aadharNumber,String address,String phoneNumber){
            JSONObject jsonObject = new JSONObject(responseBody);

            String responseFirstName = jsonObject.getString("Fname");
            String responseLastName = jsonObject.getString("Lname");
            String responseAadharNumber = jsonObject.getString("Aadhar_No");
            String responseAddress = jsonObject.getString("Address");
            String responsePhone = jsonObject.getString("Phone");

            Assert.assertEquals(responseFirstName, firstName);
            Assert.assertEquals(responseLastName, lastName);
            Assert.assertEquals(responseAadharNumber, aadharNumber);
            Assert.assertEquals(responseAddress, address);
            Assert.assertEquals(responsePhone, phoneNumber);

    }
}

