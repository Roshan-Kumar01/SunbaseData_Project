<%@ page import="java.util.List" %>
<%@ page import="backend.Customer" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Show Customers</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .action-icons {
            display: flex;
            justify-content: space-between;
        }

        .back-button {
            position: absolute;
            top: 10px;
            right: 10px;
            padding: 8px 15px;
            border-radius: 4px;
            background-color: #ccc;
            text-decoration: none;
            color: #333;
            font-weight: bold;
        }

        .back-button:hover {
            color: #000;
            background-color: #ddd;
        }
    </style>
</head>
<body>
    <!-- Button to go back to CreateCustomer.jsp -->
    <a href="CreateCustomer.jsp" class="back-button">Create Customer</a>

    <h2>Customer List</h2>
    <table>
        <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Street</th>
                <th>Address</th>
                <th>City</th>
                <th>State</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% 
                @SuppressWarnings("unchecked")
                List<Customer> customerList = (List<Customer>) request.getSession().getAttribute("customerList");
                if (customerList != null && !customerList.isEmpty()) {
                    for (Customer customer : customerList) {
            %>
                        <tr>
                            <td><%= customer.getFirstName()%></td>
                            <td><%= customer.getLastName() %></td>
                            <td><%= customer.getStreet() %></td>
                            <td><%= customer.getAddress() %></td>
                            <td><%= customer.getCity() %></td>
                            <td><%= customer.getState() %></td>
                            <td><%= customer.getEmail() %></td>
                            <td><%= customer.getPhone() %></td>
                            <td class="action-icons">
                                <a href="CustomerEdit.jsp?uuid=<%= customer.getUuid() %>" title="Edit">✎</a>
                                <a href="DeleteServlet?uuid=<%= customer.getUuid() %>" title="Delete">🗑</a>
                            </td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr>
                        <td colspan="9">No customers found.</td>
                    </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
