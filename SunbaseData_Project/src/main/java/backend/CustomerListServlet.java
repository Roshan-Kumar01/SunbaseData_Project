package backend;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import backend.Customer;
import javax.json.JsonValue;

@SuppressWarnings("serial")
@WebServlet("/CustomerListServlet")
public class CustomerListServlet extends HttpServlet {
 @Override
 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     try {
         String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
         String token = (String) req.getSession().getAttribute("access_token"); // Replace with your actual token

         URL url = new URL(apiUrl);
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();

         // Set request method and headers
         connection.setRequestMethod("GET");
         connection.setRequestProperty("Authorization", "Bearer " + token);

         int responseCode = connection.getResponseCode();
         if (responseCode == HttpURLConnection.HTTP_OK) {
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             String line;
             StringBuilder response = new StringBuilder();

             while ((line = reader.readLine()) != null) {
                 response.append(line);
             }
             reader.close();

             // Process the JSON response
             System.out.println("Response:");
             System.out.println(response.toString());

             // Read JSON response using javax.json
             JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
             JsonArray jsonArray = jsonReader.readArray();
             jsonReader.close();

             // Convert JSON array to list of Customer objects
             List<Customer> customerList = new ArrayList<>();
             for (int i = 0; i < jsonArray.size(); i++) {
                 JsonObject jsonObject = jsonArray.getJsonObject(i);
                 String uuid = jsonObject.containsKey("uuid") && jsonObject.get("uuid").getValueType() == JsonValue.ValueType.STRING
                         ? jsonObject.getString("uuid")
                         : "";
                 Customer customer = new Customer(
                		 uuid,
                         jsonObject.getString("first_name", ""),
                         jsonObject.getString("last_name", ""),
                         jsonObject.getString("street", ""),
                         jsonObject.getString("address", ""),
                         jsonObject.getString("city", ""),
                         jsonObject.getString("state", ""),
                         jsonObject.getString("email", ""),
                         jsonObject.getString("phone", "")
                 );
                 customerList.add(customer);
             }

             // Set customerList as a request attribute
             req.setAttribute("customerList", customerList);
             req.getSession().setAttribute("customerList", customerList);
             // Forward the request to the showCustomer.jsp page
             RequestDispatcher dispatcher = req.getRequestDispatcher("/CustomerList.jsp");
             dispatcher.forward(req, resp);

         } else {
             // Handle error response
             System.out.println("Error: " + responseCode);
         }

         connection.disconnect();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
}
