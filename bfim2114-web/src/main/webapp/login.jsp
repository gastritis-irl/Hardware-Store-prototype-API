<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <input type="submit" value="Login">
    </div>
    <div>
        <!-- Display error message if login fails -->
        <% if (request.getParameter("error") != null) { %>
        <span style="color:red;">
                Invalid username or password.
            </span>
        <% } %>
    </div>
</form>
</body>
</html>
