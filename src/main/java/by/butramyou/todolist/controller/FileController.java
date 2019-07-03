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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/task")
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AttachmentRepo attachmentRepo;

    @GetMapping("/change/{task}")
    public String getTask(@PathVariable Task task, Model model) {
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        model.addAttribute("attachment", attachment);
        model.addAttribute("task", task);
        return "taskChange";
    }


    @PostMapping("/change/{task}")
    public String updateTask(@AuthenticationPrincipal User user,
                             @PathVariable Task task,
                             @RequestParam Map<String,String> tag,
                             @RequestParam String topicTask,
                             @RequestParam(required = false, defaultValue = "") String textTask,
                             @RequestParam(required = false, defaultValue = "") String deadline,
                             Model model,
                             @RequestParam("file") MultipartFile file) throws ParseException, IOException {
        Attachment attachment = attachmentRepo.findAllByTaskId(task);

        Date nowTime = DateUtil.setTimeToMidnight(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        if(!deadline.isEmpty()) {
            Date deadlineTime = dateFormat.parse(deadline);
            if (deadlineTime.after(nowTime) || deadlineTime.equals(nowTime)) {
                task.setDeadline(deadlineTime);
            }
        }

        task.setTopicTask(topicTask);

        if(!textTask.isEmpty()) {
            task.setTextTask(textTask);
        }

        taskRepo.save(task);

            //add file
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                if(attachment != null) {
                    if (FileUtils.removeFile(attachment.getGeneratedPath(), attachment.getGeneratedName())) {
                        attachmentRepo.delete(attachment);
                    }
                }

                String generatedPath = uploadPath + FileUtils.getPath();
                File uploadDir = new File(generatedPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(generatedPath + "/" + resultFilename));
                Attachment reAttachment = new Attachment(file.getOriginalFilename(), generatedPath, resultFilename, task);

                attachmentRepo.save(reAttachment);
            }


        return "redirect:/task/{task}";
    }


}
