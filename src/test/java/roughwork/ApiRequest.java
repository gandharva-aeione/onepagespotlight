package roughwork;

import com.aeione.ops.generic.TestSetUp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;



public class ApiRequest extends TestSetUp
{
    @Test(priority = 07, enabled = true, alwaysRun = true, description = "Verify get spread sheet data")
   public void getSpreadSheetValue() throws ParseException {
       RestAssured.baseURI = "https://sheets.googleapis.com";
       RequestSpecification request = RestAssured.given();

       Response response = request.get("https://sheets.googleapis.com/v4/spreadsheets/1HRLXHwflJ4y_5J0wDYUc71EuJPHO787it77lTw7Ksec/values/Registration!A2:H2?dateTimeRenderOption=FORMATTED_STRING&majorDimension=ROWS&valueRenderOption=FORMATTED_VALUE&key=AIzaSyD6BIV5VaiRMYvY_rYX9h02xtxyO-wF80s");

       ResponseBody body = response.getBody();


       JSONParser parser = new JSONParser();
       JSONObject json = (JSONObject) parser.parse(body.asString());
       Gson gson = new GsonBuilder().setPrettyPrinting().create();
       String jsonResponse = gson.toJson(json);

       System.out.println(jsonResponse);

   }



}
