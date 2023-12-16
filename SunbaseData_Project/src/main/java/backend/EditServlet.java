package backend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import backend.Customer;
import java.util.List;
@SuppressWarnings("serial")
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";
        String token = (String) request.getSession().getAttribute("access_token"); // Replace with your actual token
        String uuidToUpdate = request.getParameter("uuid");
        String first_name = request.getParameter("firstName");
        String last_name = request.getParameter("lastName");
        String street = request.getParameter("street");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Prepare the JSON payload for updating customer details
        String payload = "{"
                + "\"first_name\": \"" + first_name + "\","
                + "\"last_name\": \"" + last_name + "\","
                + "\"street\": \"" + street + "\","
                + "\"address\": \"" + address + "\","
                + "\"city\": \"" + city + "\","
                + "\"state\": \"" + state + "\","
                + "\"email\": \"" + email + "\","
                + "\"phone\": \"" + phone + "\""
                + "}";
        
        try {
            URL url = new URL(apiUrl + "?cmd=update&uuid=" + uuidToUpdate);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output and send the JSON payload
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(payload);
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder responseBody = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                reader.close();

                System.out.println("Response:");
                System.out.println(responseBody.toString());
                
                List<Customer> customerList = (List<Customer>) request.getSession().getAttribute("customerList");
                if (customerList != null) {
                    for (Customer customer : customerList) {
                        if (customer.getUuid().equals(uuidToUpdate)) {
                            // Update customer details
                            customer.setFirstName(first_name);
                            customer.setLastName(last_name);
                            customer.setStreet(street);
                            customer.setAddress(address);
                            customer.setCity(city);
                            customer.setState(state);
                            customer.setEmail(email);
                            customer.setPhone(phone);
                            break;
                        }
                    }
                    // Update the customerList in session
                    request.getSession().setAttribute("customerList", customerList);
                }
                // Redirect to a success page or perform other actions
                response.sendRedirect("CustomerList.jsp");
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                StringBuilder errorResponse = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
                reader.close();

                System.out.println("Error Response:");
                System.out.println(errorResponse.toString());
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

