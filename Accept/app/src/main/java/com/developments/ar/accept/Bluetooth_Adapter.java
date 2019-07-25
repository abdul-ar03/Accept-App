package com.developments.ar.accept;
import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Bluetooth_Adapter extends ArrayAdapter<String> {

    private Activity mContext;
    private List<String> Bt_name_list = new ArrayList<>();
    private List<String> Bt_addr_list = new ArrayList<>();

    public Bluetooth_Adapter(Activity context, List<String> list1, List<String> list2) {
        super(context,0,list1);
        mContext = context;
        Bt_name_list = list1;
        Bt_addr_list = list2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.bt_list_item,parent,false);
            TextView name = (TextView) listItem.findViewById(R.id.bt_name);
            name.setText(Bt_name_list.get(position));

            TextView release = (TextView) listItem.findViewById(R.id.bt_addr);
            release.setText(Bt_addr_list.get(position));
            return listItem;
    }
}