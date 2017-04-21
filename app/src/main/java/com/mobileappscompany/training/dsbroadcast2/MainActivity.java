package com.mobileappscompany.training.dsbroadcast2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver mR, mR2;
    Button sBroadcast2, dBroadcast2;
    EditText eStatic2, eDynamic2;

    String txtStatic;

    public static final String STATIC2 = "com.mobileappscompany.staticbroadcast2";
    public static final String DYNAMIC2 = "com.mobileappscompany.dynamicbroadcast2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sBroadcast2 = (Button)findViewById(R.id.buttonStatic2);
        dBroadcast2 = (Button)findViewById(R.id.buttonDynamic2);
        eStatic2 = (EditText)findViewById(R.id.editStatic2);
        eDynamic2 = (EditText)findViewById(R.id.editDynamic2);



        dBroadcast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendDynamic = new Intent(DYNAMIC2);
                sendDynamic.putExtra("messageDynamic2", eDynamic2.getText().toString());
                sendDynamic.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(sendDynamic);
            }
        });
        sBroadcast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStatic = new Intent(STATIC2);
                sendStatic.putExtra("messageStatic2", eStatic2.getText().toString());
                sendStatic.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(sendStatic);
            }
        });

        Bundle obtained = getIntent().getExtras();
        if(obtained != null){
            eStatic2.setText(obtained.getString("messageStatic"));
        }

        mR2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, intent.getStringExtra("messageDynamic2"), Toast.LENGTH_SHORT).show();
                eStatic2.setText(intent.getStringExtra("messageStatic"));
                eStatic2.setText(intent.getStringExtra("messageStatic2"));
                eDynamic2.setText(intent.getStringExtra("messageDynamic"));
                eDynamic2.setText(intent.getStringExtra("messageDynamic2"));
            }
        };
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //registerReceiver(mR2, new IntentFilter(DYNAMIC2));
        eStatic2.setText(txtStatic);
        unregisterReceiver(mR2);


    }

    @Override
    protected void onResume() {
        super.onResume();
        eStatic2.setText(txtStatic);
        unregisterReceiver(mR2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        eStatic2.setText(txtStatic);
        //unregisterReceiver(mR2);

    }

    @Override
    protected void onPause() {
        super.onPause();
        registerReceiver(mR2, new IntentFilter(STATIC2));
    }

    @Override
    protected void onStop() {
        //unregisterReceiver(mR2);
        mR2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                txtStatic = intent.getStringExtra("messageStatic");
                Toast.makeText(context, intent.getStringExtra("messageStatic"), Toast.LENGTH_SHORT).show();
                eStatic2.setText(intent.getStringExtra("messageStatic"));
                eStatic2.setText(intent.getStringExtra("messageStatic2"));
                eDynamic2.setText(intent.getStringExtra("messageDynamic"));
                eDynamic2.setText(intent.getStringExtra("messageDynamic2"));
            }
        };
        registerReceiver(mR2, new IntentFilter(DYNAMIC2));
        super.onStop();

    }
}
