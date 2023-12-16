<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.io.*, java.net.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>Create New Customer</title>
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
       
    <h1>Create New Customer</h1>
    <form action="CreateCustomerServlet" method="post" onsubmit="showPopup()">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required><br>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required><br>

        <label for="street">Street:</label>
        <input type="text" id="street" name="street"><br>

        <label for="address">Address:</label>
        <input type="text" id="address" name="address"><br>

        <label for="city">City:</label>
        <input type="text" id="city" name="city"><br>

        <label for="state">State:</label>
        <input type="text" id="state" name="state"><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email"><br>

        <label for="phone">Phone:</label>
        <input type="tel" id="phone" name="phone"><br>
        <input type="submit" value="Create Customer">
    </form>

    <div class="popup" id="successPopup">
        Customer added successfully!
    </div>

    <div class="links">
        <a href="CustomerListServlet">Show Customer List</a>
        <a href="LogoutServlet">Logout</a>
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
