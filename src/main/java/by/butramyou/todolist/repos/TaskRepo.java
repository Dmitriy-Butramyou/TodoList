package by.butramyou.todolist.repos;

import by.butramyou.todolist.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {

    List<Task> findAllByTag(String tag);
}

