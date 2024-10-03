package com.example.devxsyncx.servlet;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.Tag;
import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.entities.enums.TaskStatus;
import com.example.devxsyncx.service.TaskService;
import com.example.devxsyncx.service.TagService;
import com.example.devxsyncx.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private TaskService taskService;
    private TagService tagService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.taskService = new TaskService();
        this.tagService = new TagService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.equals("list")) {
            listTasks(request, response);
        } else if (action.equals("delete")) {
            deleteTask(request, response);
        }
    }

    private void listTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = taskService.getAllTasks();
        List<Tag> tags = tagService.getAllTags();
        List<User> allusers = userService.getAllUsers();

        System.out.println("All Users: " + allusers);

        if (tasks.isEmpty()) {
            request.setAttribute("message", "No tasks found.");
        }

        request.setAttribute("tasks", tasks);
        request.setAttribute("tags", tags);
        request.setAttribute("allusers", allusers);
        request.getRequestDispatcher("/WEB-INF/views/task-list.jsp").forward(request, response);
    }


    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        taskService.deleteTask(id);
        response.sendRedirect("tasks?action=list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Long assignedToId = Long.parseLong(request.getParameter("assignedTo"));
        Long createdById = currentUser.getId();


        String[] tagIdsArray = request.getParameterValues("tags[]");
        if (tagIdsArray == null) {
            System.out.println("No tags selected.");
        } else {
            System.out.println("Received tag IDs: " + Arrays.toString(tagIdsArray));
        }
        List<Long> tagIds = getSelectedTagIds(tagIdsArray);


        LocalDateTime dueDate = LocalDateTime.parse(request.getParameter("dueDate"));

        User assignedTo = userService.getUserById(assignedToId);
        User createdBy = userService.getUserById(createdById);
        List<Tag> selectedTags = tagService.getTagsByIds(tagIds);

        Task task;
        if (id == null || id.isEmpty()) {
            task = new Task();
            task.setCreatedAt(LocalDateTime.now());
            task.setDueDate(dueDate);
        } else {
            task = taskService.getTaskById(Long.parseLong(id));
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setAssignedTo(assignedTo);
        task.setCreatedBy(createdBy);
        task.setTags(selectedTags);
        task.setStatus(TaskStatus.PENDING);

        if (id == null || id.isEmpty()) {
            taskService.createTask(task);
        } else {
            taskService.updateTask(task);
        }

        response.sendRedirect("tasks?action=list");
    }

    private List<Long> getSelectedTagIds(String[] tagIds) {
        List<Long> selectedTagIds = new ArrayList<>();
        if (tagIds != null) {
            for (String tagId : tagIds) {
                try {
                    selectedTagIds.add(Long.parseLong(tagId));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid tag ID: " + tagId);
                }
            }
        }
        return selectedTagIds;
    }

}
