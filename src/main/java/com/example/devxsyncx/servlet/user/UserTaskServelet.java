package com.example.devxsyncx.servlet.user;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.service.TaskService;
import com.example.devxsyncx.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/userTasks")
public class UserTaskServelet extends HttpServlet {
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        this.taskService = new TaskService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        Long userId = currentUser.getId();

        List<Task> pendingTasks = taskService.getPendingTasksForUser(userId);
        List<Task> inProgressTasks = taskService.getInProgressTasksForUser(userId);
        List<Task> completedTasks = taskService.getCompletedTasksForUser(userId);

        request.setAttribute("pendingTasks", pendingTasks);
        request.setAttribute("inProgressTasks", inProgressTasks);
        request.setAttribute("completedTasks", completedTasks);

        request.getRequestDispatcher("/WEB-INF/views/task.jsp").forward(request, response);
    }
}
