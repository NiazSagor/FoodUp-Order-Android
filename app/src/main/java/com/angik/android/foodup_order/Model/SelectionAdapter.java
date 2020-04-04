package com.angik.android.foodup_order.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angik.android.foodup_order.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder> {

    private SharedPreferences sp_vendor;

    private ArrayList<String> mName;//Item names
    private ArrayList<String> mPrice;//Items prices
    private String mNode;//Node name on which add or remove operation to be happened
    private String mLunchORBreakfast;//Node name on which add or remove operation to be happened
    private String currentVendor;

    private Context mContext;
    private OnItemClickListener mListener;

    //Own interface to handle click listener
    public interface OnItemClickListener {
        void onItemClick(int position);//Default method to override which takes an argument which is position
    }

    //Public constructor for the on click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class SelectionViewHolder extends RecyclerView.ViewHolder {

        private TextView name;//Item name text view
        private TextView price;//Item price text view
        private CheckBox checkBox;//Check box where add or remove happens
        private EditText editText;

        /* As View holder class is normally static, it can not access other variables outside itself
         * So we are getting listener from store view holder default constructor
         * As well as other parameters such as mContext, mName, mPrice and mNode
         * These parameters have functionality inside the @SelectionViewHolder
         * That's why we are getting via default constructor @line number 120
         */
        SelectionViewHolder(@NonNull View itemView, final OnItemClickListener listener, final Context mContext,
                            final ArrayList<String> mName, final ArrayList<String> mPrice, final String mNode, final String currentVendorItemAdd, final String currnetVendorItemQuantity) {
            super(itemView);

            //Database ref for our node where we assign and remove values
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(currentVendorItemAdd);
            final DatabaseReference db_ref_AvailableItemsQuantity = FirebaseDatabase.getInstance().getReference(currnetVendorItemQuantity);

            //Finding and initializing the views
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            checkBox = itemView.findViewById(R.id.checkbox1);
            editText = itemView.findViewById(R.id.quantityEditText);
            editText.clearFocus();

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
            //This invokes when the state of the checkbox changes
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        //If it is checked then we want to push that item and price to the database
                        int position = getAdapterPosition();
                        //If the quantity is input then proceed or stop
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Please input the quantity");
                            checkBox.setChecked(false);
                            //Toast.makeText(mContext, "Please input the quantity", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        /*if (mNode.equals("Beef") || mNode.equals("Rice") || mNode.equals("Mejbani") || mNode.equals("Biryani")) {
                            DatabaseReference db_ref_featuredItems = FirebaseDatabase.getInstance().getReference("Featured Items").child(mNode);
                            db_ref_featuredItems.child("Items").child(mName.get(position)).setValue(mPrice.get(position));
                            db_ref_AvailableItemsQuantity.child(mName.get(position)).setValue(editText.getText().toString());
                            Toast.makeText(mContext, "Item Added", Toast.LENGTH_SHORT).show();
                            return;
                        }*/
                        db_ref_AvailableItemsQuantity.child(mName.get(position)).setValue(editText.getText().toString());
                        databaseReference.child(mNode).child(mName.get(getAdapterPosition())).setValue(mPrice.get(position));
                        Toast.makeText(mContext, "Item Added", Toast.LENGTH_SHORT).show();
                    } else {
                        //If it not checked then we want to delete that item from database
                        int position = getAdapterPosition();
                        /*if (mNode.equals("Beef") || mNode.equals("Rice") || mNode.equals("Mejbani") || mNode.equals("Biryani")) {
                            DatabaseReference db_ref_featuredItems = FirebaseDatabase.getInstance().getReference("Featured Items").child(mNode);
                            db_ref_featuredItems.child("Items").child(mName.get(position)).setValue(null);
                            Toast.makeText(mContext, "Item Removed", Toast.LENGTH_SHORT).show();
                            return;
                        }*/
                        databaseReference.child(mNode).child(mName.get(position)).setValue(null);
                        Toast.makeText(mContext, "Item Removed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //Public default constructor
    public SelectionAdapter(Context context, ArrayList<String> name, ArrayList<String> price, String node, String lunchORbreakfast) {
        mContext = context;
        mName = name;
        mPrice = price;
        mNode = node;//This indicates on which node the addition or removal operation will be happening eg Biscuits, Bun like these.
        mLunchORBreakfast = lunchORbreakfast;


        sp_vendor = mContext.getSharedPreferences("vendor", Context.MODE_PRIVATE);
        currentVendor = sp_vendor.getString("currentVendor", null);
    }

    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_custom, viewGroup, false);
        if (mLunchORBreakfast.equals("breakfast")) {
            switch (currentVendor) {
                case "Kutub":
                    return new SelectionViewHolder(v, mListener, mContext, mName, mPrice, mNode, "Faisal Items Breakfast", "Available Items Quantity Faisal");//Passing arguments that we need in the ViewHolder class
                case "Khan":
                    return new SelectionViewHolder(v, mListener, mContext, mName, mPrice, mNode, "Khan Items Breakfast", "Available Items Quantity Khan");//Passing arguments that we need in the ViewHolder class
                default:
                    return new SelectionViewHolder(v, mListener, mContext, mName, mPrice, mNode, "", "");//Passing arguments that we need in the ViewHolder class
            }
        } else {
            switch (currentVendor) {
                case "Kutub":
                    return new SelectionViewHolder(v, mListener, mContext, mName, mPrice, mNode, "Allahr Daan", "Available Items Quantity Faisal");//Passing arguments that we need in the ViewHolder class
                case "Khan":
                    return new SelectionViewHolder(v, mListener, mContext, mName, mPrice, mNode, "Cooling Corner", "Available Items Quantity");//Passing arguments that we need in the ViewHolder class
                case "Shaowal":
                    return new SelectionViewHolder(v, mListener, mContext, mName, mPrice, mNode, "Shaowal Restora", "Available Items Quantity Shaowal");
                case "Kaftan":
                    return new SelectionViewHolder(v, mListener, mContext, mName, mPrice, mNode, "Kaftan", "Available Items Quantity Kaftan");
                default:
                    return new SelectionViewHolder(v, mListener, mContext, mName, mPrice, mNode, "", "");//Passing arguments that we need in the ViewHolder class
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final SelectionViewHolder selectionViewHolder, final int i) {
        selectionViewHolder.name.setText(mName.get(i));
        selectionViewHolder.price.setText(mPrice.get(i) + " Tk");
    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    public void addLunchItems(String node, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Featured Items").child(node);
        databaseReference.child("Items").child(mName.get(position)).setValue(mPrice.get(position));
    }

    public void removeLunchItems(String node, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Featured Items").child(node);
        databaseReference.child("Items").child(mName.get(position)).setValue(null);
    }
}
