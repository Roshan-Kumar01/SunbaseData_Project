package backend;

import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/CreateCustomerServlet")
public class CreateCustomerServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // API Endpoint URL
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";
        
        // Bearer token obtained from the authentication API
        String token = (String)request.getSession().getAttribute("access_token"); // Replace with your actual token
        String first_name= request.getParameter("firstName");
        String last_name= request.getParameter("lastName");
        String street= request.getParameter("street");
        String address= request.getParameter("address");
        String city= request.getParameter("city");
        String state= request.getParameter("state");
        String email= request.getParameter("email");
        String phone= request.getParameter("phone");
        try {
            // Create URL object
            URL url = new URL(apiUrl);
            
            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Create the request body
            String requestBody = "{\"first_name\":\"" + first_name + "\",\"last_name\":\"" + last_name + "\"," +
                    "\"street\":\"" + street + "\",\"address\":\"" + address + "\"," +
                    "\"city\":\"" + city + "\",\"state\":\"" + state + "\",\"email\":\"" + email + "\"," +
                    "\"phone\":\"" + phone + "\"}";
            
            // Write request body to the connection
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CreateCustomer.jsp");
            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            
            // Process response based on status code
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Successfully Created");
                
                dispatcher.forward(request, response);
                // Further processing for success scenario
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
            	System.out.println("First Name or Last Name is missing");
                 dispatcher.forward(request, response);
                // Further processing for failure scenario
            } else {
                // Handle other response codes
                System.out.println("Unexpected response code: " + responseCode);
            }
            
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
