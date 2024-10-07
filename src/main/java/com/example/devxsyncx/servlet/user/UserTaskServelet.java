package com.example.devxsyncx.servlet.user;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.Token;
import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.service.TaskService;
import com.example.devxsyncx.service.UserService;
import com.example.devxsyncx.service.TokenService;
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
    private TokenService tokenService;

    @Override
    public void init() throws ServletException {
        this.taskService = new TaskService();
        this.tokenService = new TokenService();
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




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskIdParam = request.getParameter("taskId");

        if (taskIdParam == null || taskIdParam.isEmpty()) {
            response.sendRedirect("userTasks");
            return;
        }

        Long taskId = Long.parseLong(taskIdParam);
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        Token token = tokenService.findByUserId(currentUser.getId());

        if (token.getDeletionTokens() > 0) {
            taskService.deleteTask(taskId);
            token.setDeletionTokens(token.getDeletionTokens() - 1);
            tokenService.update(token);
            response.sendRedirect("userTasks");
        } else {

            request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
        }
    }

}
