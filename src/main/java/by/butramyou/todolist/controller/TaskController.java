package by.butramyou.todolist.controller;


import by.butramyou.todolist.domain.Attachment;
import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.repos.AttachmentRepo;
import by.butramyou.todolist.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AttachmentRepo attachmentRepo;

    @GetMapping("{task}")
    public String taskDescription(@PathVariable Task task,
                                  Model model){

        task.setTag("");
        taskRepo.save(task);
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        model.addAttribute("attachment", attachment);
        model.addAttribute("task", task);
        return "taskShow";
    }

    @GetMapping("/change/{task}")
    public String taskChange(@PathVariable Task task, Model model) {
        model.addAttribute("task", task);
        return "taskChange";
    }
}

