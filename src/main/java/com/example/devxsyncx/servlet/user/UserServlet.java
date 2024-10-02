package com.example.devxsyncx.servlet.user;

import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if ("edit".equals(action)) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");

            if (user != null) {
                boolean isUpdated = userService.updateUser(user, firstName, lastName, email, currentPassword, newPassword);
                if (isUpdated) {
                    response.sendRedirect("profile");
                } else {
                    request.setAttribute("error", "Current password is incorrect.");
                    request.getRequestDispatcher("profile.jsp").forward(request, response);
                }
            }
        }
    }



}