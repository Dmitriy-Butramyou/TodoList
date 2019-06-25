package by.butramyou.todolist.controller;

import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import by.butramyou.todolist.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    public String add(Map<String, Object> model) {
        Iterable<Task> tasks = taskRepo.findAll();
        model.put("tasks", tasks);
        return "addTask";
    }

    @PostMapping("/addTask")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String textTask,
                      @RequestParam String deadline, Map<String, Object> model) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        int nowInt = Integer.parseInt(dateFormat.format(now).replace("/", ""));
        int deadlineInt = Integer.parseInt(deadline.replace("-", ""));
        if (deadlineInt > nowInt){
            Task task = new Task(textTask, deadline, user);
            taskRepo.save(task);
            Iterable<Task> tasks = taskRepo.findAll();
            model.put("tasks", tasks);
        }
        Iterable<Task> tasks = taskRepo.findAll();
        model.put("tasks", tasks);
        return "addTask";
    }

}
