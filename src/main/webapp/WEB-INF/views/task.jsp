<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.devxsyncx.entities.Task" %>


    <style>
        .container1 {
            display: grid;
            place-items: center;
            text-align: left;
            width: 100%;
        }

        .row {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }

        .swim-lane {
            width: 300px;
            padding: 20px;
            border: 2px solid #c6dbd1;
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
            border-radius: 20px;
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

        .text-xs {
            font-size: 12px;
            margin-top: 20px;
        }
        .dropdown-toggle::after {
            display: none;
        }
        .datebadge{
            background-color: #0e0e0d;
            color: #fff;
            padding-top: 2px;
            padding-left: 5px;
            border-radius: 10px;
            font-size: 10px;
            height: 20px;
            max-width: 100px;
        }
        .taskTodo{
            border: 2px solid #ffb92f;
        }
        .taskInpr{
            border: 2px solid #2f90ff;
        }
        .taskDone{
            border: 2px solid #2fff63;
        }

        .ezy__travel2_wDNEJ8wr {
            height: 90px;
            width: 98%;
            border-radius: 10px;
            margin-bottom: 20px;

            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            color: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .ezy__travel2_wDNEJ8wr-heading {
            margin-left: -200px;
            font-size: 10px;
        }

        @media (min-width: 768px) {
            .ezy__travel2_wDNEJ8wr-heading {
                font-size: 50px;
            }
        }
    </style>
</head>
<body>
<%@ include file="admin/layout.jsp" %>
<div class="container  container1">


    <section
            class="ezy__travel2_wDNEJ8wr"
            style="background-image: url(https://cdn.easyfrontend.com/pictures/hero/header35-img.png)"
    >
        <div class="container">
            <div class="row justify-content-center align-items-center">
                <div class="col-12 col-lg-7 text-left">
                    <h2 class=" mb-2" style="margin-left: -150px">Task managment</h2>

                </div>
            </div>
        </div>
    </section>



    <div class="row">
        <div class="col-md-4">
            <div class="swim-lane" id="todo-lane">
                <h4 class="heading">TODO</h4>
                <%
                    List<Task> pendingTasks = (List<Task>) request.getAttribute("pendingTasks");
                    if (pendingTasks != null) {
                        for (Task task : pendingTasks) { %>
                <div class="task taskTodo" draggable="true" data-id="<%= task.getId() %>" style="position: relative;">
                    <div class="task-title"><%= task.getTitle() %></div>
                    <div class="task-details text-gray"><%= task.getDescription() %></div>
                    <div class="text-xs mt-2 datebadge"> <%= task.getDueDate() %> </div>

                    <div class="dropdown" style="position: absolute; top: 10px; right: 10px;">

                        <a class="btn btn-link text-dark" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false" style="background: #ced4da ; border-radius: 100% ;width: 30px; display: flex; justify-content: center; align-items: center;">
                            <i class="bi bi-gear"></i>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <li>
                                <form action="<%= request.getContextPath() %>/editTaskRequest" method="post" style="display:inline;">
                                    <input type="hidden" name="taskId" value="<%= task.getId() %>">
                                    <button type="submit" class="dropdown-item">Edit</button>
                                </form>
                            </li>
                            <li>
                                <form action="<%= request.getContextPath() %>/userTasks" method="post" style="display:inline;">
                                    <input type="hidden" name="taskId" value="<%= task.getId() %>">
                                    <button type="submit" class="dropdown-item">Delete</button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
                <% } } %>
            </div>
        </div>
        <div class="col-md-4">
            <div class="swim-lane" id="doing-lane">
                <h4 class="heading">In Progress</h4>
                <%
                    List<Task> inProgressTasks = (List<Task>) request.getAttribute("inProgressTasks");
                    if (inProgressTasks != null) {
                        for (Task task : inProgressTasks) { %>
                <div class="task taskInpr" draggable="true" data-id="<%= task.getId() %>">
                    <div class="task-title"><%= task.getTitle() %></div>
                    <div class="task-details text-gray"><%= task.getDescription() %></div>
                    <div class="text-xs mt-2  datebadge"> <%= task.getDueDate() %></div>
                </div>
                <% } } %>
            </div>
        </div>
        <div class="col-md-4">
            <div class="swim-lane" id="done-lane">
                <h4 class="heading">Completed</h4>
                <%
                    List<Task> completedTasks = (List<Task>) request.getAttribute("completedTasks");
                    if (completedTasks != null) {
                        for (Task task : completedTasks) { %>
                <div class="task taskDone" draggable="true" data-id="<%= task.getId() %>">
                    <div class="task-title"><%= task.getTitle() %></div>
                    <div class="task-details text-gray"><%= task.getDescription() %></div>
                    <div class="text-xs mt-2 datebadge" > <%= task.getDueDate() %></div>
                </div>
                <% } } %>
            </div>
        </div>
    </div>
</div>

</section>
</div>
</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
                        location.reload();
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
