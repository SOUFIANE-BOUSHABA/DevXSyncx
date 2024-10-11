<%@ include file="admin/layout.jsp" %>


<div class="container ">
    <div class="row task-board">
        <div class="col-md-3">
            <div class="ezy__travel2_wDNEJ8wr" style="background-image: url(https://cdn.easyfrontend.com/pictures/hero/header35-img.png); background-color: rgba(0, 123, 255, 0.7);">
                <div class="card-body">
                    <h5 class="task-title">Task Todo</h5>
                    <p class="task-details">
                        <%
                            Long taskTodo = (Long) request.getAttribute("pendingTasks");
                        %>
                        <%= taskTodo %>
                    </p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="ezy__travel2_wDNEJ8wr" style="background-image: url(https://cdn.easyfrontend.com/pictures/hero/header35-img.png); background-color: rgba(108, 117, 125, 0.7);">
                <div class="card-body">
                    <h5 class="task-title">Task In Progress</h5>
                    <p class="task-details">
                        <%
                            Long inProgressTasks = (Long) request.getAttribute("inProgressTasks");
                        %>
                        <%= inProgressTasks %>
                    </p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="ezy__travel2_wDNEJ8wr" style="background-image: url(https://cdn.easyfrontend.com/pictures/hero/header35-img.png); background-color: rgba(40, 167, 69, 0.7);">
                <div class="card-body">
                    <h5 class="task-title">Task Completed</h5>
                    <p class="task-details">
                        <%
                            Long completedTasks = (Long) request.getAttribute("completedTasks");
                        %>
                        <%= completedTasks %>
                    </p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="ezy__travel2_wDNEJ8wr" style="background-image: url(https://cdn.easyfrontend.com/pictures/hero/header35-img.png); background-color: rgba(255, 7, 7, 0.7);">
                <div class="card-body">
                    <h5 class="task-title">Task Overdue</h5>
                    <p class="task-details">
                        <%
                            Long overDueTasks = (Long) request.getAttribute("overDueTasks");
                        %>
                        <%= overDueTasks %>
                    </p>
                </div>
            </div>
        </div>
    </div>






    <div class="row">
        <div class="col-md-12">
            <div class="card text-dark bg-light mb-3">
                <div class="card-header">Tasks Status</div>
                <div class="card-body" style="width: 550px">
                    <canvas id="tasksChart" width="400" height="200"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<link rel="stylesheet" href="assets/css/statistique.css">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        var ctx = document.getElementById('tasksChart').getContext('2d');


        var taskTodo = <%= taskTodo %>;
        var inProgressTasks = <%= inProgressTasks %>;
        var completedTasks = <%= completedTasks %>;
        var overDueTasks = <%= overDueTasks %>;

        var tasksChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Todo', 'In Progress', 'Completed', 'Overdue'],
                datasets: [{
                    label: 'Number of Tasks',
                    data: [taskTodo, inProgressTasks, completedTasks, overDueTasks],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 99, 132, 0.2)'
                    ],
                    borderColor: [
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });
</script>
</body>
</html>
