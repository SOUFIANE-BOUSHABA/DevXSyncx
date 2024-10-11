<%@ page import="com.example.devxsyncx.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.devxsyncx.entities.Tag" %>
<%@ page import="com.example.devxsyncx.entities.Task" %>
<%@ page import="com.example.devxsyncx.entities.enums.UserType" %>

<%
    if (session == null || session.getAttribute("user") == null ) {

        response.sendRedirect("login");
        return;

    }
%>
<%@ include file="admin/layout.jsp" %>
<div class="container ">
    <div class="card shadow-sm">
        <div class="d-flex justify-content-between p-3">
            <button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#taskModal">
                Add Task
            </button>

            <div class="d-flex gap-2" >
                <form action="tasks" method="GET" class="d-flex gap-4">
                    <input type="text" class="form-control" name="search" placeholder="Search">
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>

            </div>


        </div>

        <div class="table-responsive p-3 mb-3 bg-body rounded">
            <table class="table align-middle mb-4 mt-2 bg-white">
                <thead class="bg-light">
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Assigned To</th>
                    <th>Created By</th>
                    <th class="col-md-2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Task> tasksList = (List<Task>) request.getAttribute("tasks");
                    List<User> allusers = (List<User>) request.getAttribute("allusers");
                    User currentUser = (User) session.getAttribute("user");

                    if (tasksList != null) {
                        for (Task task : tasksList) {
                %>
                <tr>
                    <td><%= task.getTitle() %></td>
                    <td><%= task.getDescription() %></td>
                    <td><%= task.getAssignedTo() != null ? task.getAssignedTo().getUsername() : "N/A" %></td>
                    <td><%= task.getCreatedBy() != null ? task.getCreatedBy().getUsername() : "N/A" %></td>
                    <td class="d-flex gap-2">
                        <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#updateModal<%= task.getId() %>">
                            Update
                        </button>
                        <a href="tasks?action=delete&id=<%= task.getId() %>" class="btn btn-danger btn-sm">Delete</a>
                    </td>
                </tr>

                <div class="modal fade" id="updateModal<%= task.getId() %>" tabindex="-1" aria-labelledby="updateModalLabel<%= task.getId() %>" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form action="tasks" method="POST">
                                <input type="hidden" name="id" value="<%= task.getId() %>">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="updateModalLabel<%= task.getId() %>">Update Task: <%= task.getTitle() %></h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="title<%= task.getId() %>" class="form-label">Title</label>
                                        <input type="text" class="form-control" id="title<%= task.getId() %>" name="title" value="<%= task.getTitle() %>" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="description<%= task.getId() %>" class="form-label">Description</label>
                                        <textarea class="form-control" id="description<%= task.getId() %>" name="description" required><%= task.getDescription() %></textarea>
                                    </div>
                                    <div class="mb-3">
                                        <label for="assignedTo<%= task.getId() %>" class="form-label">Assigned To</label>
                                        <select class="form-control" id="assignedTo<%= task.getId() %>" name="assignedTo" required>
                                            <%
                                                if (currentUser.getUserType() == UserType.MANAGER) {
                                                    if (allusers != null) {
                                                        for (User user : allusers) {
                                                            String selected = (task.getAssignedTo() != null && task.getAssignedTo().getId().equals(user.getId())) ? "selected" : "";
                                                            out.println("<option value=\"" + user.getId() + "\" " + selected + ">" + user.getUsername() + "</option>");
                                                        }
                                                    }
                                                } else if (currentUser.getUserType() == UserType.USER) {
                                                    String selected = (task.getAssignedTo() != null && task.getAssignedTo().getId().equals(currentUser.getId())) ? "selected" : "";
                                                    out.println("<option value=\"" + currentUser.getId() + "\" " + selected + ">" + currentUser.getUsername() + "</option>");
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


                                    <div class="mb-3">
                                        <label for="dueDate<%= task.getId() %>" class="form-label">Due Date</label>
                                        <input type="datetime-local" class="form-control" id="dueDate<%= task.getId() %>" name="dueDate" value="<%= task.getDueDate().toString().replace(' ', 'T') %>" required>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
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
        </div>
    </div>

    <div class="modal fade" id="taskModal" tabindex="-1" aria-labelledby="taskModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="tasks" method="POST">
                    <div class="modal-header">
                        <h5 class="modal-title" id="taskModalLabel">Add New Task</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="title" class="form-label">Title</label>
                            <input type="text" class="form-control" id="title" name="title" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="assignedTo" class="form-label">Assigned To</label>
                            <select class="form-control" id="assignedTo" name="assignedTo" required>
                                <%
                                    if (currentUser.getUserType() == UserType.MANAGER) {
                                        if (allusers != null) {
                                            for (User user : allusers) {
                                                out.println("<option value=\"" + user.getId() + "\">" + user.getUsername() + "</option>");
                                            }
                                        }
                                    } else if (currentUser.getUserType() == UserType.USER) {
                                        out.println("<option value=\"" + currentUser.getId() + "\">" + currentUser.getUsername() + "</option>");
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


                        <div class="mb-3">
                            <label for="dueDate" class="form-label">Due Date</label>
                            <input type="datetime-local" class="form-control" id="dueDate" name="dueDate" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save Task</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</section>
</div>
</div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>