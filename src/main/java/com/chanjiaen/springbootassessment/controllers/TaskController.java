package com.chanjiaen.springbootassessment.controllers;

import com.chanjiaen.springbootassessment.exceptions.ResourceNotFoundException;
import com.chanjiaen.springbootassessment.models.Task;
import com.chanjiaen.springbootassessment.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTask();

        if (tasks.isEmpty())
            throw new ResourceNotFoundException();


        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getAllCompletedTasks() {
        List<Task> completedTasks = taskService.findAllCompletedTask();

        if(completedTasks.isEmpty())
            throw new ResourceNotFoundException();

        return ResponseEntity.ok(completedTasks);
    }

    @GetMapping("/incomplete")
    public ResponseEntity<List<Task>> getAllIncompleteTasks() {
        List<Task> incompleteTasks = taskService.findAllInCompleteTask();

        if(incompleteTasks.isEmpty())
            throw new ResourceNotFoundException();

        return ResponseEntity.ok(incompleteTasks);
    }

    @PostMapping("")
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {

        Task createdTask = taskService.createnewTask(task);

        return new ResponseEntity<>(createdTask,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        Task currentTask = taskService.findTaskById(id).map(foundTask -> {
            foundTask.setTask(task.getTask());
            foundTask.setCompleted(task.isCompleted());

            return taskService.updateTask(foundTask);
        }).orElseThrow(() -> new ResourceNotFoundException());

        return new ResponseEntity<>(currentTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        Task deletedTask = taskService.findTaskById(id).map(foundTask -> {
            taskService.deleteTask(id);

            return foundTask;
        }).orElseThrow(() -> new ResourceNotFoundException());

        return new ResponseEntity<>(deletedTask, HttpStatus.OK);
    }

}
