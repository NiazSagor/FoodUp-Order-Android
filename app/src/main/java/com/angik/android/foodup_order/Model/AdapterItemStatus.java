package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angik.android.foodup_order.R;

import java.util.ArrayList;

public class AdapterItemStatus extends RecyclerView.Adapter<AdapterItemStatus.StatusViewHolder> {

    private ArrayList<String> mItemName;
    private ArrayList<String> mGivenQuantity;
    private ArrayList<String> mOrderedQuantity;
    private Context mContext;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView given_quantity;
        private TextView ordered_quantity;

        StatusViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            given_quantity = itemView.findViewById(R.id.givenQuantity);
            ordered_quantity = itemView.findViewById(R.id.orderedQuantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            //TODO from here we write delete
        }
    }

    public AdapterItemStatus(Context context, ArrayList<String> itemName, ArrayList<String> givenQuantity, ArrayList<String> orderedQuantity) {
        mContext = context;

        mItemName = itemName;
        mGivenQuantity = givenQuantity;
        mOrderedQuantity = orderedQuantity;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.status_layout, viewGroup, false);
        return new StatusViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final StatusViewHolder statusViewHolder, final int i) {
        statusViewHolder.itemName.setText(mItemName.get(i));
        statusViewHolder.given_quantity.setText(mGivenQuantity.get(i));
        statusViewHolder.ordered_quantity.setText(mOrderedQuantity.get(i));
    }

    @Override
    public int getItemCount() {
        return mOrderedQuantity.size();
    }
}
