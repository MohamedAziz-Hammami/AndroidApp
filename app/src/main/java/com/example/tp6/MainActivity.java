package com.example.tp6;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText studentid;
    private EditText grade;
    private Button b;
    private Button check;
    private ImageButton imb;
    private String n;
    private Double g;
    Databasehelper db=new Databasehelper(MainActivity.this);
    int idst;
    Student studentfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        studentid=(EditText)findViewById(R.id.checkifexists);
        name=(EditText)findViewById(R.id.forname);
        grade=(EditText)findViewById(R.id.fornote);
        b=(Button)findViewById(R.id.add);
        check=(Button)findViewById(R.id.check);
        imb=(ImageButton)findViewById(R.id.liste);


        b.setOnClickListener((View v)->{
            n=name.getText().toString().trim();
            if(n.isEmpty()){
                Toast.makeText(MainActivity.this,"Name is Necessary",Toast.LENGTH_LONG).show();
                return;
            }

            if(grade.getText().toString().trim().isEmpty()){
                Toast.makeText(MainActivity.this,"Grade is Necessary",Toast.LENGTH_LONG).show();
                return;
            }

            try {
                g=Double.parseDouble(grade.getText().toString());

            }
            catch (NumberFormatException e){
                Toast.makeText(MainActivity.this,"Grade needs to be Double",Toast.LENGTH_LONG).show();
                return;
            }
            db.AddStudent(n,g);


        });

        check.setOnClickListener((View v)->{
            if(studentid.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this,"Le champ doivent etre remplir svp",Toast.LENGTH_LONG).show();
                return;

            }
            try{
                idst=Integer.parseInt(studentid.getText().toString());

            }
            catch (NumberFormatException e){
                Toast.makeText(MainActivity.this,"Error in format",Toast.LENGTH_LONG).show();
                return;

            }

            studentfound=new Student();
            studentfound=db.getstudent(idst);
            if(studentfound==null){
                Toast.makeText(this, "No student with the given ID", Toast.LENGTH_LONG).show();
                return;

            }
            Toast.makeText(MainActivity.this,"Name :"+studentfound.getName()+"  Grade :"+studentfound
                    .getGrade(),Toast.LENGTH_LONG).show();


        });

        imb.setOnClickListener((View v)->{
            Intent intent=new Intent(this,second.class);
            startActivity(intent);
        });

    }
}