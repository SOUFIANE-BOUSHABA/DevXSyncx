<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.devxsyncx.entities.Task" %>
<%@ page import="com.example.devxsyncx.entities.enums.TaskStatus" %>

<head>
    <meta charset="UTF-8">
    <title>Jira-Style Task Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaNnYgO7y0UY9YGSCS6Kt6hgB3mno1uTmIq+2gpzYIbX8rHU5awsuZVVFIhv" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"> <!-- Added Bootstrap Icons -->

    <style>
        .container {
            display: grid;
            place-items: center;
        }

        .row {
            border: 1px solid #d1e7dd;
            width: 80%;
            display: flex;
            justify-content: space-between;
        }

        .swim-lane {
            width: 250px;
            padding: 20px;
            border: 2px solid #d1e7dd;
            border-radius: 5px;
            background-color: #f8f9fa;
            transition: background-color 0.3s ease;
            min-height: 300px;
            height: auto;
        }

        .swim-lane h3 {
            background-color: #0d6efd;
            color: white;
            padding: 10px;
            text-align: center;
            border-radius: 5px;
        }

        .swim-lane:hover {
            background-color: #e2e6ea;
        }

        .task {
            padding: 20px;
            margin: 10px 0;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            cursor: grab;
            transition: background-color 0.2s ease;
            height: auto;
        }

        .task:hover {
            background-color: #f1f3f5;
        }

        .is-dragging {
            opacity: 0.5;
            background-color: #ced4da;
        }

        .badge-status {
            display: inline-block;
            font-size: 12px;
            padding: 5px;
            margin-right: 5px;
            border-radius: 5px;
        }

        .badge-status.todo {
            background-color: #ffc107;
        }

        .badge-status.in-progress {
            background-color: #0dcaf0;
        }

        .badge-status.completed {
            background-color: #198754;
        }

        .btn-add-task {
            display: block;
            width: 100%;
            margin-top: 10px;
        }

        .task-title {
            font-size: 16px;
            font-weight: bold;
        }

        .task-details {
            font-size: 14px;
        }

        .task-board {
            display: flex;
            justify-content: space-around;
            align-items: flex-start;
            padding: 20px;
        }

        .task-column {
            flex: 1;
            padding: 10px;
            background-color: #f5f5f5;
            margin: 0 10px;
            min-height: 300px;
            display: flex;
            flex-direction: column;
        }

        .task-column h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .task-card {
            background-color: white;
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 5px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
        }

        .task-card .status-badge {
            width: fit-content;
            padding: 5px 10px;
            color: white;
            font-weight: bold;
            border-radius: 3px;
            margin-bottom: 10px;
        }

        .status-badge.todo {
            background-color: #ffcc00;
        }

        .status-badge.in-progress {
            background-color: #00ccff;
        }

        .status-badge.completed {
            background-color: #00cc66;
        }
        .text-xs{
            font-size: 12px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center my-4">Task Management (Jira Style)</h1>
    <div class="row">
        <div class="col-md-4">
            <div class="swim-lane" id="todo-lane">
                <h3 class="heading">TODO</h3>
                <%
                    List<Task> pendingTasks = (List<Task>) request.getAttribute("pendingTasks");
                    if (pendingTasks != null) {
                        for (Task task : pendingTasks) { %>
                <div class="task" draggable="true" data-id="<%= task.getId() %>">
                    <span class="badge-status todo"><i class="bi bi-list-task"></i></span>
                    <div class="task-title"><%= task.getTitle() %></div>
                    <div class="task-details"><%= task.getDescription() %></div>
                    <div class="text-xs mt-2" style="color: #ffc107;"> <%= task.getDueDate() %> <i class="bi bi-clock-history" ></i></div>

                </div>
                <% } } %>
            </div>
        </div>
        <div class="col-md-4">
            <div class="swim-lane" id="doing-lane">
                <h3 class="heading">In Progress</h3>
                <%
                    List<Task> inProgressTasks = (List<Task>) request.getAttribute("inProgressTasks");
                    if (inProgressTasks != null) {
                        for (Task task : inProgressTasks) { %>
                <div class="task" draggable="true" data-id="<%= task.getId() %>">
                    <span class="badge-status in-progress"><i class="bi bi-arrow-counterclockwise"></i></span>
                    <div class="task-details"><%= task.getDescription() %></div>
                    <div class="text-xs mt-2" style="color: #0dcaf0;"> <%= task.getDueDate() %> <i class="bi bi-clock-history" ></i></div>
                </div>
                <% } } %>
            </div>
        </div>
        <div class="col-md-4">
            <div class="swim-lane" id="done-lane">
                <h3 class="heading">Completed</h3>
                <%
                    List<Task> completedTasks = (List<Task>) request.getAttribute("completedTasks");
                    if (completedTasks != null) {
                        for (Task task : completedTasks) { %>
                <div class="task" draggable="true" data-id="<%= task.getId() %>">
                    <span class="badge-status completed"><i class="bi bi-check-circle"></i></span>
                    <div class="task-details"><%= task.getDescription() %></div>
                    <div class="text-xs mt-2" style="color: #198754;"> <%= task.getDueDate() %> <i class="bi bi-clock-history" ></i></div>
                </div>
                <% } } %>
            </div>
        </div>
    </div>
</div>
<script>
    const draggables = document.querySelectorAll(".task");
    const droppables = document.querySelectorAll(".swim-lane");

    draggables.forEach((task) => {
        task.addEventListener("dragstart", () => {
            task.classList.add("is-dragging");
        });
        task.addEventListener("dragend", () => {
            task.classList.remove("is-dragging");
        });
    });

    droppables.forEach((zone) => {
        zone.addEventListener("dragover", (e) => {
            e.preventDefault();
            const bottomTask = insertAboveTask(zone, e.clientY);
            const curTask = document.querySelector(".is-dragging");

            if (!bottomTask) {
                zone.appendChild(curTask);
            } else {
                zone.insertBefore(curTask, bottomTask);
            }
        });

        zone.addEventListener("drop", (e) => {
            const curTask = document.querySelector(".is-dragging");
            const taskId = curTask.getAttribute("data-id");
            let newStatus;

            if (zone.id === "todo-lane") {
                newStatus = "PENDING";
            } else if (zone.id === "doing-lane") {
                newStatus = "IN_PROGRESS";
            } else if (zone.id === "done-lane") {
                newStatus = "COMPLETED";
            }

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "<%= request.getContextPath() %>/userTasksUpdate", true);
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        console.log("Update successful!");
                    } else {
                        console.error("Update error:", xhr.responseText);
                    }
                }
            };

            const payload = JSON.stringify({ id: taskId, status: newStatus });
            xhr.send(payload);
        });
    });

    function insertAboveTask(zone, y) {
        const draggableTasks = [...zone.querySelectorAll(".task:not(.is-dragging)")];

        return draggableTasks.reduce((closest, child) => {
            const box = child.getBoundingClientRect();
            const offset = y - box.top - box.height / 2;

            if (offset < 0 && offset > closest.offset) {
                return { offset: offset, element: child };
            } else {
                return closest;
            }
        }, { offset: Number.NEGATIVE_INFINITY }).element;
    }
</script>
</body>
