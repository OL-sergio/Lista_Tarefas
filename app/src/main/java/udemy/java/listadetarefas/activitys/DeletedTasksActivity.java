package udemy.java.listadetarefas.activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import udemy.java.listadetarefas.R;
import udemy.java.listadetarefas.adapter.TaskAdapter;

import udemy.java.listadetarefas.databinding.ActivityDeletedTasksBinding;
import udemy.java.listadetarefas.helper.DataBaseHelper;
import udemy.java.listadetarefas.helper.TaskDAO;
import udemy.java.listadetarefas.model.Task;


public class DeletedTasksActivity extends AppCompatActivity {

    private ActivityDeletedTasksBinding binding;

    private RecyclerView recyclerViewDeletedList;
    private TaskAdapter taskAdapter;


    private List<Task> listDeletedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDeletedTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Tarefas apagadas");

        recyclerViewDeletedList = binding.recyclerViewDeletedTasks;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deleted_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.item_menu_save:
                AlertDialog.Builder dialog = new AlertDialog.Builder(DeletedTasksActivity.this);
                dialog.setTitle("Confirmar exclusão.");
                dialog.setMessage("Deseja apagar todas tarefas!");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskDAO taskDAO = new TaskDAO(getApplicationContext());

                        if (taskDAO.listTasksDeleted() != null) {
                                taskDAO.deletingAllTasksDeleted();
                                getDeletedList();
                            Toast.makeText(DeletedTasksActivity.this,
                                    "Todas as tarefas apagadas!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DeletedTasksActivity.this,
                                    "Erro apagar tarefas!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não", null);
                dialog.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDeletedList() {

        TaskDAO taskDao = new TaskDAO(getApplicationContext());
        listDeletedList = taskDao.listTasksDeleted();

        /*
        *
        Task tasks1 = new Task();
        tasks1.setTaskDeleteName("Ir mercado");
        listDeletedList.add(tasks1);

        Task tasks2 = new Task();
        tasks2.setTaskDeleteName("Ir a feira");
        listDeletedList.add(tasks2);
        * */

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