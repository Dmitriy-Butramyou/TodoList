package by.butramyou.todolist.controller;

import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private TaskRepo taskRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(){
        return "greeting";
    }

    @GetMapping("/index")
    public String index(@RequestParam(required = false, defaultValue = "") String filter,
                        @RequestParam(required = false, defaultValue = "") String day,
                        Model model) {
        Iterable<Task> tasks = taskRepo.findAll();
        String lighting = "";
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
                lighting = "linear-gradient(135deg, rgba(248,80,50,1) 0%, rgba(240,96,77,1) 18%, rgba(246,41,12,1) 57%, rgba(227,45,25,1) 79%, rgba(231,56,39,1) 100%);";
            }
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        model.addAttribute("lighting", lighting);
        return "index";
    }

    @PostMapping("/index")
    public String addAll(@AuthenticationPrincipal User user,
                      @RequestParam String topicTask,
                      @RequestParam String textTask,
                      @RequestParam String deadline, Model model,
                      @RequestParam("file") MultipartFile file
    ) throws ParseException, IOException {

        Date nowTime = DateUtil.setTimeToMidnight(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date deadlineTime = dateFormat.parse(deadline);

        if (deadlineTime.after(nowTime) || deadlineTime.equals(nowTime)){
            Task task = new Task(topicTask, textTask, deadlineTime, user);

            //add file
            if(file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()){
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                task.setFilename(resultFilename);
            }

            taskRepo.save(task);
            Iterable<Task> tasks = taskRepo.findAll();
            model.addAttribute("tasks", tasks);
        }
        Iterable<Task> tasks = taskRepo.findAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }
}
