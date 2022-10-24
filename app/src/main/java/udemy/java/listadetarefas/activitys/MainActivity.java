package udemy.java.listadetarefas.activitys;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import udemy.java.listadetarefas.R;
import udemy.java.listadetarefas.activitys.AddTaskActivity;
import udemy.java.listadetarefas.adapter.TaskAdapter;
import udemy.java.listadetarefas.databinding.ActivityMainBinding;
import udemy.java.listadetarefas.helper.DataBaseHelper;
import udemy.java.listadetarefas.helper.RecyclerItemClickListener;
import udemy.java.listadetarefas.helper.TaskDAO;
import udemy.java.listadetarefas.model.Task;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;

    private RecyclerView recyclerView;
    private TaskAdapter tasksAdapter;
    private Task taskSelected;

    private List<Task> listTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //Confirurar recycler
         recyclerView = binding.recyclerViewTasks;

        /*
        *
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());

        ContentValues cv  = new ContentValues();
        cv.put("name" , "Teste" );

        dataBaseHelper.getWritableDatabase().insert("tasks", null, cv);
        */

        //Adicionar evento de click
        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onItemClick(View view, int position) {
                                //Retrieve task fot edit
                                Task taskSelected = listTasks.get(position);

                                //Send task data for activity add task
                                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                                intent.putExtra("taskSelected", taskSelected);
                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                taskSelected = listTasks.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa " + taskSelected.getTaskName() + "?" );
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TaskDAO taskDAO = new TaskDAO(getApplicationContext());

                                        Task task = new Task();
                                        task.setTaskDeleteName(taskSelected.getTaskName());
                                        taskDAO.save(task);

                                            if (taskDAO.delete(taskSelected)){
                                                getTaskList();
                                                Toast.makeText(getApplicationContext(),
                                                        "Sucesso ao apagar tarefa!",
                                                        Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getApplicationContext(),
                                                        "Erro ao apagar tarefa!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                    }
                                });
                                dialog.setNegativeButton("Não", null);
                                dialog.show();
                            }
                        }
                )
        );

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ) {

            case R.id.item_menu_save:
                Toast.makeText(MainActivity.this,
                        "Guardar item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item_delete_all_tasks:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Confirmar exclusão.");
                dialog.setMessage("Deseja apagar todas tarefas!");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                        if (taskDAO.listTasksDeleted() != null) {
                            taskDAO.deletingAllTasks();
                            getTaskList();
                            Toast.makeText(MainActivity.this,
                                    "Todas as tarefas apagadas!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Erro apagar tarefas!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não", null);
                dialog.show();

                break;

            case R.id.item_menu_config:
                Toast.makeText(MainActivity.this,
                        "Configuração item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item_menu_deletedTasks:
                Intent intent = new Intent( getApplicationContext(), DeletedTasksActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    public void getTaskList() {

        //List tasks
        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
        listTasks = taskDAO.listTasks();

        /**
        Task tasks1 = new Task();
        tasks1.setTaskName("Ir mercado");
        listTasks.add(tasks1);

        Task tasks2 = new Task();
        tasks2.setTaskName("Ir a feira");
        listTasks.add(tasks2);
        */

        //setting adapter
        tasksAdapter = new TaskAdapter(listTasks);

        //Seating RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration( new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL ));
        recyclerView.setAdapter(tasksAdapter);
    }

    @Override
    protected void onStart() {
        getTaskList();
        super.onStart();
    }

}