package udemy.java.listadetarefas.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import udemy.java.listadetarefas.R;
import udemy.java.listadetarefas.adapter.TaskAdapter;
import udemy.java.listadetarefas.databinding.ActivityDeletedTasksBinding;
import udemy.java.listadetarefas.databinding.ActivityMainBinding;
import udemy.java.listadetarefas.helper.TaskDAO;
import udemy.java.listadetarefas.model.Task;


public class DeletedTasksActivity extends AppCompatActivity {

    private ActivityDeletedTasksBinding binding;

    private RecyclerView recyclerViewDeletedList;
    private TaskAdapter taskAdapter;
    private Task deletedTaskRetrieved;

    private List<Task> listDeletedList = new ArrayList<>();



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

    private void getDeletedList() {

        TaskDAO taskDao = new TaskDAO(getApplicationContext());
        listDeletedList = taskDao.listTasksDeleted();

        Task tasks1 = new Task();
        tasks1.setTaskName("Ir mercado");
        listDeletedList.add(tasks1);

        Task tasks2 = new Task();
        tasks2.setTaskName("Ir a feira");
        listDeletedList.add(tasks2);

        taskAdapter = new TaskAdapter(listDeletedList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewDeletedList.setLayoutManager(layoutManager);
        recyclerViewDeletedList.setHasFixedSize(true);
        recyclerViewDeletedList.addItemDecoration( new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL ));
        recyclerViewDeletedList.setAdapter(taskAdapter);
    }


    @Override
    protected void onStart() {
        getDeletedList();
        super.onStart();
    }
}