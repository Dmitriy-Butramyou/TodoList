package by.butramyou.todolist.repos;

import by.butramyou.todolist.domain.Attachment;
import by.butramyou.todolist.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
    Attachment findAllByTaskId(Task taskId);
}
