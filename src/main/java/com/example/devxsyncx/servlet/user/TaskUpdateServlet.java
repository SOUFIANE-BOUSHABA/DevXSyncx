package com.example.devxsyncx.servlet.user;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.enums.TaskStatus;
import com.example.devxsyncx.service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userTasksUpdate")
public class TaskUpdateServlet extends HttpServlet {

    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        this.taskService = new TaskService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();


        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Map<String, String> requestData = gson.fromJson(reader, Map.class);


        String taskIdParam = requestData.get("id");
        String newStatusParam = requestData.get("status");

        if (taskIdParam == null || newStatusParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid input\"}");
            return;
        }

        try {
            Long taskId = Long.parseLong(taskIdParam);
            TaskStatus newStatus = TaskStatus.valueOf(newStatusParam);


            Task task = taskService.getTaskById(taskId);
            if (task != null) {
                task.setStatus(newStatus);
                taskService.updateTask(task);

                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Task status updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\": \"Task not found\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid task ID format\"}");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid task status\"}");
        }
    }
}
