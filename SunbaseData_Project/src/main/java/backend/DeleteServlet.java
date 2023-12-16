package backend;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";
        String token = (String) request.getSession().getAttribute("access_token"); // Replace with your actual token
        String uuidToDelete = request.getParameter("uuid");
        System.out.println("UUID:" + uuidToDelete);

        try {
            URL url = new URL(apiUrl + "?cmd=delete&uuid=" + uuidToDelete);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Assuming your CustomerList is updated in the session
                @SuppressWarnings("unchecked")
                List<Customer> updatedCustomerList = (List<Customer>) request.getSession().getAttribute("customerList");
                // Remove the deleted customer from the list
                updatedCustomerList.removeIf(customer -> customer.getUuid().equals(uuidToDelete));

                // Redirect to customerList.jsp with the updated list of customers
                request.getSession().setAttribute("customerList", updatedCustomerList);
                response.sendRedirect("CustomerList.jsp");
            } else {
                // Handle error or redirection to an error page
                System.out.println("Response Code:"+ responseCode);
                System.out.println("Error occured while deletion");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
