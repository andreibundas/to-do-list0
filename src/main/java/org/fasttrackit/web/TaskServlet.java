package org.fasttrackit.web;

import org.fasttrackit.domain.Task;
import org.fasttrackit.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/tasks")   //  / tasks = path
public class TaskServlet extends HttpServlet {

    private final TaskService taskService = new TaskService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Task> tasks = taskService.getTasks();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    // Metode Web: HTTP METODS: citire de date : GET  ; creere de date : POST '  MODIFICARE DE DATE : PUT  ; STERERE DE DATE : DELETE


}
