package by.butramyou.todolist.repos;

import by.butramyou.todolist.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findAllByCompleteFalseAndDeletedFalse();

    List<Task> findAllByDeadlineAndCompleteFalseAndDeletedFalse(Date deadline);

    List<Task> findAllByDeadlineBeforeAndCompleteFalseAndDeletedFalse(Date deadline);

    List<Task> findAllByDeadlineAfterAndCompleteFalseAndDeletedFalse(Date deadline);

    List<Task> findAllByCompleteIsTrueAndDeletedFalse();

    List<Task> findAllByDeletedTrue();

}

