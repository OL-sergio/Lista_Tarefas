package udemy.java.listadetarefas.helper;

import java.util.List;

import udemy.java.listadetarefas.model.Task;

public interface ITaskDAO {

    public boolean save (Task task);
    public boolean update (Task task);
    public boolean delete (Task task);
    public List<Task> listTasks();
}
