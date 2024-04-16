<%@page import="org.jsp.todo_app.dto.Task"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
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

        tr:hover {
            background-color: #f5f5f5;
        }

        .button {
            display: flex;
            justify-content: space-around;
            align-items: center;
            margin-top: 20px;
        }

        .button button {
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .button button:hover {
            background-color: #45a049;
        }

        img {
            max-width: 50px;
            max-height: 50px;
        }
    </style>
</head>
<body>
    <h1 align="center">Home Page</h1>
    <% 
    List<Task> tasks = (List<Task>) request.getAttribute("tasks"); 
    %>
    <%
    if(!tasks.isEmpty()) {
    %>
    <div>
        <table>
            <thead>
                <tr>
                    <th>Task Image</th>
                    <th>Task Name</th>
                    <th>Task Description</th>
                    <th>Created Time</th>
                    <th>Status</th>
                    <th>Delete</th>
                    <th>Edit</th>
                </tr>
            </thead>
            <%
            for(Task task : tasks) {
            %>
            <tbody>
                <tr>
                    <td><img alt="image" src="data:image/png;base64,<%=task.getEncodeImage()%>"></td>
                    <td><%=task.getName() %></td>
                    <td><%=task.getDescription() %></td>
                    <td><%=task.getAddTime() %></td>
                    <td><%if(task.isStatus()) {%>
                    Completed<%}else{ %><a href="complete-task?id=<%=task.getId() %>"><button>Complete</button></a><%} %>
                    </td>
                    <td><a href="delete?id=<%=task.getId()%>"><button>Delete</button></a></td>
                    <td><a href="edit-task.jsp?id=<%=task.getId()%>"><button>Edit</button></a></td>
                </tr>
            </tbody>
            <%
            }
            %>
        </table>
    </div>
    <%
    }
    %>
    <div class="button">
        <a href="add-task.html"><button>ADD TASK</button></a>
        <form method="post" action="logout">
            <button type="submit">LOGOUT</button>
        </form>
    </div>
</body>
</html>
