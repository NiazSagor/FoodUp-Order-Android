package com.angik.android.foodup_order.Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.angik.android.foodup_order.R;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class OrderDetailAdapter extends ArrayAdapter<String> {
    private Activity mC;//Getting current activity

    private ArrayList<String> mItemName;
    private ArrayList<Integer> mItemPrice;

    public OrderDetailAdapter(Activity c, ArrayList<String> itemName, ArrayList<Integer> itemPrice) {
        super(c, R.layout.order_detail_layout);

        this.mC = c;
        this.mItemName = itemName;
        this.mItemPrice = itemPrice;
    }

    static class ViewHolder {
        TextView itemName, itemCount;
    }

    @Override
    public int getCount() {
        return mItemName.size();
    }

    @NonNull
    @SuppressLint({"ViewHolder", "SetTextI18n"})
    public View getView(final int position, View view, final ViewGroup parent) {

        final ViewHolder holder = new ViewHolder();
        LayoutInflater inflater;

        if (view == null) {
            inflater = (LayoutInflater) mC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_detail_layout, parent, false);

            holder.itemName = view.findViewById(R.id.itemName);
            holder.itemCount = view.findViewById(R.id.itemCount);
            //Setting the values we got from the array list, in their view position
            holder.itemName.setText(mItemName.get(position));
            holder.itemCount.setText("" + mItemPrice.get(position));
        }
        return view;
    }
}
