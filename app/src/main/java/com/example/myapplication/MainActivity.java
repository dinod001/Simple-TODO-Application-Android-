package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    TextView count_view;
    Button main_button;
    Intent intent;
    Context context;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        dBhelper=new DBhelper(context);

        listView=findViewById(R.id.listview);
        count_view=findViewById(R.id.count);
        main_button=findViewById(R.id.main_btn);

        //set count
        int count=dBhelper.Count();
        count_view.setText("You have "+count+" Tdos Today");

        //Adapter set
        List<Todo> todos=dBhelper.read();
        TodoAdapter adapter=new TodoAdapter(context,R.layout.single_todo,todos);
        listView.setAdapter(adapter);

        //update,delete and finishes buttons

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo=todos.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(todo.getTitle());
                builder.setMessage(todo.getDescription());

                //finsh button
                builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todo.setFinished(System.currentTimeMillis());
                        dBhelper.update(todo);
                        startActivity(new Intent(context,MainActivity.class));
                    }
                });

                //upadte button
                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent=new Intent(context,Edit_TODO.class);
                        intent.putExtra("id",String.valueOf(todo.getId()));
                        startActivity(intent);
                    }
                });

                //delete button
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id=todo.getId();
                        boolean result=dBhelper.delete(id);
                        if(result){
                            Toast.makeText(context,"Deleted successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context,MainActivity.class));
                        }
                        else{
                            Toast.makeText(context,"Deleted failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
    }



    public void clickme(View view) {
        startActivity(new Intent(context,Add_TODO.class));
    }
}