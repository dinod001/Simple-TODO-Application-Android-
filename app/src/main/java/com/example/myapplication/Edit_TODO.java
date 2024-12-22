package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_TODO extends AppCompatActivity {
    TextView edit_title;
    TextView edit_desc;
    Button edit_btn;
    Long updatedate;
    DBhelper dBhelper;
    Context context;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        context=this;
        dBhelper=new DBhelper(context);
        id = getIntent().getStringExtra("id");

        edit_title=findViewById(R.id.edit_title);
        edit_desc=findViewById(R.id.edit_desc);
        edit_btn=findViewById(R.id.edit_button);

        //set title and desc
        Todo todo=dBhelper.getsingle(Integer.parseInt(id));
        edit_title.setText(todo.getTitle());
        edit_desc.setText(todo.getDescription());
    }

    public void update(View view) {
        String user_title=edit_title.getText().toString();
        String user_description=edit_desc.getText().toString();
        updatedate=System.currentTimeMillis();
        Todo todo=new Todo(Integer.parseInt(id),user_title,user_description,updatedate,0);
        int status=dBhelper.update(todo);
        if (status>0){
            Toast.makeText(context,"Updated Successfully",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(context,MainActivity.class));
        }
        else{
            Toast.makeText(context,"Updated Failed",Toast.LENGTH_SHORT).show();
        }
    }
}