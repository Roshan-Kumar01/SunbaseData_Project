<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List, backend.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Customer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            text-align: center;
        }
        form {
            max-width: 500px;
            margin: 0 auto;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="email"],
        input[type="tel"] {
            width: calc(100% - 12px);
            padding: 6px;
            margin-bottom: 10px;
            border-radius: 4px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .links {
            position: absolute;
            top: 10px;
            right: 10px;
        }
        .links a {
            margin-left: 10px;
            text-decoration: none;
            color: #333;
            font-weight: bold;
            padding: 8px 15px;
            border-radius: 4px;
            background-color: #ccc;
        }
        .links a:hover {
            color: #000;
            background-color: #ddd;
        }
        .popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            z-index: 1;
        }
    </style>
</head>
<body>
    <h1>Update Customer</h1>
    <form action="EditServlet" method="post" onsubmit="showPopup()">
        <% String uuid = request.getParameter("uuid"); %>
        <% List<Customer> customerList = (List<Customer>) session.getAttribute("customerList"); %>
        <% Customer customer = null; %>
        <% if (customerList != null) {
            for (Customer c : customerList) {
                if (c.getUuid().equals(uuid)) {
                    customer = c;
                    break;
                }
            }
        } %>
        <% if (customer != null) { %>
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" required value="<%= customer.getFirstName() %>"><br>

            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" required value="<%= customer.getLastName() %>"><br>

            <label for="street">Street:</label>
            <input type="text" id="street" name="street" value="<%= customer.getStreet() %>"><br>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="<%= customer.getAddress() %>"><br>

            <label for="city">City:</label>
            <input type="text" id="city" name="city" value="<%= customer.getCity() %>"><br>

            <label for="state">State:</label>
            <input type="text" id="state" name="state" value="<%= customer.getState() %>"><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= customer.getEmail() %>"><br>

            <label for="phone">Phone:</label>
            <input type="tel" id="phone" name="phone" value="<%= customer.getPhone() %>"><br>

            <input type="hidden" name="uuid" value="<%= customer.getUuid() %>">
            <input type="submit" value="Update Customer">
        <% } else { %>
            <p>Customer not found!</p>
        <% } %>
    </form>
    <div class="popup" id="successPopup">
        Customer updated successfully!
    </div>

    <script>
        function showPopup() {
            document.getElementById("successPopup").style.display = "block";
            setTimeout(function () {
                document.getElementById("successPopup").style.display = "none";
            }, 1000); // Display for 1 seconds, you can adjust as needed
        }
    </script>
</body>
</html>
