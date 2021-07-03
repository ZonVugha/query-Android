package com.example.nbnhhsh;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MySQLite mySQLite;
    private SQLiteDatabase database;
    private TextView tvTitle;
    private TextView tvContent;
    private Button btnFind;
    private EditText editletter;
    private String TAG = "TAG";
    private DrawerLayout mDrawerLayout;
    private Button btnLocal;
    private Button btnOnline;
    private AudioManager manager;
    private MediaPlayer turnUFKMusicDown;
    Handler handler = new Handler();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_add) {
//            Intent intent = new Intent(MainActivity.this, Addtxt.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        turnUFKMusicDown = MediaPlayer.create(this,R.raw.turnufkmusicdown);
        turnUFKMusicDown.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        循环
//        turnUFKMusicDown.setLooping(true);
//        使用线程
        handler.postDelayed(runnable,1000);

        //        菜单项按钮显示
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.more);
        }
        mySQLite = new MySQLite(this, MySQLite.DATABAENAME, null, 1);
        mySQLite.getWritableDatabase();
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                查询数据
                if (editletter.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入需要查询的字母！", Toast.LENGTH_LONG).show();
                } else {
                    database = mySQLite.getWritableDatabase();
                    Cursor cursor = database.query(MySQLite.TABLENAME, null, MySQLite.VALUE_ABBREVIATION + "=?", new String[]{editletter.getText().toString()},
                            null, null, null);
                    tvContent.setText(null);
                    if (cursor.isAfterLast()) {
                        Toast.makeText(getApplicationContext(), "查询不到词义", Toast.LENGTH_LONG).show();
                        tvContent.setText("如果查询不到请点击右上角的添加按钮添加词义");
                    } else {
                        while (cursor.moveToNext()) {
                            String txet = cursor.getString(cursor.getColumnIndex(MySQLite.VALUE_TXT));
                            tvContent.append(txet + "  ");
                        }
                        cursor.close();
                        database.close();
                    }
                }
            }
        });
//        打开在线查询
        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,searchOnline.class);
                startActivity(intent);
            }
        });

    }

    //          判断点击菜单按钮
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }

//        判断是否打开菜单
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawers();
            }
        }
//        打开添加页面
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, Addtxt.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);


    }

    //  返回页面时插入数据
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String abbreviation = intent.getStringExtra("abbreviation");
        String text = intent.getStringExtra("txt");
        if (abbreviation != null && text != null) {
            ContentValues values = new ContentValues();
            values.put(MySQLite.VALUE_ABBREVIATION, abbreviation);
            values.put(MySQLite.VALUE_TXT, text);
            database = mySQLite.getWritableDatabase();
            long result = database.insert("datatable", null, values);
            database.close();
            Log.i(TAG, "data number: " + result);
        }
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);
        btnFind = (Button) findViewById(R.id.btnFind);
        editletter = (EditText) findViewById(R.id.editletter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mdr);
        btnLocal = (Button) findViewById(R.id.btnLocal);
        btnOnline = (Button) findViewById(R.id.btnOnline);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                handler.postDelayed(this, 5000);
                if (manager.isMusicActive()){
                    Log.d(TAG, "设备音量: "+manager.getStreamVolume(AudioManager.STREAM_MUSIC));
                    if (manager.getStreamVolume(AudioManager.STREAM_MUSIC)>=10){
                        manager.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                        Toast.makeText(getApplicationContext(), "TURN YOU FUCKING MUSIC DOWN!!!", Toast.LENGTH_LONG).show();
                        turnUFKMusicDown.start();
                    }
                }else {
                    Log.d(TAG, "no music and 设备音量: "+manager.getStreamVolume(AudioManager.STREAM_MUSIC));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(runnable,1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        turnUFKMusicDown.pause();
    }
}
