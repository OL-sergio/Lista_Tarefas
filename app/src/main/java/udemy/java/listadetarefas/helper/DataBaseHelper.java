package udemy.java.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String DB_NAME = "DB_TASKS";
    public static String TASKS_TABLE = "tasks";
    public static String TASKS_DELETED = "tasksDeleted";



    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlTaskCreated  = "CREATE TABLE IF NOT EXISTS " + TASKS_TABLE
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " name TEXT NOT NULL ) ";

        try {
            db.execSQL(sqlTaskCreated);
            Log.i("INFO DB", "Sucesso ao criar a Tabela tarefas" );
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a Tabela tarefas" + e.getMessage());

        }


        String sqlTasksDeleted  = "CREATE TABLE IF NOT EXISTS " + TASKS_DELETED
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " name TEXT NOT NULL ) ";

        try {
            db.execSQL(sqlTasksDeleted);
            Log.i("INFO DB", "Sucesso ao criar a Tabela tarafas apagadas" );
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a Tabela tarafas apagadas" + e.getMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sqlTasksCreated  = " DROP TABLE IF EXISTS  " + TASKS_TABLE + ";" ;

        try {
            db.execSQL(sqlTasksCreated);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao Actualizar App" );
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a Tabela" + e.getMessage());

        }

        String sqlTasksDeleted  = " DROP TABLE IF EXISTS  " + TASKS_DELETED + ";" ;

        try {
            db.execSQL(sqlTasksDeleted);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao Actualizar App" );
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a Tabela" + e.getMessage());

        }
    }
}
