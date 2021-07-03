package com.example.nbnhhsh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class searchOnline extends AppCompatActivity {

    private DrawerLayout mdrOnline;
    private TextView tvTitle;
    private EditText editletter;
    private TextView tvContent;
    private Button btnFind;
    private Button btnLocal;
    private Button btnOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_online);
        //        菜单项按钮显示
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.more);
        }
        initView();

        //        本地查询
        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(searchOnline.this,MainActivity.class);
                startActivity(intent);
            }
        });
        OkHttpClient okHttpClient = new OkHttpClient();
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        changeJSON json = new changeJSON();
                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        String enterText = editletter.getText().toString();
                        json.setText(enterText);
                        Gson gson = new Gson();
//                      使用Gson转换为json
                        String getJSON = gson.toJson(json);
                        Log.d("TAG", "转化为json: "+getJSON);
//                        测试json发送
//                        RequestBody body = RequestBody.create(JSON, "{\"text\":\"hh\"}");
                        RequestBody body = RequestBody.create(JSON, getJSON);

                        String url = "https://lab.magiconch.com/api/nbnhhsh/guess";

                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .build();

                        final Call call = okHttpClient.newCall(request);
                        try {
                            final String result = call.execute().body().string();

                            Log.d("TAG", "get data: " + result);
                            JSONArray jsonArray = new JSONArray(result);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                                获得对象名为trans的数据
                                JSONArray arr = jsonObject.getJSONArray("trans");
                                Log.e("TAG", arr.join(" "));
                                tvContent.setText(arr.join(" "));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });
    }
    //          判断点击菜单按钮
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mdrOnline.openDrawer(GravityCompat.START);
                break;
            default:
        }

//        判断是否打开菜单
        if (item.getItemId() == android.R.id.home) {
            if (mdrOnline.isDrawerOpen(GravityCompat.START)) {
                mdrOnline.closeDrawers();
            }
        }
        return true;
    }

    private void initView() {
        mdrOnline = (DrawerLayout) findViewById(R.id.mdrOnline);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        editletter = (EditText) findViewById(R.id.editletter);
        tvContent = (TextView) findViewById(R.id.tvContent);
        btnFind = (Button) findViewById(R.id.btnFind);
        btnLocal = (Button) findViewById(R.id.btnLocal);
        btnOnline = (Button) findViewById(R.id.btnOnline);
    }
}