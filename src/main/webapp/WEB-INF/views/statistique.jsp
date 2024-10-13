<%@ page import="com.example.devxsyncx.entities.Tag" %>
<%@ page import="java.util.List" %>
<%@ include file="admin/layout.jsp" %>
<style>
    .container{
        height: 124vh;
        width: 100%;
        background: #f4f5f5;
    }
    .tags-container {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;
        max-width: 500px;
    }

    .tag {
        padding: 4px 8px;
        border: 1px solid lightgray;
        border-radius: 12px;
        font-size: 12px;
        cursor: pointer;
        user-select: none;
        transition: border-color 0.3s ease;
    }

    .tag-checkbox:checked + .tag {
        border-color: blue;
    }

    .tag-checkbox {
        display: none;
    }

    .tag:hover {
        border-color: gray;
    }

    .filter-container {
        display: flex;
        flex-direction: column;
        gap: 16px;
    }

    .filter-row {
        display: flex;
        justify-content: space-between;
        gap: 16px;
    }

    .filter-row .tags-container {
        flex: 1;
    }

    .periode {
        flex: 1;
    }

    .filter-button {
        align-self: flex-end;
        width: 200px;
        margin-top: -20px;
    }

    .chart {
        margin-top: -370px;
        width: 100%;
        max-width: 800px;
        margin-left: auto;
        margin-right: auto;
    }
    .error-message {
        color: red;
        margin-top: 10px;
    }
</style>

<div class="container">
    <div class="row task-board">
        <div class="col-md-12">
            <div class="card p-3 mb-2">
                <h5 class="card-title">Filtrer</h5>
                <form action="statistique" method="POST" class="filter-container" onsubmit="return validateForm()">
                    <div class="filter-row">
                        <div class="tags-container">
                            <%
                                List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                                if (tags != null) {
                                    for (Tag tag : tags) {
                                        out.println("<input type='checkbox' id='tag-" + tag.getId() + "' name='tags' value='" + tag.getId() + "' class='tag-checkbox'>");
                                        out.println("<label for='tag-" + tag.getId() + "' class='tag'>" + tag.getName() + "</label>");
                                    }
                                }
                            %>
                            <div id="error-message" class="error-message" style="display: none;">Please select at least one tag.</div>
                        </div>

                        <div class="periode">
                            <select id="period" name="period" class="form-select">
                                <option value="week" selected>Cette Semaine</option>
                                <option value="month">Ce Mois</option>
                                <option value="year">Cette Année</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary filter-button" id="filterBtn">Appliquer le Filtre</button>
                </form>
            </div>
        </div>
    </div>


    <div class="col-md-12 p-3">
                <div class="row">
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
            </div>
        </div>
    </div>






    <div class=" chart">
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

<script>
    function validateForm() {
        const checkboxes = document.querySelectorAll('.tag-checkbox');
        const errorMessage = document.getElementById('error-message');
        for (let checkbox of checkboxes) {
            if (checkbox.checked) {
                errorMessage.style.display = 'none';
                return true;
            }
        }
        errorMessage.style.display = 'block';
        return false;
    }
</script>
</body>
</html>
