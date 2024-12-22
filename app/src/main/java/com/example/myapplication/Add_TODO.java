package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Add_TODO extends AppCompatActivity {

    TextView add_title;
    TextView add_description;
    Button add_button;
    DBhelper dBhelper;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        context=this;
        dBhelper=new DBhelper(context);

        add_title=findViewById(R.id.add_title);
        add_description=findViewById(R.id.add_desc);
        add_button=findViewById(R.id.add_button);

    }

    public void Addbtn(View view) {
        String user_title=add_title.getText().toString();
        String user_desc=add_description.getText().toString();
        long started=System.currentTimeMillis();
        Todo todo=new Todo(user_title,user_desc,started,0);
        Boolean result=dBhelper.save(todo);
        if(result==true){
            Toast.makeText(context,"Added Successfully",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Added Failed",Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(context,MainActivity.class));
    }
}