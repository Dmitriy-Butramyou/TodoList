package by.butramyou.todolist.domain;

import javax.persistence.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String topicTask;
    private String textTask;
    private LocalDateTime problemStatementDate;
    private Date deadline;
    private LocalDateTime implementationDate;
    private boolean deleted;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User authorTask;

    public Task() {
    }

    public Task(String topicTask, String textTask, Date deadline, User user) throws ParseException {
        this.topicTask = topicTask;
        this.authorTask = user;
        this.textTask = textTask;
        this.problemStatementDate = LocalDateTime.now();
        this.deadline = deadline;
        this.tag = "New";
    }

    public String getTopicTask() {
        return topicTask;
    }

    public void setTopicTask(String topicTask) {
        this.topicTask = topicTask;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getImplementationDate() {
        return implementationDate;
    }

    public void setImplementationDate(LocalDateTime implementationDate) {
        this.implementationDate = implementationDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
