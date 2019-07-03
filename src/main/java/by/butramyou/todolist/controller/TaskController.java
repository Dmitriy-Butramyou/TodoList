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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Value("${upload.path}")
    private String uploadPath;


    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AttachmentRepo attachmentRepo;


    @GetMapping("{task}")
    public String taskDescription(@PathVariable Task task,
                                  Model model) {
        task.setTag("");
        taskRepo.save(task);
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        model.addAttribute("attachment", attachment);
        model.addAttribute("task", task);
        return "taskShow";
    }

    @PostMapping("/{task}")
    public String attachmentDelete(@PathVariable Task task, Model model) {
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        model.addAttribute("task", task);
        if (FileUtils.removeFile(attachment.getGeneratedPath(), attachment.getGeneratedName())) {
            attachmentRepo.delete(attachment);
        }
        return "taskShow";
    }

    @GetMapping("/add")
    public String task(){
        return "addTask";
    }

    @PostMapping("/add")
    public String addTask(@AuthenticationPrincipal User user,
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

//            Iterable<Task> tasks = taskRepo.findAll();
//            model.addAttribute("tasks", tasks);
        }
        Iterable<Task> tasks = taskRepo.findAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }



}

