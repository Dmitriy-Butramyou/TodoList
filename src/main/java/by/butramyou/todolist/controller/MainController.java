package by.butramyou.todolist.controller;

import by.butramyou.todolist.domain.Attachment;
import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import by.butramyou.todolist.repos.AttachmentRepo;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.service.TaskService;
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
public class MainController {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AttachmentRepo attachmentRepo;


    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/index")
    public String index(
//            @RequestParam(required = false, defaultValue = "") String filter,
                        @RequestParam(required = false, defaultValue = "") String day,
                        Model model) {
        Iterable<Task> tasks = taskRepo.findAllByCompleteFalseAndDeletedFalse();
        Date nowTime = DateUtil.setTimeToMidnight(new Date());

//        if (filter != null && !filter.isEmpty()) {
//            tasks = taskRepo.findAllByTag(filter);
//        } else {
//            tasks = taskRepo.findAll();
//        }

        if (day != null && !day.isEmpty()) {
            if (day.equals("Today")) {
                tasks = taskRepo.findAllByDeadlineAndCompleteFalseAndDeletedFalse(nowTime);
            } else if (day.equals("Tomorrow")) {
                nowTime = DateUtil.getTomorrow(nowTime);
                tasks = taskRepo.findAllByDeadlineAndCompleteFalseAndDeletedFalse(nowTime);
            } else if (day.equals("Someday")) {
                nowTime = DateUtil.getTomorrow(nowTime);
                tasks = taskRepo.findAllByDeadlineAfterAndCompleteFalseAndDeletedFalse(nowTime);
            } else if (day.equals("Deadline Missing")) {
                tasks = taskRepo.findAllByDeadlineBeforeAndCompleteFalseAndDeletedFalse(nowTime);
            } else if (day.equals("Performed")) {
                tasks = taskRepo.findAllByCompleteIsTrueAndDeletedFalse();
            }
        }
        model.addAttribute("tasks", tasks);
//        model.addAttribute("filter", filter);
        return "index";
    }

    @GetMapping("/index/complete/{task}")
    public String setComplete(@PathVariable Task task) {


        taskService.setComplete(task);

        return "redirect:/index";
    }

    @GetMapping("/index/uncomplete/{task}")
    public String setUnComplete(@PathVariable Task task) {

        taskService.setUnComplete(task);

        return "redirect:/index";
    }



}
