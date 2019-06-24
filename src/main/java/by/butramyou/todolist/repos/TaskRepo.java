package by.butramyou.todolist.repos;

import by.butramyou.todolist.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepo extends CrudRepository<Task, Integer> {
    // TODO: 24.06.2019 X3
//    List<Task> findAllByCompletenessNot(boolean b);
}

