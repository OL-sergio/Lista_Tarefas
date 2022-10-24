package udemy.java.listadetarefas.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import udemy.java.listadetarefas.model.Task;

public class TaskDAO implements ITaskDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;
    private final SQLiteDatabase delete;


    public TaskDAO(Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        write = dataBaseHelper.getWritableDatabase();
        read = dataBaseHelper.getReadableDatabase();
        delete = dataBaseHelper.getReadableDatabase();
    }

    @Override
    public boolean save(Task task) {

        ContentValues tasksValues = new ContentValues();
        tasksValues.put("name", task.getTaskName());

        try {
            write.insert(DataBaseHelper.TASKS_TABLE, null, tasksValues);
            Log.i("INFO DB", "Tarefa salva com sucesso!");

        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao salvar tarefa" + e.getMessage());
            return false;
        }


        ContentValues deletedValues = new ContentValues();
        deletedValues.put("name", task.getTaskDeleteName());

        try {
            write.insert(DataBaseHelper.TASKS_DELETED, null, deletedValues);
            Log.i("INFO DB", "Tarefa salva com sucesso!");

        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao salvar tarefa" + e.getMessage());
            return false;
        }

        return true;
    }


    @Override
    public boolean update(Task task) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", task.getTaskName());

        try {
            String[] args = {task.getId().toString()};
            write.update(DataBaseHelper.TASKS_TABLE, contentValues, "id=?", args);
            Log.i("INFO DB", "Tarefa atualizada com sucesso!");

        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao atualizar tarefa" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Task task) {

        try {
            String[] args = {task.getId().toString()};
            read.delete(DataBaseHelper.TASKS_TABLE, "id=?", args);
            Log.i("INFO DB", "Tarefa removida com sucesso!");

        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao remover a tarefa!" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Task> listTasks() {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM " + DataBaseHelper.TASKS_TABLE + " ;";
        Cursor cursor = read.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            Task task = new Task();

            @SuppressLint("Range") Long id = cursor.getLong(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String nameTask = cursor.getString(cursor.getColumnIndex("name"));

            task.setId(id);
            task.setTaskName(nameTask);

            tasks.add(task);
        }

        cursor.close();

        return tasks;
    }

    public List<Task> listTasksDeleted() {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM " + DataBaseHelper.TASKS_DELETED + " ;";
        Cursor cursor = read.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            Task task = new Task();

            @SuppressLint("Range") Long id = cursor.getLong(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String nameTask = cursor.getString(cursor.getColumnIndex("name"));

            task.setId(id);
            task.setTaskName(nameTask);

            tasks.add(task);
        }

        cursor.close();

        return tasks;
    }
    public void deletingAllTasksDeleted() {
        String sqlTasksCreated = " DELETE FROM " + DataBaseHelper.TASKS_DELETED + ";";
        try {
            delete.execSQL(sqlTasksCreated);
            Log.i("INFO DB", "Sucesso ao Actualizar App");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar a Tabela" + e.getMessage());

        }
    }

    public void deletingAllTasks() {
        String sqlTasksCreated = " DELETE FROM " + DataBaseHelper.TASKS_TABLE + ";";
        try {
            delete.execSQL(sqlTasksCreated);
            Log.i("INFO DB", "Sucesso ao Actualizar App");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar a Tabela" + e.getMessage());

        }
    }

}
