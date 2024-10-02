package com.example.devxsyncx.servlet;

import com.example.devxsyncx.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (userService.checkPassword(username, password)) {
                userService.deleteUserByUsername(username);
                response.sendRedirect("login");
            } else {
                response.sendRedirect("profile.jsp?error=Invalid%20password");
            }
        }
    }
}