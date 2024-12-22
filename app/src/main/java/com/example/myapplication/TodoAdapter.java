package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TodoAdapter extends ArrayAdapter {
    private Context context;
    private int resorce;
    private List<Todo> todos;

    TodoAdapter(Context context, int resorce, List<Todo> todos){
        super(context,resorce,todos);
        this.context=context;
        this.resorce=resorce;
        this.todos=todos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(resorce,parent,false);

        TextView title=row.findViewById(R.id.single_title);
        TextView dec=row.findViewById(R.id.single_desc);
        ImageView image=row.findViewById(R.id.imageView);

        Todo todo=todos.get(position);
        title.setText(todo.getTitle());
        dec.setText(todo.getDescription());
        image.setVisibility(row.INVISIBLE);

        if(todo.getFinished()>0){
            image.setVisibility(View.VISIBLE);
        }
        return row;
    }
}
