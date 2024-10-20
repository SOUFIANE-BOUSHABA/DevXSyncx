package com.example.devxsyncx.servlet.user;

import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.repository.impl.TokenRepositoryImpl;
import com.example.devxsyncx.repository.impl.UserRepositoryImpl;
import com.example.devxsyncx.scheduler.ResetTokenScheduler;
import com.example.devxsyncx.scheduler.TaskRequestScheduler;
import com.example.devxsyncx.scheduler.TaskScheduler;
import com.example.devxsyncx.service.TaskService;
import com.example.devxsyncx.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;
    private TaskScheduler taskScheduler;
    private TaskRequestScheduler taskRequestScheduler;
    private ResetTokenScheduler resetTokenScheduler;


    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserRepositoryImpl() , new TokenRepositoryImpl());

        taskScheduler = new TaskScheduler();
        taskScheduler.start();
        resetTokenScheduler = new ResetTokenScheduler();
        resetTokenScheduler.start();
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect("profile");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("profile");
        } else {
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
