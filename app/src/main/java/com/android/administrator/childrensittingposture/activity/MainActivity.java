package com.android.administrator.childrensittingposture.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.bean.CultivateDb;
import com.android.administrator.childrensittingposture.dao.SendRequest;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    private TextView tv_time;
    private TextView tv_frequency;
    private TextView tv_state;
    private TextView tv_remind;
    private TextView tv_automaticallyRemind;
    private TextView tv_CumulativeQuantity;
    private TextView tv_history;
    private TextView tv_refresh;

    private Handler mHandler,recHandler;

    public static final int CUMULATION_TIME=1;
    public static final int REMIND_NUMBER=2;
    public static final int SUM_REMIND_NUMBER=3;
    public static final int BAR_DATA=4;
    public static final int SUCCESS=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = Connector.getDatabase();

        initView();
        initListener();




}
    private void initView(){
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_frequency=(TextView)findViewById(R.id.tv_frequency);
        tv_state=(TextView)findViewById(R.id.tv_state);
        tv_remind=(TextView)findViewById(R.id.tv_remind);
        tv_automaticallyRemind=(TextView)findViewById(R.id.tv_automaticallyRemind);
        tv_CumulativeQuantity=(TextView)findViewById(R.id.tv_CumulativeQuantity);
        tv_history=(TextView)findViewById(R.id.tv_history);
        tv_refresh=(TextView)findViewById(R.id.tv_refresh);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case CUMULATION_TIME :
                        if (msg.obj!=null){
//                            tv_time.setText("1:20:12");
                        }
                        break;
                    case REMIND_NUMBER :
//                            tv_remind.setText("8");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                        tv_frequency.setText("8");
                                CultivateDb firstDb = DataSupport.findFirst(CultivateDb.class);
                                tv_frequency.setText(firstDb.getRemindFrequency()+"");

                            }
                        });


                        break;
                    case SUM_REMIND_NUMBER :
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                            tv_CumulativeQuantity.setText("20");
                                CultivateDb sumRemindNumber=DataSupport.findFirst(CultivateDb.class);
                                tv_CumulativeQuantity.setText(sumRemindNumber.getSumRemindFrequency()+"");
                            }
                        });

                        break;
                }
            }
        };
    }


    private void initListener(){
        tv_time.setOnClickListener(this);
        tv_frequency.setOnClickListener(this);
        tv_state.setOnClickListener(this);
        tv_remind.setOnClickListener(this);
        tv_automaticallyRemind.setOnClickListener(this);
        tv_CumulativeQuantity.setOnClickListener(this);
        tv_history.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_time  :
                break;
            case R.id.tv_automaticallyRemind  :
                Intent intent_automaticallyRemind=new Intent();
                intent_automaticallyRemind.setClass(MainActivity.this,SettingActivity.class);
                startActivity(intent_automaticallyRemind);
                break;
            case R.id.tv_history  :
                Intent intent_history=new Intent();
                intent_history.setClass(MainActivity.this,HistoryActivity.class);
                startActivity(intent_history);
                break;
            case R.id.tv_refresh  :
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
//                        Log.e("readytosend",time+"");
                        new SendRequest().SendUid(1,mHandler,MainActivity.this);
                    }
                }, 2500);
                break;
            default:
                break;
        }

    }
}
