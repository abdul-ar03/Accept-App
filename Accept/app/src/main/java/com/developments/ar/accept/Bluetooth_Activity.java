package com.developments.ar.accept;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class Bluetooth_Activity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;

    ListView listDevicesFound;
    //Button btnScanDevice;
    TextView stateBluetooth;
    BluetoothAdapter bluetoothAdapter;

    ArrayAdapter<String> btArrayAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.band_connect);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.bluetooth_activity);
        stateBluetooth = (TextView)dialog.findViewById(R.id.bluetoothState);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        listDevicesFound = (ListView)dialog.findViewById(R.id.devicesfound);
        btArrayAdapter = new ArrayAdapter<String>(Bluetooth_Activity.this, android.R.layout.simple_list_item_1);
        listDevicesFound.setAdapter(btArrayAdapter);

        CheckBlueToothState();

        //btnScanDevice.setOnClickListener(btnScanDeviceOnClickListener);

        registerReceiver(ActionFoundReceiver,
                new IntentFilter(BluetoothDevice.ACTION_FOUND));

        dialog.show();
        scan();

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(ActionFoundReceiver);
    }

    private void CheckBlueToothState() {
        if (bluetoothAdapter == null) {
            stateBluetooth.setText("Bluetooth NOT support");
        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    stateBluetooth.setText("Bluetooth is currently in device discovery process.");
                } else {
                    stateBluetooth.setText("Bluetooth is Enabled.");
                }
            } else {
                stateBluetooth.setText("Bluetooth is NOT Enabled!");
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                bluetoothAdapter.enable();
            }
        }

        String macAddress = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");

        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);

        Log.d("Address"," mac "+macAddress+" device "+device);
    }

    private void scan(){
            // TODO Auto-generated method stub
            btArrayAdapter.clear();
            bluetoothAdapter.startDiscovery();
            checkConnected();

            Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice d : pairedDevices) {
                    String deviceName = d.getName();
                    String macAddress = d.getAddress();
                    Log.i("log", "paired device: " + deviceName + " at " + macAddress);
                    btArrayAdapter.add("Paired = " + deviceName + "\n" + macAddress);
                    btArrayAdapter.notifyDataSetChanged();
                    // do what you need/want this these list items
                }
            }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBlueToothState();
        }


    }

    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                btArrayAdapter.add("visible = " + device.getName() + "\n" + device.getAddress());
                btArrayAdapter.notifyDataSetChanged();
            }
        }
    };


    public void checkConnected() {
        // true == headset connected && connected headset is support hands free
        int state = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(3);
        int state1 = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(1000);
        int state2 = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(100);
        int state3 = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(0);
        int state4 = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(-1);
        int state5 = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(12);
//        int state5 = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.PRIORITY_ON);
//        int state5 = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.MAP);
//        int state5 = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.MAP);
        Log.d("State connected", BluetoothProfile.STATE_CONNECTED + " --- "+" ,"+state+" ,"+state1+" ,"+state2+" ,"+state3+" ,"+state4+" ,"+state5);
//        if (state != BluetoothProfile.STATE_CONNECTED)
//            return;

        try {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this, serviceListener, BluetoothProfile.HEALTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BluetoothProfile.ServiceListener serviceListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceDisconnected(int profile) {

        }

        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            for (BluetoothDevice device : proxy.getConnectedDevices()) {
                Log.i("onServiceConnected", "|" + device.getName() + " | " + device.getAddress() + " | " + proxy.getConnectionState(device) + "(connected = "
                        + BluetoothProfile.STATE_CONNECTED + ")");
            }

            BluetoothAdapter.getDefaultAdapter().closeProfileProxy(profile, proxy);
        }
    };
}