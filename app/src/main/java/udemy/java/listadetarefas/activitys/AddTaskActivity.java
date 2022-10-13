package udemy.java.listadetarefas.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import udemy.java.listadetarefas.R;
import udemy.java.listadetarefas.databinding.ActivityAddTaskBinding;
import udemy.java.listadetarefas.helper.TaskDAO;
import udemy.java.listadetarefas.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    private ActivityAddTaskBinding binding;

    private TextInputEditText editTextTask;
    private Task taskRetrieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextTask = binding.textInputAddTask;

        //Retrieved task, case be edited
        taskRetrieved = (Task) getIntent().getSerializableExtra("taskSelected");

        //Task settings on box text
        if (taskRetrieved != null){
            editTextTask.setText(taskRetrieved.getTaskName());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId()) {
            case R.id.item_menu_save:

                TaskDAO taskDAO = new TaskDAO(getApplicationContext());

                if (taskRetrieved != null) {

                    String taskName  = editTextTask.getText().toString();

                    if (!taskName.isEmpty()){
                        Task task = new Task();
                        task.setTaskName(taskName);
                        task.setId(taskRetrieved.getId());

                        if (taskDAO.update(task)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }

                    }else {

                        String nomeTarefa  = editTextTask.getText().toString();

                        if (!nomeTarefa.isEmpty()) {
                            Task task = new Task();
                            task.setTaskName(nomeTarefa);

                            if (taskDAO.save(task)){
                                Toast.makeText(getApplicationContext(),
                                        "Sucesso ao salvar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Erro ao salvar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            }

                            finish();
                    }
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}