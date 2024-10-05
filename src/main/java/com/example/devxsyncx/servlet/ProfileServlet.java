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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private TaskService taskService; // Add your service here

    @Override
    public void init() {

        taskService = new TaskService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            req.setAttribute("user", user);
            long userId = user.getId();

            long completedTasks = taskService.countTasksByStatusAndUser(TaskStatus.COMPLETED , userId);
            long inProgressTasks = taskService.countTasksByStatusAndUser(TaskStatus.IN_PROGRESS , userId);
            long todoTasks = taskService.countTasksByStatusAndUser(TaskStatus.PENDING , userId);

            req.setAttribute("completedTasks", completedTasks);
            req.setAttribute("inProgressTasks", inProgressTasks);
            req.setAttribute("todoTasks", todoTasks);


            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }
}
