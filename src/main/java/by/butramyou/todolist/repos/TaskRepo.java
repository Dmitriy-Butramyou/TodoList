package by.butramyou.todolist.repos;

import by.butramyou.todolist.domain.Task;
import by.butramyou.todolist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findAllByCompleteFalseAndDeletedFalseAndAuthorTask(User user);

    List<Task> findAllByDeadlineAndCompleteFalseAndDeletedFalseAndAuthorTask(Date deadline, User user);

    List<Task> findAllByDeadlineBeforeAndCompleteFalseAndDeletedFalseAndAuthorTask(Date deadline, User user);

    List<Task> findAllByDeadlineAndAuthorTask(Date deadline, User user);

    List<Task> findAllByCompleteIsTrueAndDeletedFalseAndAuthorTask(User user);

    List<Task> findAllByDeletedTrueAndAuthorTask(User user);

}

