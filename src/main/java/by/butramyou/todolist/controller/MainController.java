package by.butramyou.todolist.controller;

import by.butramyou.todolist.domain.Attachment;
import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import by.butramyou.todolist.repos.AttachmentRepo;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.util.DateUtil;
import by.butramyou.todolist.util.FileUtils;
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

    @Autowired
    private AttachmentRepo attachmentRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/index")
    public String index(@RequestParam(required = false, defaultValue = "") String filter,
                        @RequestParam(required = false, defaultValue = "") String day,
                        Model model) {
        Iterable<Task> tasks = taskRepo.findAll();
        Date nowTime = DateUtil.setTimeToMidnight(new Date());

        if (filter != null && !filter.isEmpty()) {
            tasks = taskRepo.findAllByTag(filter);
        } else {
            tasks = taskRepo.findAll();
        }

        if (day != null && !day.isEmpty()) {
            if (day.equals("Today")) {
                tasks = taskRepo.findAllByDeadline(nowTime);
            } else if (day.equals("Tomorrow")) {
                nowTime = DateUtil.getTomorrow(nowTime);
                tasks = taskRepo.findAllByDeadline(nowTime);
            } else if (day.equals("Someday")) {
                nowTime = DateUtil.getTomorrow(nowTime);
                tasks = taskRepo.findAllByDeadlineAfter(nowTime);
            } else if (day.equals("Deadline Missing")) {
                tasks = taskRepo.findAllByDeadlineBefore(nowTime);
            }
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/index")
    public String addAll(@AuthenticationPrincipal User user,
                         @RequestParam String topicTask,
                         @RequestParam String textTask,
                         @RequestParam String deadline, Model model,
                         @RequestParam("file") MultipartFile file) throws ParseException, IOException {

        Date nowTime = DateUtil.setTimeToMidnight(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date deadlineTime = dateFormat.parse(deadline);

        if (deadlineTime.after(nowTime) || deadlineTime.equals(nowTime)) {
            Task task = new Task(topicTask, textTask, deadlineTime, user);
            taskRepo.save(task);

            //add file
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                String generatedPath = uploadPath + FileUtils.getPath();
                File uploadDir = new File(generatedPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(generatedPath + "/" + resultFilename));
                Attachment attachment = new Attachment(file.getOriginalFilename(), generatedPath, resultFilename, task);

                attachmentRepo.save(attachment);
            }

                Iterable<Task> tasks = taskRepo.findAll();
                model.addAttribute("tasks", tasks);
            }
            Iterable<Task> tasks = taskRepo.findAll();
            model.addAttribute("tasks", tasks);
            return "index";
        }

}
