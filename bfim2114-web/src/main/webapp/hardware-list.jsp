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
<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="Logout">
</form>
</body>
</html>
