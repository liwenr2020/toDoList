package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String toDoList = "create table toDoList(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "todo text,"+
            "time text)";
    private Context mContext;

    //static int id=0;

    public MyDBHelper(@Nullable AddActivity context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }
    public MyDBHelper(@Nullable MainActivity context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(toDoList);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

   //    private void add(int id){
//                SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy年/MM/dd/   HH:mm:ss");
//                Date curDate =  new Date(System.currentTimeMillis());
//                /* 获取当前时间 */
//                String   getTime   =   formatter.format(curDate);
//                String todo=todoList.getText().toString();
//                String addItem="INSERT INTO toDoList (id,todo,time) VALUES ("+id+", "+todo+", "+getTime+")";
//                IdView.setText(id);
//                TimeView.setText(getTime);
//
//    }
//    private void del(){
//        ButtonDel.findViewById(R.id.del);
//        todoList.findViewById(R.id.edit);
//        ButtonDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String delId;
//                delId=IdView.getText().toString();
//                String delItem="DELETE FROM todoList WHERE id="+id;
//
//                Toast.makeText(mContext, "清除该条备忘录", Toast.LENGTH_SHORT).show();
//                try {
//                    Thread.sleep(3);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }
}
