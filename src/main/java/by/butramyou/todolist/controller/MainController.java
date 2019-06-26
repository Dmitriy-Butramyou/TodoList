package by.butramyou.todolist.controller;

import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.util.DateUtil;
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
import java.time.LocalDateTime;
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
    public String index(@RequestParam(required = false, defaultValue = "") String filter,
                        @RequestParam(required = false, defaultValue = "") String day,
                        Model model) {
        Iterable<Task> tasks = taskRepo.findAll();
        Date nowTime = DateUtil.setTimeToMidnight(new Date());


        if(filter != null && !filter.isEmpty()) {
            tasks = taskRepo.findAllByTag(filter);
        } else {
            tasks = taskRepo.findAll();
        }

        if(day != null && !day.isEmpty()) {
            if(day.equals("Today")){
                tasks = taskRepo.findAllByDeadline(nowTime);
            } else if (day.equals("Tomorrow")){
                nowTime = DateUtil.getTomorrow(nowTime);
                tasks = taskRepo.findAllByDeadline(nowTime);
            } else  if (day.equals("Someday")) {
                nowTime = DateUtil.getTomorrow(nowTime);
                tasks = taskRepo.findAllByDeadlineAfter(nowTime);
            } else  if (day.equals("Deadline Missing")) {
                tasks = taskRepo.findAllByDeadlineBefore(nowTime);
            }
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        return "index";
    }


//    @GetMapping("/addTask")
//    public String add(Map<String, Object> model) {
//        Iterable<Task> tasks = taskRepo.findAll();
//        model.put("tasks", tasks);
//        return "addTask";
//    }

    @PostMapping("/index")
    public String addAll(@AuthenticationPrincipal User user,
                      @RequestParam String textTask,
                      @RequestParam String deadline, Map<String, Object> model) throws ParseException {
        Date nowTime = DateUtil.setTimeToMidnight(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date deadlineTime = dateFormat.parse(deadline);
        if (deadlineTime.after(nowTime) || deadlineTime.equals(nowTime)){
            Task task = new Task(textTask, deadlineTime, user);
            taskRepo.save(task);
            Iterable<Task> tasks = taskRepo.findAll();
            model.put("tasks", tasks);
        }
        Iterable<Task> tasks = taskRepo.findAll();
        model.put("tasks", tasks);
        return "index";
    }


//    @PostMapping("/addTask")
//    public String add(@AuthenticationPrincipal User user,
//                      @RequestParam String textTask,
//                      @RequestParam String deadline, Map<String, Object> model) {
//        int result = comparisonTime(deadline);
//        if (result == 1){
//            Task task = new Task(textTask, deadline, user);
//            taskRepo.save(task);
//            Iterable<Task> tasks = taskRepo.findAll();
//            model.put("tasks", tasks);
//        }
//        Iterable<Task> tasks = taskRepo.findAll();
//        model.put("tasks", tasks);
//        return "addTask";
//    }

}
