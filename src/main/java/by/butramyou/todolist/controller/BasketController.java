package by.butramyou.todolist.controller;


import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BasketController {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @GetMapping("/basket")
    public String basket(@AuthenticationPrincipal User currentUser, Model model) {
        Iterable<Task> tasks = taskRepo.findAllByDeletedTrueAndAuthorTask(currentUser);
        model.addAttribute("tasks", tasks);
        return "basket";
    }

    @GetMapping("/basket/delete/{task}")
    public String finishDeleteTask(@PathVariable Task task) {
        taskService.finishDeleteTask(task);
        return "redirect:/basket";
    }

    @GetMapping("/index/delete/{task}")
    public String deleteTask(@PathVariable Task task) {
        taskService.deleteTask(task);
        return "redirect:/index";
    }

    @GetMapping("/basket/restore/{task}")
    public String restoreTask(@PathVariable Task task) {
        taskService.restoreTask(task);
        return "redirect:/basket";
    }

    @GetMapping("/basket/deleteAll")
    public String deleteAll(@AuthenticationPrincipal User user) {
        taskService.deleteAll(user);
        return "redirect:/basket";
    }


}
