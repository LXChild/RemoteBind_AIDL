package com.example.lxchild.remotebind_aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lxchild.aidl.IMyAidlInterface;


public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.lxchild.remotebindservice_aidl", "com.example.lxchild.remotebindservice_aidl.MyService"));
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binder = null;
        }
    };

    public void onClick(View view) throws RemoteException {

        if (binder != null) {
            int res = binder.sum(1, 2);
            Log.i("Hensen", "从服务端调用成功的结果：" + res);

        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
