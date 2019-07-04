package by.butramyou.todolist.controller;

import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.service.TaskService;
import by.butramyou.todolist.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class MainController {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/index")
    public String index(@RequestParam(required = false, defaultValue = "") String day,
                        Model model) {
        Iterable<Task> tasks = taskRepo.findAllByCompleteFalseAndDeletedFalse();
        Date nowTime = DateUtil.setTimeToMidnight(new Date());

        if (day != null && !day.isEmpty()) {
            switch (day) {
                case "Today":
                    tasks = taskRepo.findAllByDeadlineAndCompleteFalseAndDeletedFalse(nowTime);
                    break;
                case "Tomorrow":
                    nowTime = DateUtil.getTomorrow(nowTime);
                    tasks = taskRepo.findAllByDeadlineAndCompleteFalseAndDeletedFalse(nowTime);
                    break;
                case "Someday":
                    nowTime = DateUtil.getTomorrow(nowTime);
                    tasks = taskRepo.findAllByDeadlineAfterAndCompleteFalseAndDeletedFalse(nowTime);
                    break;
                case "Deadline Missing":
                    tasks = taskRepo.findAllByDeadlineBeforeAndCompleteFalseAndDeletedFalse(nowTime);
                    break;
            }
        }
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/index/complete/{task}")
    public String setComplete(@PathVariable Task task) {
        taskService.setComplete(task);
        return "redirect:/index";
    }


}
