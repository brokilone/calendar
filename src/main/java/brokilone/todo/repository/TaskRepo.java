package brokilone.todo.repository;

import brokilone.todo.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * TaskRepo
 * created by Ksenya_Ushakova at 01.09.2020
 */
@Repository
public interface TaskRepo extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

}
