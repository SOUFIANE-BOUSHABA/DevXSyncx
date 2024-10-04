<%@ page import="com.example.devxsyncx.entities.Task" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Management - Drag & Drop</title>
    <link rel="stylesheet" href="assets/css/drag.css">

    <script src="assets/js/drag.js" defer></script>
    <script src="assets/js/todo.js" defer></script>
</head>
<body>
<div class="board">

    <div class="lanes">
        <div class="swim-lane" id="todo-lane">
            <h3 class="heading">TODO</h3>
            <%
                List<Task> pendingTasks = (List<Task>) request.getAttribute("pendingTasks");
                if (pendingTasks != null) {
                    for (Task task : pendingTasks) {
                        out.println("<p class='task' draggable='true'>" + task.getTitle() + "</p>");
                    }
                }
            %>
        </div>


        <div class="swim-lane" id="doing-lane">
            <h3 class="heading">Doing</h3>
            <%
                List<Task> inProgressTasks = (List<Task>) request.getAttribute("inProgressTasks");
                if (inProgressTasks != null) {
                    for (Task task : inProgressTasks) {
                        out.println("<p class='task' draggable='true'>" + task.getTitle() + "</p>");
                    }
                }
            %>
        </div>


        <div class="swim-lane" id="done-lane">
            <h3 class="heading">Done</h3>
            <%
                List<Task> completedTasks = (List<Task>) request.getAttribute("completedTasks");
                if (completedTasks != null) {
                    for (Task task : completedTasks) {
                        out.println("<p class='task' draggable='true'>" + task.getTitle() + "</p>");
                    }
                }
            %>
        </div>
    </div>
</div>
</body>
</html>
