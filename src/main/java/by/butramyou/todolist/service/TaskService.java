package by.butramyou.todolist.service;


import by.butramyou.todolist.domain.Attachment;
import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.repos.AttachmentRepo;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.util.DateUtil;
import by.butramyou.todolist.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private AttachmentRepo attachmentRepo;


    public void setComplete(Task task) {
        task.setComplete(true);
        taskRepo.save(task);
    }

    public void setUnComplete(Task task) {
        task.setComplete(false);
        taskRepo.save(task);
    }

    public void finishDeleteTask(Task task) {
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        if (attachment != null) {
            if (FileUtils.removeFile(attachment.getGeneratedPath(), attachment.getGeneratedName())) {
                attachmentRepo.delete(attachment);
            }
        }
        taskRepo.delete(task);
    }

    public void deleteTask(Task task) {
        task.setDeleted(true);
        taskRepo.save(task);
    }

    public void restoreTask(Task task) {
        task.setDeleted(false);
        task.setDeadline(DateUtil.setTimeToMidnight(new Date()));
        taskRepo.save(task);
    }

    public void deleteAll() {
        Iterable<Task> tasks = taskRepo.findAllByDeletedTrue();
        for (Task task : tasks) {
            Attachment attachment = attachmentRepo.findAllByTaskId(task);
            if (attachment != null) {
                if (FileUtils.removeFile(attachment.getGeneratedPath(), attachment.getGeneratedName())) {
                    attachmentRepo.delete(attachment);
                }
            }
            taskRepo.delete(task);
        }
    }
}
