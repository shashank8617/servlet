<%@page import="org.jsp.todo_app.dto.Task"%>
<%@page import="org.jsp.todo_app.dao.TodoDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Task</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
        text-align: center;
    }

    form {
        background-color: #ffffff;
        max-width: 400px;
        margin: 50px auto;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    h1 {
        color: #333333;
    }

    label {
        display: block;
        margin-bottom: 8px;
        color: #555555;
        text-align: left;
    }

    input {
        width: 100%;
        padding: 8px;
        margin-bottom: 15px;
        box-sizing: border-box;
        border: 1px solid #cccccc;
        border-radius: 4px;
    }

    input[type="file"] {
        margin-bottom: 20px; /* Adjust margin for file input */
    }

    button {
        background-color: #4caf50;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    button:hover {
        background-color: #45a049;
    }

    a {
        color: #3498db;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>
<%
int id=Integer.parseInt(request.getParameter("id")); 
TodoDao dao = new TodoDao();
Task task = dao.findTaskById(id);
%>
<form action="edit-task" method="post" enctype="multipart/form-data">
    <h1>Edit Task</h1>
    <input type="hidden" name="id" value="<%=task.getId()%>">
    <label>Task Name :</label> <input type="text" name="name" required value="<%=task.getName()%>"><br><br>
    <label>Task Description :</label> <input type="text" name="description" required value="<%=task.getDescription()%>"><br><br>
    <label>Image :</label> <input type="file" name="image" ><img alt="image" height="50px" width="50px" src="data:image/png;base64,<%=task.getEncodeImage()%>"><br><br>
    <button type="submit">Update</button> <button type="reset">Cancel</button><br><br>
</form>
</body>
</html>
