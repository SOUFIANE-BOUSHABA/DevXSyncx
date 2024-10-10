<%@ page import="com.example.devxsyncx.entities.User" %>
<%@ page import="com.example.devxsyncx.entities.enums.UserType" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
    <link href="assets/css/admin.css" rel="stylesheet">
    <script src="assets/js/admin.js" defer></script>
    <title>Bootstrap Example</title>
</head>
<body>
<%
    if (session == null || session.getAttribute("user") == null) {
    response.sendRedirect("login");
    return;
}
%>
<div class="container-fluid">
    <div class="row">
        <aside class="left shadow" id="myAside">
            <div class="d-flex gap-2 mb-5">
                <h3 class="link"> DEVSYNC<span class="text-primary">X</span> </h3>
            </div>

            <a href="tasks" class="d-flex gap-3 align-items-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-activity" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M6 2a.5.5 0 0 1 .47.33L10 12.036l1.53-4.208A.5.5 0 0 1 12 7.5h3.5a.5.5 0 0 1 0 1h-3.15l-1.88 5.17a.5.5 0 0 1-.94 0L6 3.964 4.47 8.171A.5.5 0 0 1 4 8.5H.5a.5.5 0 0 1 0-1h3.15l1.88-5.17A.5.5 0 0 1 6 2Z"/>
                </svg>
                <span class="link">Add Task</span>
            </a>

            <% if (session.getAttribute("user") != null && ((User) session.getAttribute("user")).getUserType().equals(UserType.MANAGER)) { %>
            <a href="editTaskRequest" class="d-flex gap-3 align-items-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-activity" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M6 2a.5.5 0 0 1 .47.33L10 12.036l1.53-4.208A.5.5 0 0 1 12 7.5h3.5a.5.5 0 0 1 0 1h-3.15l-1.88 5.17a.5.5 0 0 1-.94 0L6 3.964 4.47 8.171A.5.5 0 0 1 4 8.5H.5a.5.5 0 0 1 0-1h3.15l1.88-5.17A.5.5 0 0 1 6 2Z"/>
                </svg>
                <span class="link"> Task  Requests</span>
            </a>
            <% } %>

            <a href="userTasks" class="d-flex gap-3 align-items-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-activity" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M6 2a.5.5 0 0 1 .47.33L10 12.036l1.53-4.208A.5.5 0 0 1 12 7.5h3.5a.5.5 0 0 1 0 1h-3.15l-1.88 5.17a.5.5 0 0 1-.94 0L6 3.964 4.47 8.171A.5.5 0 0 1 4 8.5H.5a.5.5 0 0 1 0-1h3.15l1.88-5.17A.5.5 0 0 1 6 2Z"/>
                </svg>
                <span class="link"> Task TODO</span>
            </a>


        </aside>

        <div class="right" id="right">
            <header class="">
                <div class="w-full navv d-flex justify-content-between align-items-center">

                    <button class="btn ml-2 btn-light" id="leftBtn" onclick="toggleAside()">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z"/>
                        </svg>
                    </button>

                    <button class="btn ml-2 btn-light" id="rightBtn" onclick="toggleAside()">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-right" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M6 8a.5.5 0 0 0 .5.5h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L12.293 7.5H6.5A.5.5 0 0 0 6 8zm-2.5 7a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5z"/>
                        </svg>
                    </button>

                    <div class="dropdown">
                        <%
                            HttpSession sessionn = request.getSession();
                            User userr = (User) sessionn.getAttribute("user");
                            String userName = userr.getUsername();
                        %>
                        <button class="btn btn-dark dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                            <%= userName != null ? userName : "User" %>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <li><a class="dropdown-item" href="profile">Profile</a></li>
                            <li><a class="dropdown-item" href="#">Another action</a></li>
                            <li>
                                <form action="logout" method="post">
                                    <button type="submit" class="nav-link  mt-2" style="margin-left: 20px">  Logout</button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </header>

            <section class="content">


