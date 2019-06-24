package by.butramyou.todolist.controller;

import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.crypto.Data;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private TaskRepo taskRepo;

    @GetMapping("/")
    public String greeting(){
        return "greeting";
    }

    @GetMapping("/index")
    public String index(Map<String, Object> model) {
        Iterable<Task> tasks = taskRepo.findAll();
        model.put("tasks", tasks);
        return "index";
    }

    @GetMapping("/addTask")
    public String add() {
        return "addTask";
    }

    @PostMapping("/addTask")
    public String add(@RequestParam String textTask, @RequestParam String deadline) {

        Task task = new Task(textTask, deadline);
        taskRepo.save(task);
        return "addTask";
    }

}
