package com.example.nbnhhsh;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Addtxt extends AppCompatActivity {

    private EditText editletter;
    private EditText editTxt;
    private Button btnAdd;
    private Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtxt);
        initView();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Addtxt.this)
                    .setTitle("确定添加词组")
                        .setMessage("字母简写: "+editletter.getText().toString()+'\n'+"词义: "+editTxt.getText().toString())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Addtxt.this,MainActivity.class);
                            String abbreviation=editletter.getText().toString();
                            String txt=editTxt.getText().toString();
                            if (abbreviation.equals("")&&txt.equals("")){
                                startActivity(intent);
                            }else {
                                intent.putExtra("abbreviation",abbreviation);
                                intent.putExtra("txt",txt);
                                startActivity(intent);
                            }
                        }
                    })
                    .setNegativeButton("取消",null)
                    .create()
                    .show();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addtxt.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        editletter = (EditText) findViewById(R.id.editletter);
        editTxt = (EditText) findViewById(R.id.editTxt);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnNo = (Button) findViewById(R.id.btnNo);
    }
}
