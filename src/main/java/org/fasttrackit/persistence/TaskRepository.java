package org.fasttrackit.persistence;

import org.fasttrackit.config.DatabaseConfiguration;
import org.fasttrackit.domain.Task;
import org.fasttrackit.transfer.CreateTaskRequest;
import org.fasttrackit.transfer.UpdateTaskRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO (Data Access Object)
public class TaskRepository {

    public void createTask(CreateTaskRequest request) throws SQLException {

        // creere Task in Baza de date
        // preventing SQL Injection using placeholders and _reparedStatement
        // conexiunea catre baza de date:
        String sql = "INSERT INTO task (description, deadline) VALUES (?,?)";

        // try with resources
        try(PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(sql)) { // sunt resurse
            // preparedStatement.close(); -> se apeleaza automat
            preparedStatement.setString(1, request.getDescription());
            preparedStatement.setDate(2, Date.valueOf(request.getDeadline()));
            // preparedStatement - protejeaza baza de date de incercari de atacuri cibernectice

            preparedStatement.executeUpdate();

            // SQL Injection -> atac malitios din partea unui utilziator asupr abazei de date
            // protectia se realizeaza prin: concatenarile inlocuite cu place holders "?" - tin locul descrierii

        }

    }

    public void deleteTask(long id) throws SQLException {
        String sql = "DELETE FROM task WHERE ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        }

    }

    public void updateTask(long id, UpdateTaskRequest request) throws SQLException {
        String sql = "UPDATE FROM task SET done = ? WHERE ID = ?";

        try(PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(sql)) {
            preparedStatement.setBoolean(1,request.isDone());
            preparedStatement.setLong(2, id);


            preparedStatement.executeUpdate();
        }
    }

    public List<Task> getTasks() throws SQLException {
        String sql = "SELECT id, description, deadline, done FROM task ";

        List<Task> tasks = new ArrayList<>();
        try(Statement statement = DatabaseConfiguration.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setDescription(resultSet.getString("description"));
                task.setDeadline(resultSet.getDate("deadline").toLocalDate());
                task.setDone(resultSet.getBoolean("done"));

                tasks.add(task);

            }
        }
        return tasks;
    }

}
