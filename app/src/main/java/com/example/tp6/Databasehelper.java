package com.example.tp6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class Databasehelper extends SQLiteOpenHelper {
    private Context context;
    final static String DataBase_name = "Student.db";
    final static int DataBase_version = 1;
    private String Table_name = "Student";
    private String Col_id = "_id";
    private String Col_name = "name";
    private String Col_grade = "grade";

    public Databasehelper(Context context) {
        super(context, DataBase_name, null, DataBase_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Table_name + " (" + Col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Col_name + " TEXT" +
                ", " + Col_grade + " NUMBER(2,2));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    public void AddStudent(String name, Double grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Col_name, name);
        cv.put(Col_grade, grade);
        long res = db.insert(Table_name, null, cv);
        if (res == -1) {
            Toast.makeText(context, "A problem Occurred while adding data", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Student Added successfully", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<Student> getallstudents() {
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_name;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Student s = new Student();
                s.setId(Integer.parseInt(cursor.getString(0)));
                s.setName(cursor.getString(1));
                s.setGrade(Double.parseDouble(cursor.getString(2)));
                students.add(s);
            }
            while (cursor.moveToNext());
        }
        return students;
    }


    public Student getstudent(int idstudent) {
        Student s = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_name + " WHERE _id= " + idstudent;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                s = new Student(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(
                        cursor.getString(2)));
            }
        }
        return s;
    }

    public void updatestudent(int id, String name, Double grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Col_name, name);
        cv.put(Col_grade, grade);

        int rowsAffected = db.update(Table_name, cv, "_id=?", new String[]{String.valueOf(id)});

        if (rowsAffected > 0) {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to Update Student", Toast.LENGTH_LONG).show();
        }
    }
    public void deletestudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long res=db.delete(Table_name,"_id=?",new String[]{
                String.valueOf(id)
        });
        if(res==-1){
            Toast.makeText(context, "Failed to Delete Student", Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show();
        }
    }
}
