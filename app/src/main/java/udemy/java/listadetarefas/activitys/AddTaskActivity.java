package udemy.java.listadetarefas.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import udemy.java.listadetarefas.R;
import udemy.java.listadetarefas.helper.TaskDAO;
import udemy.java.listadetarefas.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText editTextTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTask = findViewById(R.id.textInput_addTask);

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

                String nomeTarefa  = editTextTask.getText().toString();
                if (!nomeTarefa.isEmpty()) {
                    Task task = new Task();
                    task.setTaskName(nomeTarefa);
                    taskDAO.save(task);
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}