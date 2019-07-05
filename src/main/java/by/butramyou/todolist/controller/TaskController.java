package by.butramyou.todolist.controller;


import by.butramyou.todolist.domain.Attachment;
import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import by.butramyou.todolist.repos.AttachmentRepo;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.service.TaskService;
import by.butramyou.todolist.util.FileUtils;
import by.butramyou.todolist.util.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AttachmentRepo attachmentRepo;


    @GetMapping("{task}")
    public String taskDescription(@PathVariable Task task,
                                  Model model) {
        taskService.notNow(task);
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        model.addAttribute("attachment", attachment);
        model.addAttribute("task", task);
        return "taskShow";
    }

    @GetMapping("/delete-file/{task}")
    public String attachmentDelete(@PathVariable Task task, Model model) {
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        model.addAttribute("task", task);
        if (FileUtils.removeFile(attachment.getGeneratedPath(), attachment.getGeneratedName())) {
            attachmentRepo.delete(attachment);
        }
        return "redirect:/task/" + task.getId();
    }

    @GetMapping("/add")
    public String task() {
        return "addTask";
    }

    @PostMapping("/add")
    public String addTask(@AuthenticationPrincipal User user,
                          @RequestParam String topicTask,
                          @RequestParam String textTask,
                          @RequestParam String deadline, Model model,
                          @RequestParam("file") MultipartFile file) throws ParseException, IOException {

        taskService.addTask(deadline, topicTask, textTask, user, file);

        Iterable<Task> tasks = taskRepo.findAll();
        model.addAttribute("tasks", tasks);
        return "redirect:/index";
    }

    @GetMapping("/change/{task}")
    public String getOneTask(@PathVariable Task task, Model model) {
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        model.addAttribute("attachment", attachment);
        model.addAttribute("task", task);
        return "taskChange";
    }

    @PostMapping("/change/{task}")
    public String updateOneTask(@AuthenticationPrincipal User user,
                             @PathVariable Task task,
                             @RequestParam String topicTask,
                             @RequestParam(required = false, defaultValue = "") String textTask,
                             @RequestParam(required = false, defaultValue = "") String deadline,
                             Model model,
                             @RequestParam("file") MultipartFile file) throws ParseException, IOException {

        taskService.updateTask(task, topicTask, textTask, deadline, file);
        return "redirect:/task/{task}";
    }

    @GetMapping("/performed/uncomplete/{task}")
    public String setUnComplete(@PathVariable Task task) {
        taskService.setUnComplete(task);
        return "redirect:/task/performed";
    }

    @GetMapping("/performed")
    public String getPerformed(Model model) {
        Iterable<Task> tasks = taskRepo.findAllByCompleteIsTrueAndDeletedFalse();
        model.addAttribute("tasks", tasks);
        return "performed";
    }

    @GetMapping("/file-download/{task}/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile1(@PathVariable String filename, @PathVariable Task task) throws IOException {

        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filename);

        File file = new File(attachment.getGeneratedPath() + "/" + attachment.getGeneratedName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + attachment.getOriginalName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }


}

