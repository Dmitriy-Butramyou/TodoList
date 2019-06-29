package by.butramyou.todolist.repos;

import by.butramyou.todolist.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
    Attachment findByTaskId(Long taskId);
}
