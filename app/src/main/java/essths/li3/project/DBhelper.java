package essths.li3.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public DBhelper(Context context) {
        super(context, "app.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE user(id_user INTEGER PRIMARY KEY AUTOINCREMENT , username varchar(50), email varchar(50) , password varchar(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS user");
    }

    public Boolean insert_user(String username , String email , String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username" , username);
        contentValues.put("email" , email);
        contentValues.put("password" , password);
        long result = DB.insert("user", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean check_user (String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM user WHERE username = ? AND password = ? ", new String[]{username, password});
        if (cursor.getCount() == 0){
            return false;
        }else{
            return true;
        }
    }
}
