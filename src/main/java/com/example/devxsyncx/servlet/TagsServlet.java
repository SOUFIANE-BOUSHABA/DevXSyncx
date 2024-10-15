package com.example.devxsyncx.servlet;

import com.example.devxsyncx.entities.Tag;
import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.repository.impl.TokenRepositoryImpl;
import com.example.devxsyncx.repository.impl.UserRepositoryImpl;
import com.example.devxsyncx.service.TagService;
import com.example.devxsyncx.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/create-task")
public class TagsServlet extends HttpServlet {

    private TagService tagService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.tagService = new TagService();
        this.userService = new UserService(new UserRepositoryImpl(), new TokenRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Tag> allTags = tagService.getAllTags();

        List<User> allUsers = userService.getAllUsers();

        request.setAttribute("tags", allTags);
        request.setAttribute("users", allUsers);
        request.getRequestDispatcher("/WEB-INF/views/task-list.jsp").forward(request, response);
    }
}
