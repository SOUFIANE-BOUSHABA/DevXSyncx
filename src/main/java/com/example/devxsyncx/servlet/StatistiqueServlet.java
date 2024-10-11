package com.example.devxsyncx.servlet;

import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.entities.enums.TaskStatus;
import com.example.devxsyncx.service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/statistique")
public class StatistiqueServlet extends HttpServlet {

    private TaskService taskService;
    @Override
    public  void init() throws ServletException {
        taskService = new TaskService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();
        long pendingTasks = taskService.countTasksByStatusAndManager(TaskStatus.PENDING,userId);
        long inProgressTasks = taskService.countTasksByStatusAndManager(TaskStatus.IN_PROGRESS,userId);
        long completedTasks = taskService.countTasksByStatusAndManager(TaskStatus.COMPLETED,userId);
        long overDueTasks = taskService.countTasksByStatusAndManager(TaskStatus.OVERDUE,userId);

        request.setAttribute("pendingTasks", pendingTasks);
        request.setAttribute("inProgressTasks", inProgressTasks);
        request.setAttribute("completedTasks", completedTasks);
        request.setAttribute("overDueTasks", overDueTasks);
        request.getRequestDispatcher("/WEB-INF/views/statistique.jsp").forward(request, response);
    }
}
