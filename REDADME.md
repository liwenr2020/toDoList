这是一个简单的备忘录，我的手机总是说解析包时发生错误，但是在模拟器上面时可以正常运行的。

（一）实现的功能：

  1、添加备忘录条目，进行编辑，保存在sqlite里面
  
  2、点击item,对备忘录进行重新编辑或者删除，然后返回主页
  
  3、主页上显示已经添加到数据库里面的item
 
（二）具体的方面

  1、数据库的操作方面：
  
    （1）使用的是最基础的数据库操作方法，如果要进行优化，可以使用room，简化操作的过程；
    
    （2）EditActivity.java和AddActivity.java里面用到的操作数据的方式有一点不同，前者是
    
    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.todolist/databases/toDoList.db",null);
    
    后者是
    
    SQLiteDatabase wDb = dbHelper.getWritableDatabase();
  
  2、显示备忘录列表
  
    (1)list
    
        mList =(ListView) findViewById(R.id.List);
        
        mList.setAdapter( new ArrayAdapter<>(this,R.layout.item_list,loadData()));
        
    (2)list里面的监听器 
    
        mList.setOnItemClickListener(this);
       
  3、过程中遇到的问题:
  
    (1)点击item，需要跳转到具体的编辑页面，编辑页面会显示这个item的具体信息，这里的具体信息就需要从数据库里面查询获取。
    
    所以就需要获得这个item的id，一开始我是有一点疑惑如何获取。
    
    后面看到了一个帖子，item会自带一个position和id，这个position和id的值通常是一样的。而且显示在页面上的数据，数据库里面的行的顺序也一定是和它一样的，所以将cursor移动到那一行。
    
            cursor.moveToPosition(position);// cursor1移动到点击的当前行
            
            select_ImgUrl = cursor.getInt(0); // 获得这一行的在数据库中的id
            
    (2)数据在activity之间的传递，使用Intent
    
        Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
        intent1.putExtra("id", select_ImgUrl);
        startActivity(intent1);
      
      然后在跳转的activity取出数据
        
        mId=getIntent().getIntExtra("id",0);
    
            

        
        
        
        
        
        
        
        
        
        
        
        
        

  
 
