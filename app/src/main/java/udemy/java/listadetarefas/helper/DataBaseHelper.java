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


    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql  = "CREATE TABLE IF NOT EXISTS " + TASKS_TABLE
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " name TEXT NOT NULL ) ";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a Tabela" );
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a Tabela" + e.getMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql  = " DROP TABLE IF EXISTS  " + TASKS_TABLE + ";" ;

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao Actualizar App" );
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a Tabela" + e.getMessage());

        }
    }
}
