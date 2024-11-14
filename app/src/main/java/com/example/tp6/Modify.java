package com.example.tp6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Modify extends AppCompatActivity {
    private Context context;
    EditText forname;
    EditText fornote;
    Databasehelper db = new Databasehelper(this);
    Button modifier;
    double g;
    Student st = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        modifier=(Button)findViewById(R.id.modifier);
        forname = (EditText) findViewById(R.id.forname);
        fornote = (EditText) findViewById(R.id.fornote);
        Intent intent = getIntent();
        int idstudent = intent.getIntExtra("id", 0);
        st = db.getstudent(idstudent);
        forname.setText(st.getName());
        fornote.setText(String.valueOf(st.getGrade()));

        modifier.setOnClickListener((View v)->{
            if(forname.getText().toString().isEmpty()){
                Toast.makeText(this, "Champ name is empty", Toast.LENGTH_LONG).show();
                return;
            }

            if(fornote.getText().toString().isEmpty()){
                Toast.makeText(this, "Champ Grade is empty", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                g=Double.parseDouble(fornote.getText().toString());
            }
            catch (NumberFormatException e){
                Toast.makeText(this, "Champ Grade is invalid", Toast.LENGTH_LONG).show();
                return;
            }
             db.updatestudent(st.getId(), forname.getText().toString(), g);
        });


    }
}