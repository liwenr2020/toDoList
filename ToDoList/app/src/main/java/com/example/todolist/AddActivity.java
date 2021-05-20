package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private EditText todoList;
    private Button ButtonIndex;
    private Button ButtonSave;


    private TextView TimeView;

    Bundle bundle;
    MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        todoList=(EditText)findViewById(R.id.addEditView);
        ButtonSave=(Button)findViewById(R.id.save);
        ButtonIndex=(Button)findViewById(R.id.index);

        TimeView=(TextView)findViewById(R.id.addGetTime);

        dbHelper=new MyDBHelper(AddActivity.this, "toDoList.db", null, 1);

        ButtonSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                //数据库里面没有该条记录，需要添加
            InsertDb();
        }
        });
        ButtonIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
}
    private void InsertDb() {
        //向数据库插入数据
        SQLiteDatabase wDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // 开始组装第一条数据
        String t=getTime();values.put("time", t);
       // String d = getToDo();
        if(todoList.getText().toString()!=null){

        values.put("todo", todoList.getText().toString());}
        else{
            Toast.makeText(AddActivity.this,"todo Failed", Toast.LENGTH_SHORT).show();
        }


        wDb.insert("toDoList", null, values); // 插入第一条数据
        values.clear();

        TimeView.setText("last edited time:"+t);
        Toast.makeText(AddActivity.this, "add successfully!", Toast.LENGTH_SHORT).show();

    }

    private void deleteDb() {
        SQLiteDatabase wDb = dbHelper.getWritableDatabase();
        int rs = wDb.delete("toDoList","id = ?",new String[]{bundle.getLong("id")+""});
        Toast.makeText(this,rs > 0 ? "Delete successfully!":"Failed",Toast.LENGTH_SHORT).show();

    }
    private String getTime(){
        SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy/MM/dd/   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String   getTime   =   formatter.format(curDate);
        return getTime;
    }

    private String getToDo(){
        String todo=todoList.getText().toString();
        Toast.makeText(AddActivity.this,todo, Toast.LENGTH_SHORT).show();
        return todo;
    }
    public List<String> selector() {
        SQLiteDatabase rDb = dbHelper.getReadableDatabase();
        Cursor cursor = rDb.query("toDoList", null, null, null, null, null, null);

        List<String> rows = new ArrayList<>();
        String todo="default";

        if (cursor.moveToFirst()) {
            do {
                // 遍历 Cursor 对象，取出数据并打印
                todo = cursor.getString(cursor.getColumnIndex
                        ("todo"));
                rows.add(todo);
            } while (cursor.moveToNext());
        }
        return rows;
    }

}







