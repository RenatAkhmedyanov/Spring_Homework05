package ru.akhmedyanov.homework05.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.akhmedyanov.homework05.model.Task;
import ru.akhmedyanov.homework05.model.TaskStatus;
import ru.akhmedyanov.homework05.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;

    /**
     * Добавить новую задачу
     * @param task
     * @return
     */
    @PostMapping
    public Task addTask(@RequestBody Task task){
        task.setCreationDate(LocalDateTime.now());
        task.setStatus(TaskStatus.NOT_STARTED);
        return taskRepository.save(task);
    }

    /**
     * Получить список задач
     * @return
     */
    @GetMapping
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    /**
     * Получить задачи по статусу
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus (@PathVariable TaskStatus status) {
        return taskRepository.getTasksByStatus(status);
    }

    /**
     * Изменение статуса задачи
     * @param id
     * @param task
     * @return
     */
    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id, @RequestBody Task task) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task updatedTask = optionalTask.get();
            updatedTask.setStatus(task.getStatus());
            return taskRepository.save(updatedTask);
        } else {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
    }

    /**
     * Удаление задачи
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }


}
