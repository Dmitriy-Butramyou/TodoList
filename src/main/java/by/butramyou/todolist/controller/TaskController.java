package by.butramyou.todolist.controller;


import by.butramyou.todolist.domain.Attachment;
import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.repos.AttachmentRepo;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {

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

}

