package by.butramyou.todolist.service;


import by.butramyou.todolist.domain.Attachment;
import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import by.butramyou.todolist.repos.AttachmentRepo;
import by.butramyou.todolist.repos.TaskRepo;
import by.butramyou.todolist.util.DateUtil;
import by.butramyou.todolist.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class TaskService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AttachmentRepo attachmentRepo;

    public void setComplete(Task task) {
        task.setImplementationDate(LocalDateTime.now());
        task.setComplete(true);
        task.setTag(false);
        taskRepo.save(task);
    }

    public void setUnComplete(Task task) {
        task.setComplete(false);
        task.setTag(true);
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
        task.setTag(false);
        taskRepo.save(task);
    }

    public void restoreTask(Task task) {
        task.setDeleted(false);
        task.setTag(true);
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

    public void notNow(Task task) {
        task.setTag(false);
        taskRepo.save(task);
    }

    public void addTask(String deadline, String topicTask, String textTask, User user, MultipartFile file) throws ParseException, IOException {
        Date nowTime = DateUtil.setTimeToMidnight(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date deadlineTime = dateFormat.parse(deadline);

        if (deadlineTime.after(nowTime) || deadlineTime.equals(nowTime)) {
            Task task = new Task(topicTask, textTask, deadlineTime, user);
            taskRepo.save(task);

            if (file != null && !file.getOriginalFilename().isEmpty()) {
                addFile(file, task);
            }
        }
    }

    private void addFile(MultipartFile file, Task task) throws IOException {
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

    public void updateTask(Task task, String topicTask, String textTask, String deadline, MultipartFile file) throws ParseException, IOException {
        Attachment attachment = attachmentRepo.findAllByTaskId(task);
        Date nowTime = DateUtil.setTimeToMidnight(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (!deadline.isEmpty()) {
            Date deadlineTime = dateFormat.parse(deadline);
            if (deadlineTime.after(nowTime) || deadlineTime.equals(nowTime)) {
                task.setDeadline(deadlineTime);
            }
        }
        task.setTopicTask(topicTask);
        if (!textTask.isEmpty()) {
            task.setTextTask(textTask);
        }
        taskRepo.save(task);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            if (attachment != null) {
                if (FileUtils.removeFile(attachment.getGeneratedPath(), attachment.getGeneratedName())) {
                    attachmentRepo.delete(attachment);
                }
            }
            addFile(file, task);
        }
    }
}
