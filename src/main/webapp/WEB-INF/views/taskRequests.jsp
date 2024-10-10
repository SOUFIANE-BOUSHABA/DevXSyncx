

<%@ page import="com.example.devxsyncx.entities.TaskRequest" %>
<%@ page import="com.example.devxsyncx.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.devxsyncx.entities.enums.UserType" %>
<%
    if (session == null || session.getAttribute("user") == null ) {
        User user = (User) session.getAttribute("user");
        if(!user.getUserType().equals(UserType.MANAGER)){
            response.sendRedirect("profile");
            return;

        }
        response.sendRedirect("login");
        return;

    }
%>

<%@ include file="admin/layout.jsp" %>
<div class="container">
    <div class="card shadow-sm">
        <div class="d-flex justify-content-between p-3">
            <h1>Your Task Requests</h1>
        </div>

        <div class="table-responsive p-3 mb-3 bg-body rounded">
            <%
                List<TaskRequest> taskRequests = (List<TaskRequest>) request.getAttribute("taskRequests");
                List<User> allusers = (List<User>) request.getAttribute("allusers");
                if (taskRequests != null && !taskRequests.isEmpty()) {
            %>
            <table class="table align-middle bg-white">
                <thead class="bg-light">
                <tr>
                    <th>Task</th>
                    <th>Requested By</th>
                    <th>Current Manager</th>
                    <th>Change Assigned </th>
                    <th class="col-md-2">Actions</th>
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
                  <td style="display: flex; align-items: center;">
                        <form action="editTaskRequest" method="post" style="display: flex; align-items: center; gap: 10px;">
                            <input type="hidden" name="taskRequestId" value="<%= taskRequest.getId() %>">
                            <select class="form-control" name="newRequesterId" style="height: 40px;">
                                <%
                                    for (User user : allusers) {
                                        if(!taskRequest.getRequester().getId().equals(user.getId())) {
                                %>
                                <option value="<%= user.getId() %>"><%= user.getUsername() %></option>
                                <%
                                        }}
                                %>
                            </select>
                            <button type="submit" class="btn btn-primary" style="height: 40px;">Save</button>
                        </form>
                    </td>
                    <td>
                        <a href="editTaskRequest?action=delete&taskId=<%= taskRequest.getTask().getId() %>"  class="btn btn-sm btn-danger">Delete</a>
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
            <p class="alert alert-warning">No task requests found.</p>
            <%
                }
            %>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
