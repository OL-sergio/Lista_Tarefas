package udemy.java.listadetarefas.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import udemy.java.listadetarefas.R;
import udemy.java.listadetarefas.adapter.TaskAdapter;
import udemy.java.listadetarefas.databinding.ActivityDeletedTasksBinding;
import udemy.java.listadetarefas.databinding.ActivityMainBinding;
import udemy.java.listadetarefas.model.Task;


public class DeletedTasksActivity extends AppCompatActivity {

    private ActivityDeletedTasksBinding binding;

    private RecyclerView recyclerViewDeletedList;
    private TaskAdapter taskAdapter;
    private Task deletedTaskRetrieved;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDeletedTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerViewDeletedList = binding.recyclerViewDeletedTasks;

        deletedTaskRetrieved = (Task) getIntent().getSerializableExtra("deletedTask");

        if (deletedTaskRetrieved != null){
          binding.textViewDeleted.setText(deletedTaskRetrieved.getTaskName());
        }

    }
}