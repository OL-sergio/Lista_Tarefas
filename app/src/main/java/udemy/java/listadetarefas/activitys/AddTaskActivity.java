package udemy.java.listadetarefas.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import udemy.java.listadetarefas.R;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }
}