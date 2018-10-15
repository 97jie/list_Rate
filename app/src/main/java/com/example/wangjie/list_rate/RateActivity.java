package com.example.wangjie.list_rate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;

public class RateActivity extends Activity {

    private EditText et_1;
    private TextView tv_nation;
    private TextView tv_money;
    String rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        et_1=findViewById(R.id.et_1);
        tv_nation=findViewById( R.id.tv_nation);
        tv_money=findViewById(R.id.tv_money);
        Intent intent=getIntent();
        String nation=intent.getStringExtra("nation");
        rate=intent.getStringExtra("rate");
        tv_nation.setText(nation);
        et_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String rmb=et_1.getText().toString();
                if(rmb.length()>0){
                    try{
                        double m=Double.parseDouble(rmb);
                        double r=Double.parseDouble(rate);
                        tv_money.setText(m*r+tv_nation.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(RateActivity.this,"请输入合法数值",Toast.LENGTH_SHORT);
                    }
                }else {
                    tv_money.setText("");
                }
            }
        });
    }

//    public void click(View v){
//        String rmb=et_1.getText().toString();
//        try{
//            double m=Double.parseDouble(rmb);
//            double r=Double.parseDouble(rate);
//            tv_money.setText(m*r+tv_nation.getText().toString());
//        }catch (Exception e){
//            Toast.makeText(this,"请输入合法数值",Toast.LENGTH_SHORT);
//        }
//    }
}
