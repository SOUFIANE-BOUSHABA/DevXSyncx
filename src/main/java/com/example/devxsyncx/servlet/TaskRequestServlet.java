package com.example.devxsyncx.servlet;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.TaskRequest;
import com.example.devxsyncx.entities.Token;
import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.entities.enums.UserType;
import com.example.devxsyncx.service.TaskRequestService;
import com.example.devxsyncx.service.TaskService;
import com.example.devxsyncx.service.TokenService;
import com.example.devxsyncx.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/editTaskRequest")
public class TaskRequestServlet extends HttpServlet {
    private TaskService taskService;
    private TaskRequestService taskRequestService;
    private UserService userService;
    private TokenService tokenService;

    @Override
    public void init() throws ServletException {
        this.taskService = new TaskService();
        this.taskRequestService = new TaskRequestService();
        this.userService = new UserService();
        this.tokenService = new TokenService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            String taskIdParam = request.getParameter("taskId");
            if (taskIdParam != null && !taskIdParam.isEmpty()) {
                Long taskRequestId = Long.parseLong(taskIdParam);
                taskService.deleteTask(taskRequestId);
            }
            response.sendRedirect("editTaskRequest");
            return;
        }


        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser != null  && currentUser.getUserType().equals(UserType.MANAGER)) {
            List<TaskRequest> taskRequests = taskRequestService.findTaskRequestsByManagerId(currentUser.getId());
            List<User> allusers = userService.getAllUsers();
            request.setAttribute("allusers", allusers);
            request.setAttribute("taskRequests", taskRequests);
            request.getRequestDispatcher("/WEB-INF/views/taskRequests.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskIdParam = request.getParameter("taskId");
        String taskRequestIdParam = request.getParameter("taskRequestId");
        String newRequesterIdParam = request.getParameter("newRequesterId");

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        if (taskIdParam != null && !taskIdParam.isEmpty()) {
            Long taskId = Long.parseLong(taskIdParam);
            handleInsertTaskRequest(taskId, currentUser);
            response.sendRedirect("userTasks");
        } else if (taskRequestIdParam != null && !taskRequestIdParam.isEmpty() && newRequesterIdParam != null && !newRequesterIdParam.isEmpty()) {
            Long taskRequestId = Long.parseLong(taskRequestIdParam);
            Long newRequesterId = Long.parseLong(newRequesterIdParam);
            handleUpdateTaskRequest(taskRequestId, newRequesterId);
            response.sendRedirect("editTaskRequest");
        }


    }

    private void handleInsertTaskRequest(Long taskId, User currentUser) {
        Token token = tokenService.findByUserId(currentUser.getId());

        if (token.getModificationTokens() > 0) {
            Task task = taskService.getTaskById(taskId);
            User manager = task.getCreatedBy();

            if (task != null && manager != null && task.getTokenUsage() == 0) {
                TaskRequest taskRequest = new TaskRequest(task, currentUser, manager, LocalDateTime.now(), LocalDateTime.now().plusHours(12));
                task.setTokenUsage(1);
                taskService.updateTask(task);
                taskRequestService.save(taskRequest);

                token.setModificationTokens(token.getModificationTokens() - 1);
                tokenService.update(token);
            }
        }

    }

    private void handleUpdateTaskRequest(Long taskRequestId, Long newRequesterId) {
        TaskRequest taskRequest = taskRequestService.findById(taskRequestId);
        Task task = taskRequest.getTask();
        if (taskRequest != null) {
            User newRequester = userService.getUserById(newRequesterId);
            if (newRequester != null) {
                task.setAssignedTo(newRequester);
                taskService.updateTask(task);
                taskRequestService.delete(taskRequestId);
            }
        }
    }
}