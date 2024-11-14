package com.example.tp6;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class second extends AppCompatActivity {
    Integer id = null;
    Button modifier;
    Button delete;
    ListView studentslist;
    List<Student> students = new ArrayList<Student>();
    ArrayList<String> lvitems = new ArrayList<String>();
    Databasehelper db = new Databasehelper(second.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        studentslist = (ListView) findViewById(R.id.listestudent);
        modifier = (Button) findViewById(R.id.modify);
        delete = (Button) findViewById(R.id.delete);
        students = db.getallstudents();
        for (Student s : students) {
            lvitems.add(s.getId() + " " + s.getName() + "  " + s.getGrade());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, lvitems);

        studentslist.setAdapter(adapter);
        studentslist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        studentslist.setOnItemClickListener((AdapterView<?> parent, View v, int position, long itemId) -> {
            id = students.get(position).getId();
            Toast.makeText(this, "Selected ID: " + id, Toast.LENGTH_LONG).show();
        });

        modifier.setOnClickListener((View v) -> {
            if (id == null) {
                Toast.makeText(this, "You need to select one from the list", Toast.LENGTH_LONG).show();
                return;
            } else {
                Intent intent = new Intent(this, Modify.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        delete.setOnClickListener((View v)->{
            if (id == null) {
                Toast.makeText(this, "You need to select one from the list", Toast.LENGTH_LONG).show();
                return;}
            else {

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you wanna delete it")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deletestudent(id);
                            }
                        })
                        .setNegativeButton("Cancel",null);
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
    }
}
