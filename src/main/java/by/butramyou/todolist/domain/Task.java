package by.butramyou.todolist.domain;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String textTask;
    private LocalDateTime problemStatementDate;
    private String deadline;
    private LocalDateTime implementationDate;
    private boolean completeness;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User authorTask;

    public Task() {
    }

    public Task(String textTask, String deadline, User user) {
        this.authorTask = user;
        this.textTask = textTask;
        this.problemStatementDate = LocalDateTime.now();
        this.deadline = deadline;

    }

    public String getAuthorName() {
        return authorTask != null ? authorTask.getUsername() : "<none>";
    }
    public User getAuthorTask() {
        return authorTask;
    }

    public void setAuthorTask(User authorTask) {
        this.authorTask = authorTask;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextTask() {
        return textTask;
    }

    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }

    public LocalDateTime getProblemStatementDate() {
        return problemStatementDate;
    }

    public void setProblemStatementDate(LocalDateTime problemStatementDate) {
        this.problemStatementDate = problemStatementDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getImplementationDate() {
        return implementationDate;
    }

    public void setImplementationDate(LocalDateTime implementationDate) {
        this.implementationDate = implementationDate;
    }

    public boolean isCompleteness() {
        return completeness;
    }

    public void setCompleteness(boolean completeness) {
        this.completeness = completeness;
    }
}
