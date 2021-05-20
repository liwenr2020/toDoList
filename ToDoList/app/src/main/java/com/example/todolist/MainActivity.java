package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button mAdd;
    private ListView mList;
    MyDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new MyDBHelper(MainActivity.this, "toDoList.db", null, 1);
        dbHelper.getWritableDatabase();

        mAdd = (Button) findViewById(R.id.addItem);
        mList =(ListView) findViewById(R.id.List);
        mList.setAdapter( new ArrayAdapter<>(this,R.layout.item_list,loadData()));


        Log.i("TAG","loadData successfully");

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "add", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                //finish();//之前写了finish()运行的时候总会报错退出，我认为是直接结束了这个activity
            }
        }) ;

        mList.setOnItemClickListener(this);
    }

    private List<String> loadData() {
        /*String[] data = { "Apple", "Banana", "Orange", "Watermelon",
                "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
                "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
                "Pineapple", "Strawberry", "Cherry", "Mango" };
return data;*/

        SQLiteDatabase rDb = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.todolist/databases/toDoList.db",null);
        List<String> rows=new ArrayList<>();
        //SQLiteDatabase rDb = dbHelper.getReadableDatabase();
        Cursor cursor = rDb.query("toDoList", null, null, null, null, null, null);


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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int select_ImgUrl =0;
        SQLiteDatabase rDb = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.todolist/databases/toDoList.db",null);
        Cursor cursor = rDb.query("toDoList", null, null, null, null, null, null);
        try {
            cursor.moveToPosition(position);// cursor1移动到点击的当前行
            select_ImgUrl = cursor.getInt(0); // 获得这一行的在数据库中的id

        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        }


        Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
        intent1.putExtra("id", select_ImgUrl);
        startActivity(intent1);
    }
}




