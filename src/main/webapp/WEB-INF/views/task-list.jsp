<%@ page import="com.example.devxsyncx.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.devxsyncx.entities.Tag" %>
<%@ page import="com.example.devxsyncx.entities.Task" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<%@ include file="shared/_header.jsp" %>
<div class="container mt-5">
    <h2>Task List</h2>

    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#taskModal">
        Add Task
    </button>

    <table class="table table-striped mt-3">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Assigned To</th>
            <th>Created By</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Task> tasksList = (List<Task>) request.getAttribute("tasks");
            if (tasksList != null) {
                for (Task task : tasksList) {
        %>
        <tr>
            <td><%= task.getTitle() %></td>
            <td><%= task.getDescription() %></td>
              <td><%= task.getAssignedTo() != null ? task.getAssignedTo().getUsername() : "N/A" %></td>
              <td><%= task.getCreatedBy() != null ? task.getCreatedBy().getUsername() : "N/A" %></td>
            <td>

                <button type="button" class="btn btn-sm btn-warning" data-toggle="modal" data-target="#updateModal<%= task.getId() %>">Update</button>
                <a href="tasks?action=delete&id=<%= task.getId() %>" class="btn btn-sm btn-danger">Delete</a>
            </td>
        </tr>


        <div class="modal fade" id="updateModal<%= task.getId() %>" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel<%= task.getId() %>" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form action="tasks" method="POST">
                        <input type="hidden" name="id" value="<%= task.getId() %>">
                        <div class="modal-header">
                            <h5 class="modal-title" id="updateModalLabel<%= task.getId() %>">Update Task: <%= task.getTitle() %></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="title<%= task.getId() %>">Title</label>
                                <input type="text" class="form-control" id="title<%= task.getId() %>" name="title" value="<%= task.getTitle() %>" required>
                            </div>
                            <div class="form-group">
                                <label for="description<%= task.getId() %>">Description</label>
                                <textarea class="form-control" id="description<%= task.getId() %>" name="description" required><%= task.getDescription() %></textarea>
                            </div>
                            <div class="form-group">
                                <label for="assignedTo<%= task.getId() %>">Assigned To</label>
                                <select class="form-control" id="assignedTo<%= task.getId() %>" name="assignedTo" required>
                                    <%
                                        List<User> allusers = (List<User>) request.getAttribute("allusers");
                                        if (allusers != null) {
                                            for (User user : allusers) {
                                                String selected = (task.getAssignedTo() != null && user.getId().equals(task.getAssignedTo().getId())) ? "selected" : "";
                                                out.println("<option value=\"" + user.getId() + "\" " + selected + ">" + user.getUsername() + "</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="tags<%= task.getId() %>">Tags</label>
                                <select class="form-control" id="tags<%= task.getId() %>" name="tags[]" multiple>
                                    <%
                                        List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                                        if (tags != null) {
                                            for (Tag tag : tags) {
                                                out.println("<option value=\"" + tag.getId() + "\">" + tag.getName() + "</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="dueDate<%= task.getId() %>">Due Date</label>
                                <input type="datetime-local" class="form-control" id="dueDate<%= task.getId() %>" name="dueDate" value="<%= task.getDueDate().toString().replace(' ', 'T') %>" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%
                }
            }
        %>
        </tbody>
    </table>


    <div class="modal fade" id="taskModal" tabindex="-1" role="dialog" aria-labelledby="taskModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
               <form action="tasks" method="POST">
                    <div class="modal-header">
                        <h5 class="modal-title" id="taskModalLabel">Add New Task</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="title">Title</label>
                            <input type="text" class="form-control" id="title" name="title" required>
                        </div>
                        <div class="form-group">
                            <label for="description">Description</label>
                            <textarea class="form-control" id="description" name="description" required></textarea>
                        </div>
                        <div class="form-group">
                            <label for="assignedTo">Assigned To</label>
                            <select class="form-control" id="assignedTo" name="assignedTo" required>
                                <%
                                    List<User> allusers = (List<User>) request.getAttribute("allusers");
                                    if (allusers != null) {
                                        for (User user : allusers) {
                                            out.println("<option value=\"" + user.getId() + "\">" + user.getUsername() + "</option>");
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="tags">Tags</label>
                            <select class="form-control" id="tags" name="tags[]" multiple>
                                <%
                                    List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                                    if (tags != null) {
                                        for (Tag tag : tags) {
                                            out.println("<option value=\"" + tag.getId() + "\">" + tag.getName() + "</option>");
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="dueDate">Due Date</label>
                            <input type="datetime-local" class="form-control" id="dueDate" name="dueDate" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save Task</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
