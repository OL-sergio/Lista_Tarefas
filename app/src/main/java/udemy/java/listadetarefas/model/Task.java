package udemy.java.listadetarefas.model;

import java.io.Serializable;

public class Task implements Serializable {

    private Long id;
    private String taskName;
    private String taskDeleteName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDeleteName() {return taskDeleteName;}

    public void setTaskName(String taskName ) {
        this.taskName = taskName;

    }
    public  void  setTaskDeleteName (String taskDeleteName) {
        this.taskDeleteName = taskDeleteName;
    }
}
