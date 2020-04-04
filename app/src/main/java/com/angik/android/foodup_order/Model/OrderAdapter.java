package com.angik.android.foodup_order.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angik.android.foodup_order.MainActivity;
import com.angik.android.foodup_order.R;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<String> mID;
    private Context mContext;
    private OnItemClickListener mListener;

    //Own interface to handle click listener
    public interface OnItemClickListener {
        void onItemClick(int position);//Default method to override which takes an argument which is position
        void onDeleteClick(int position);
    }

    //Public constructor for the on click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private ImageView delete;

        //As View holder class is normally static, so we are getting listener from store view holder default constructor
        OrderViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            delete = itemView.findViewById(R.id.delete);

            //Then setting on click listener on the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);//Which invokes the method in the interface and passes the current position
                        }
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);//Which invokes the method in the interface and passes the current position
                        }
                    }
                }
            });
        }
    }

    public OrderAdapter(Context context, ArrayList<String> id) {
        mContext = context;
        mID = id;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderlist, viewGroup, false);
        return new OrderViewHolder(v, mListener);//Passing listener as well
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder orderViewHolder, final int i) {
        //storeViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
        orderViewHolder.id.setText(mID.get(i));
    }

    @Override
    public int getItemCount() {
        return mID.size();
    }

}
