package udemy.java.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import udemy.java.listadetarefas.model.Task;

public class TaskDAO implements ITaskDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;


    public TaskDAO(Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        write = dataBaseHelper.getWritableDatabase();
        read = dataBaseHelper.getReadableDatabase();
    }

    @Override
    public boolean save(Task task) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", task.getTaskName());

        try {
            write.insert(DataBaseHelper.TASKS_TABLE, null, contentValues );
            Log.i("INFO DB", "Tarefa sava com sucesso!" );

        } catch (Exception e){
            Log.i("INFO DB", "Erro ao salvar tarefa" + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Task task) {
        return false;
    }

    @Override
    public boolean delete(Task task) {
        return false;
    }

    @Override
    public List<Task> listTasks() {
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM " + DataBaseHelper.TASKS_TABLE + " ;" ;
        Cursor cursor =  read.rawQuery (sql , null);
        while (cursor.moveToNext()) {

            Task task = new Task();

            Long id = cursor.getLong( cursor.getColumnIndex("id") );
            String nameTask = cursor.getString( cursor.getColumnIndex("name") );

            task.setId(id);
            task.setTaskName(nameTask);

            tasks.add(task);
        }

        return tasks;
    }
}
