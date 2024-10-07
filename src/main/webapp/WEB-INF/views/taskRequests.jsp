<%@ page import="com.example.devxsyncx.entities.TaskRequest" %>
<%@ page import="com.example.devxsyncx.entities.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>User Task Requests</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="mt-5">Your Task Requests</h1>
    <%
        List<TaskRequest> taskRequests = (List<TaskRequest>) request.getAttribute("taskRequests");
        List<User> allusers = (List<User>) request.getAttribute("allusers");
        if (taskRequests != null && !taskRequests.isEmpty()) {
    %>
    <table class="table table-striped mt-3">
        <thead class="thead-dark">
        <tr>
            <th>Task</th>
            <th>Requested By</th>
            <th>Current Manager</th>
            <th>Change Assigned Manager</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (TaskRequest taskRequest : taskRequests) {
        %>
        <tr>
            <td><%= taskRequest.getTask().getDescription() %></td>
            <td><%= taskRequest.getRequester().getUsername() %></td>
            <td><%= taskRequest.getManager().getUsername() %></td>
            <td>
                <form action="editTaskRequest" method="post" class="form-inline">
                    <input type="hidden" name="taskRequestId" value="<%= taskRequest.getId() %>">
                    <select class="form-control mr-2" name="newRequesterId">
                        <%
                            for (User user : allusers) {
                                if(!taskRequest.getRequester().getId().equals(user.getId())) {
                        %>
                        <option value="<%= user.getId() %>" >
                            <%= user.getUsername() %>
                        </option>
                        <%
                            }}
                        %>
                    </select>
                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </td>
            <td>
                <a href="editTaskRequest?action=delete&taskId=<%= taskRequest.getTask().getId() %>" class="btn btn-sm btn-danger">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <p class="mt-3 alert alert-warning">No task requests found.</p>
    <%
        }
    %>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
