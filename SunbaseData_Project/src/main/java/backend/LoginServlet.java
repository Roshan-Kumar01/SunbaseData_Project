package backend;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String loginId = request.getParameter("login_id");
        String password = request.getParameter("password");

        // Perform authentication logic, obtain the token, and set it in the session
        String token = authenticateUser(loginId, password);
        if (token != null) {
            request.getSession().setAttribute("access_token", token);
            System.out.println("Token:"+request.getSession().getAttribute("access_token"));
            request.getRequestDispatcher("/CreateCustomer.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        }
    }

    private String authenticateUser(String loginId, String password) throws IOException {
        // API Endpoint URL
        String authURL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

        // JSON request body
        String requestBody = "{\"login_id\" : \"" + loginId + "\", \"password\" :\"" + password + "\"}";

        // Create URL object
        URL url = new URL(authURL);

        // Create connection object
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method
        connection.setRequestMethod("POST");

        // Set request headers
        connection.setRequestProperty("Content-Type", "application/json");

        // Enable output and send request body
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get response code
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Read response
        String bearerToken = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Response: " + response.toString());
            
            bearerToken = extractTokenFromResponse(response.toString());
        }

        // Close connection
        connection.disconnect();

        return bearerToken; // Return the bearer token obtained from the API call
    }
    private String extractTokenFromResponse(String jsonResponse) {
        // Parse JSON response to extract the token
        return jsonResponse != null ? jsonResponse.replace("{\"access_token\":\"", "").replace("\"}", "") : null;
    }
}
