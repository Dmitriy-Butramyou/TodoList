package by.butramyou.todolist.repos;

import by.butramyou.todolist.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findAllByTag(String tag);
    List<Task> findAllByDeadline(Date deadline);
    List<Task> findAllByDeadlineBefore(Date deadline);
    List<Task> findAllByDeadlineAfter(Date deadline);
}

