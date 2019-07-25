package com.developments.ar.accept;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Admin on 3/27/2018.
 */
public class Device_Activity extends Activity {
    private String b_name;
    private String b_addr;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private BluetoothDevice bluetoothDevice;
    private ImageView tick1;
    private TextView bluetoothState;
    private TextView bluetoothContent;
    private TextView txt_cancel;
    private LinearLayout next_div;
    private Boolean band_con=false;
    private Boolean bluetooth_con=false;
    private int iter=0;
    private int from=0;
    private Sql_db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.band_connect);
        db = new Sql_db(this);
        Intent intent = getIntent();
        b_name = intent.getStringExtra("b_name");
        b_addr = intent.getStringExtra("b_addr");
        from = intent.getIntExtra("from", 0);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        tick1=(ImageView)findViewById(R.id.tick1);
        bluetoothState=(TextView)findViewById(R.id.bluetoothState);
        bluetoothContent=(TextView)findViewById(R.id.bluetoothContent);
        txt_cancel=(TextView)findViewById(R.id.txt_cancel);
        next_div=(LinearLayout)findViewById(R.id.next1_div);
        start_connection();
    }

    private void start_connection() {
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(b_addr);

        Log.v("test", "Connecting to " + b_addr);
        Log.v("test", "Device name " + b_name);

        try{
            bluetoothGatt = bluetoothDevice.connectGatt(this, true, bluetoothGattCallback);
        }
        catch (Exception e){
            device_setting(0);
        }

    }


    void startScanHeartRate() {
        //txtByte.setText("...");
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.HeartRate.service)
                .getCharacteristic(CustomBluetoothProfile.HeartRate.controlCharacteristic);
        bchar.setValue(new byte[]{21, 2, 1});
        bluetoothGatt.writeCharacteristic(bchar);
    }

    void listenHeartRate() {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.HeartRate.service)
                .getCharacteristic(CustomBluetoothProfile.HeartRate.measurementCharacteristic);
        bluetoothGatt.setCharacteristicNotification(bchar, true);
        BluetoothGattDescriptor descriptor = bchar.getDescriptor(CustomBluetoothProfile.HeartRate.descriptor);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);
    }

    void getBatteryStatus() {
        //txtByte.setText("...");
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.Basic.service)
                .getCharacteristic(CustomBluetoothProfile.Basic.batteryCharacteristic);
        Log.d("event", "getBatteryStatus");
        if (!bluetoothGatt.readCharacteristic(bchar)) {
            Log.e("Failed","Failed issue battery");
            Toast.makeText(this, "Failed get battery info", Toast.LENGTH_SHORT).show();
        }

    }


    void startVibrate() {

        try{
            BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.AlertNotification.service)
                    .getCharacteristic(CustomBluetoothProfile.AlertNotification.alertCharacteristic);
            band_con=true;
//            if (bchar!=null){
//
//                bchar.setValue(new byte[]{2});
//                Log.d("event", "startVibrate");
//                if (!bluetoothGatt.writeCharacteristic(bchar)) {
//                    Log.e("Failed","Failed issue vibrate 1");
//                    Toast.makeText(this, "Failed start vibrate", Toast.LENGTH_SHORT).show();
//                }
//            }
//            else {
//                band_con=false;
//            }
        }catch (Exception e){
            band_con=false;
            Log.d("error :  ",e+"  ... ");
        }

    }

    void stopVibrate() {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.AlertNotification.service)
                .getCharacteristic(CustomBluetoothProfile.AlertNotification.alertCharacteristic);
        bchar.setValue(new byte[]{0});
        if (!bluetoothGatt.writeCharacteristic(bchar)) {
            Toast.makeText(this, "Failed stop vibrate", Toast.LENGTH_SHORT).show();
        }
    }

    final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.v("test", "onConnectionStateChange  " + gatt + "  " + status + "  " + newState);

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d("State", "Conected");
                bluetoothGatt.discoverServices();
                complete_connection();
                //startVibrate();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                bluetoothGatt.disconnect();
                Log.d("State", "Disconnected");
            }
        }
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.v("test", "onServicesDiscovered");
            listenHeartRate();
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.v("test", "onCharacteristicRead");
            final byte[] data = characteristic.getValue();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //txtByte.setText(Arrays.toString(data));
                }
            });

        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.v("test", "onCharacteristicWrite");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.v("test", "onCharacteristicChanged");
            final byte[] data = characteristic.getValue();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //txtByte.setText(Arrays.toString(data));
                }
            });
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.v("test", "onDescriptorRead");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.v("test", "onDescriptorWrite");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            Log.v("test", "onReliableWriteCompleted");
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            Log.v("test", "onReadRemoteRssi");
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.v("test", "onMtuChanged");
        }
    };

    private void complete_connection() {
        test1();
    }

    private void test1(){
        Log.d("iter  :  ", iter + "");
        if (iter>=3){
            device_setting(0);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iter++;
                        try {
                            BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.AlertNotification.service)
                                    .getCharacteristic(CustomBluetoothProfile.AlertNotification.alertCharacteristic);
                            band_con = true;
                        } catch (Exception e) {
                            band_con = false;
                            Log.d("error :  ", e + "  ... ");
                        }
                        if (band_con) {
                            test2();
                            iter = 0;
                        } else if (iter <= 3) {
                            test1();
                            Log.d("iter 2 :  ", iter + "");
                        }
                    }
                });
            }
        }, 3000);

    }


    private void test2() {
        Log.d("Connected  :  ", iter + "  Connected");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //stopVibrate();
            }
        }, 300);
        device_setting(1);
        if (from==0){
            db.update_new_device(b_name,b_addr);
            String[] content={"",""};
            content=db.get_device();
            Log.d("values db",content[0]);
            Log.d("values db",content[1]);
        }
    }

    public void cancel_fun(View view){
        if(!band_con){
            finish();
        }
        else{
            Intent intent = new Intent(Device_Activity.this, Mi_fit.class);
            startActivityForResult(intent, 5555);
        }
    }

    public void change_device_fun(View view){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


    public void device_setting(int n){
        if(n==1){
            tick1.setImageResource(R.drawable.tick1);
            tick1.setVisibility(View.VISIBLE);
            bluetoothState.setText("Connected");
            txt_cancel.setVisibility(View.GONE);
            next_div.setVisibility(View.VISIBLE);
            bluetoothContent.setText(b_name + "  -  " + b_addr);
        }
        else{
            tick1.setImageResource(R.drawable.error);
            tick1.setVisibility(View.VISIBLE);
            bluetoothState.setText("Connection error");
            bluetoothContent.setText("Kindly check the device connection");
        }
    }
}
