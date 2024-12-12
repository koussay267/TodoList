package essths.li3.project.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import essths.li3.project.model.ToDoModel;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final int DATABASE_VERSION = 1;
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";

    public DataBaseHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertTask(ToDoModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, model.getTask());
        contentValues.put(COL_3, 0); // Default status is 0
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateTask(int id , String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, task);
        db.update(TABLE_NAME, contentValues, "ID+?", new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, status);
        db.update(TABLE_NAME, contentValues, "ID+?", new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});

    }
    public List<ToDoModel> getAllTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        ToDoModel ToDoModel = new ToDoModel();
                        ToDoModel.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        ToDoModel.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        ToDoModel.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        modelList.add(ToDoModel);

                    }while(cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
