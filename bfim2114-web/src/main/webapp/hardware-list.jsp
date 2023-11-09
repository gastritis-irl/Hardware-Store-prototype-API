<%@ page import="java.util.List" %>
<%@ page import="edu.bbte.idde.bfim2114.backend.model.HardwarePart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hardware Parts List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h1>Hardware Parts</h1>

<form action="${pageContext.request.contextPath}/api/hardwareparts" method="post">
    <div>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div>
        <label for="manufacturer">Manufacturer:</label>
        <input type="text" id="manufacturer" name="manufacturer" required>
    </div>
    <div>
        <label for="category">Category:</label>
        <input type="text" id="category" name="category" required>
    </div>
    <div>
        <label for="price">Price:</label>
        <input type="number" step="0.01" id="price" name="price" required>
    </div>
    <div>
        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea>
    </div>
    <div>
        <input type="submit" value="Add Hardware Part">
    </div>
</form>

<%
    List<HardwarePart> parts = (List<HardwarePart>) request.getAttribute("parts");
    if (parts != null && !parts.isEmpty()) {
%>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Manufacturer</th>
        <th>Category</th>
        <th>Price</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (HardwarePart part : parts) {
    %>
    <tr>
        <td><%= part.getName() %>
        </td>
        <td><%= part.getManufacturer() %>
        </td>
        <td><%= part.getCategory() %>
        </td>
        <td><%= part.getPrice() %>
        </td>
        <td><%= part.getDescription() %>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<% } else { %>
<p>No hardware parts available.</p>
<% } %>
<form action="${pageContext.request.contextPath}/logout" method="get">
    <input type="submit" value="Logout">
</form>
</body>
</html>
