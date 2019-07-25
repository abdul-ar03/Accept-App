package com.developments.ar.accept;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Blutooth_DialogBox extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public TextView stop, cancel,bt_title;
    private Bluetooth_Adapter Bt_Adapter;
    private List<String> Bt_name_list = new ArrayList<>();
    private List<String> Bt_addr_list = new ArrayList<>();
    private Blutooth_DialogBox mContext;
    private ListView listView;
    private static BluetoothAdapter bluetoothAdapter;
    private int count=0;
    private ProgressBar bt_progress;
    public Blutooth_DialogBox(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bluetooth_activity);
        mContext = this;
        stop = (TextView) findViewById(R.id.b_stop);
        cancel = (TextView) findViewById(R.id.b_cancel);
        bt_title = (TextView) findViewById(R.id.bt_title);
        bt_progress=(ProgressBar)findViewById(R.id.bt_progress);
        stop.setOnClickListener(this);
        cancel.setOnClickListener(this);
        listView = (ListView)findViewById(R.id.devicesfound);
        check_connect();
    }

    public void check_connect(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.d("Bluetooth", "Bluetooth NOT support");
            bt_title.setText("Bluetooth NOT suppor");
        }
        else {
            if (bluetoothAdapter.isEnabled()) {
                Log.d("Bluetooth", "Bluetooth is Enabled.");
                bt_title.setText("Paired Device");
                bt_progress.setVisibility(View.GONE);
                scan_device();
            }
            else {
                Log.d("Bluetooth", "Bluetooth is NOT Enabled!");
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                bluetoothAdapter.enable();
                bt_title.setText("Connecting...");
                bt_progress.setVisibility(View.VISIBLE);
                count++;

                if (count<3){
                    c.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    check_connect();
                                }
                            }, 3000);
                        }
                    });
                }
                else {
                    bt_title.setText("Bluetooth Error");
                }

            }
        }
    }

    public void scan_device(){
        Bt_name_list.clear();
        Bt_addr_list.clear();
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice d : pairedDevices) {
                String deviceName = d.getName();
                String macAddress = d.getAddress();
                if(d.getType()==2){
                    Bt_name_list.add(deviceName);
                    Bt_addr_list.add(macAddress);
                }
                Log.i("log", "paired device: " + deviceName + " at " + macAddress);


            }
        }
        Bt_Adapter = new Bluetooth_Adapter(c,Bt_name_list,Bt_addr_list);
        listView.setAdapter(Bt_Adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.d("############", "Items " + Bt_name_list.get(pos));
                Intent intent = new Intent(c, Device_Activity.class);
                intent.putExtra("b_name",Bt_name_list.get(pos));
                intent.putExtra("b_addr",Bt_addr_list.get(pos));
                intent.putExtra("from",0);
                c.startActivityForResult(intent, 8888);
                dismiss();
            }
        });
    }

    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //if(device.getType()==2){
                    Bt_name_list.add( device.getName());
                    Bt_addr_list.add(device.getAddress());
                    Log.i("log", "other device " + device.getName() + " at " + device.getAddress());
                    Bt_Adapter.notifyDataSetChanged();
                //}
            }
        }
    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode==8888 && resultCode  == RESULT_CANCELED) {
//            Blutooth_DialogBox dialogBox = new Blutooth_DialogBox(this);
//            dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialogBox.show();
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_stop:
                //c.finish();
                break;
            case R.id.b_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}