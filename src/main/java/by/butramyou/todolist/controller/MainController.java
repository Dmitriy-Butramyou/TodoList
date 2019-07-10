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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                        @RequestParam(required = false, defaultValue = "") String deadline,
                        Model model) throws ParseException {
        Iterable<Task> tasks = taskRepo.findAllByCompleteFalseAndDeletedFalse();
        Date nowTime = DateUtil.setTimeToMidnight(new Date());
        String location = "All task";
        String lighting = "";

        if (day != null && !day.isEmpty()) {
            switch (day) {
                case "Today":
                    tasks = taskRepo.findAllByDeadlineAndCompleteFalseAndDeletedFalse(nowTime);
                    location = "Tasks for today";
                    break;
                case "Tomorrow":
                    nowTime = DateUtil.getTomorrow(nowTime);
                    tasks = taskRepo.findAllByDeadlineAndCompleteFalseAndDeletedFalse(nowTime);
                    location = "Tasks for tomorrow";
                    break;
                case "Deadline Missing":
                    tasks = taskRepo.findAllByDeadlineBeforeAndCompleteFalseAndDeletedFalse(nowTime);
                    location = "Tasks with a missed deadline";
                    lighting = "linear-gradient(135deg, rgba(248,80,50,1) 0%, rgba(240,96,77,1) 18%, rgba(246,41,12,1) 57%, rgba(227,45,25,1) 79%, rgba(231,56,39,1) 100%);";
                    break;
            }
        }
        if(!deadline.isEmpty()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date deadlineTime = dateFormat.parse(deadline);
            tasks = taskRepo.findAllByDeadline(deadlineTime);
            location = "Tasks for " + deadline;
        }

        model.addAttribute("lighting", lighting);
        model.addAttribute("location", location);
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/index/complete/{task}")
    public String setComplete(@PathVariable Task task) {
        taskService.setComplete(task);
        return "redirect:/index";
    }


}
