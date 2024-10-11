package com.example.devxsyncx.servlet;

import com.example.devxsyncx.entities.Tag;
import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.entities.enums.TaskStatus;
import com.example.devxsyncx.service.TagService;
import com.example.devxsyncx.service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/statistique")
public class StatistiqueServlet extends HttpServlet {

    private TaskService taskService;
    private TagService tagService;
    @Override
    public  void init() throws ServletException {
        taskService = new TaskService();
        tagService = new TagService();
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

        List<Tag> tags = tagService.getAllTags();

        request.setAttribute("tags", tags);
        request.setAttribute("pendingTasks", pendingTasks);
        request.setAttribute("inProgressTasks", inProgressTasks);
        request.setAttribute("completedTasks", completedTasks);
        request.setAttribute("overDueTasks", overDueTasks);
        request.getRequestDispatcher("/WEB-INF/views/statistique.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();

        String[] selectedTags = request.getParameterValues("tags");
        String period = request.getParameter("period");

        long pendingTasks = taskService.countFilteredTasks(TaskStatus.PENDING, userId, selectedTags, period);
        long inProgressTasks = taskService.countFilteredTasks(TaskStatus.IN_PROGRESS, userId, selectedTags, period);
        long completedTasks = taskService.countFilteredTasks(TaskStatus.COMPLETED, userId, selectedTags, period);
        long overDueTasks = taskService.countFilteredTasks(TaskStatus.OVERDUE, userId, selectedTags, period);

        List<Tag> tags = tagService.getAllTags();

        request.setAttribute("tags", tags);
        request.setAttribute("pendingTasks", pendingTasks);
        request.setAttribute("inProgressTasks", inProgressTasks);
        request.setAttribute("completedTasks", completedTasks);
        request.setAttribute("overDueTasks", overDueTasks);

        request.getRequestDispatcher("/WEB-INF/views/statistique.jsp").forward(request, response);

    }

}
