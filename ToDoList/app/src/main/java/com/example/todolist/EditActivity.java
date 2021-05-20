package com.example.todolist;
/*EditActivity.java和AddActivity.java里面用到的操作数据的方式有所不同
* 前者是SQLiteDatabase db，后者是dbHelper*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditActivity extends AppCompatActivity {

//数据库里面有相关的数据，只是进行更新
    private EditText todoList;

    private Button ButtonSave;
    private Button ButtonDel;

    private TextView TimeView;

    Bundle bundle;
    //MyDBHelper dbHelper;
    //SQLiteDatabase db;
    static String id;
    int mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);mId=getIntent().getIntExtra("id",0);
        id=String.valueOf(mId);
       // id=getIntent().getExtras().getString("id");//感觉这里的id根本没有传进来
        todoList=(EditText)findViewById(R.id.editView);
        ButtonSave=(Button)findViewById((R.id.saveEdit));
        ButtonDel=(Button)findViewById(R.id.delEdit);
        TimeView=(TextView)findViewById(R.id.getTime);

        todoList.setText(selectDbToDo(id));
        //todoList.setText(selectDbToDo(id));
        TimeView.setText("last edited time:"+selectDbTime(id));

            ButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateDb(id);
                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    }
            });

            ButtonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDb(id);
                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }


    private void updateDb(String id) {
        //更新数据库的内容
//        SQLiteDatabase wDb = dbHelper.getWritableDatabase();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.todolist/databases/toDoList.db",null);

        ContentValues values = new ContentValues();

        // 开始组装第一条数据
        String t=getTime();

        values.put("todo", getToDo());
        values.put("time",t);

        int rs = db.update("toDoList",values,"id = ?",new String[]{id});
        Toast.makeText(this,rs > 0 ? "Update successfully!":"Failed",Toast.LENGTH_SHORT).show();
        TimeView.setText("last edited time:"+t);

    }

    private void deleteDb(String id) {
        SQLiteDatabase wDb = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.todolist/databases/toDoList.db",null);

        int rs = wDb.delete("toDoList","id = ?",new String[]{id});
        Toast.makeText(this,rs > 0 ? "Delete successfully!":"Failed",Toast.LENGTH_SHORT).show();
    }
    private String selectDbToDo(String id) {
        SQLiteDatabase rDb =SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.todolist/databases/toDoList.db",null);

        Cursor cursor = rDb.rawQuery("select * from toDoList where id="+id,null);

        String myToDo = null;
        if (cursor.moveToFirst()) {
            myToDo = cursor.getString(1);//返回的cursor结果集的游标默认在-1，需要移到0(开始读取位置)
        }
        return myToDo;
    }
    private String selectDbTime(String id) {
        SQLiteDatabase rDb = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.todolist/databases/toDoList.db",null);

        Cursor cursor = rDb.rawQuery("select * from toDoList where id="+id,null);

        String myTime = null;
        if (cursor.moveToFirst()) {
            myTime = cursor.getString(2);
        }
        return myTime;
    }

    private String getTime(){
        SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy/MM/dd/   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String   getTime   =   formatter.format(curDate);
        return getTime;
    }

    private String getToDo(){
        String todo=todoList.getText().toString();
        return todo;
    }


    public List<String> getEachId () {
        SQLiteDatabase rDb = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.todolist/databases/toDoList.db",null);

        Cursor cursor = rDb.query("toDoList", null, null, null, null, null, null);

        List<String> rows = new ArrayList<>();
        String id="default";

        if (cursor.moveToFirst()) {
            do {
                // 遍历 Cursor 对象，取出数据并打印
                id = cursor.getString(cursor.getColumnIndex
                        ("id"));
                rows.add(id);
            } while (cursor.moveToNext());
        }
        return rows;
    }
}