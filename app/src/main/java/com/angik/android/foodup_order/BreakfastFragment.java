package com.angik.android.foodup_order;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.angik.android.foodup_order.Model.AdapterItemStatus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BreakfastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BreakfastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BreakfastFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference databaseReference;//Given quantity
    private DatabaseReference db_ref_orderedQuantity;
    private DatabaseReference db_ref_store;

    private String currentVendor;

    private RecyclerView breakfastItemStatus;

    private SharedPreferences sp_vendor;

    private int count;

    private ArrayList<String> itemName = new ArrayList<>();
    private ArrayList<String> givenQuantity = new ArrayList<>();
    private ArrayList<String> orderedQuantity = new ArrayList<>();

    private AdapterItemStatus adapter;

    private OnFragmentInteractionListener mListener;

    public BreakfastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreakfastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BreakfastFragment newInstance(String param1, String param2) {
        BreakfastFragment fragment = new BreakfastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sp_vendor = Objects.requireNonNull(getActivity()).getSharedPreferences("vendor", Context.MODE_PRIVATE);

        currentVendor = sp_vendor.getString("currentVendor", null);


        getData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breakfast, container, false);

        breakfastItemStatus = view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        breakfastItemStatus.setLayoutManager(linearLayoutManager);
        breakfastItemStatus.setHasFixedSize(true);


        return view;
    }

    private void getData() {

        switch (currentVendor) {
            case "Faisal":
                databaseReference = FirebaseDatabase.getInstance().getReference("Available Items Quantity Faisal");
                db_ref_orderedQuantity = FirebaseDatabase.getInstance().getReference("Ordered Amount Faisal");
                db_ref_store = FirebaseDatabase.getInstance().getReference("Allahr Daan");
                break;

            case "Khan":
                databaseReference = FirebaseDatabase.getInstance().getReference("Available Items Quantity");
                db_ref_orderedQuantity = FirebaseDatabase.getInstance().getReference("Order Amount");
                db_ref_store = FirebaseDatabase.getInstance().getReference("Cooling Corner");
                break;


            case "Shaowal":
                databaseReference = FirebaseDatabase.getInstance().getReference("Available Items Quantity Shaowal");
                db_ref_orderedQuantity = FirebaseDatabase.getInstance().getReference("Ordered Amount Shaowal");
                db_ref_store = FirebaseDatabase.getInstance().getReference("Shaowal Restora");
                break;

            case "Kaftan":
                databaseReference = FirebaseDatabase.getInstance().getReference("Available Items Quantity Kaftan");
                db_ref_orderedQuantity = FirebaseDatabase.getInstance().getReference("Ordered Amount Kaftan");
                db_ref_store = FirebaseDatabase.getInstance().getReference("Kaftan");
                break;
        }


        db_ref_orderedQuantity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String quantity = Objects.requireNonNull(postSnapshot.getValue()).toString();//Values
                        itemName.add(postSnapshot.getKey());//Keys
                        orderedQuantity.add(quantity);

                        //count = (int) dataSnapshot.getChildrenCount();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (itemName.contains(postSnapshot.getKey())) {
                            String quantity = Objects.requireNonNull(postSnapshot.getValue()).toString();//Values
                            givenQuantity.add(quantity);
                        }
                    }
                    breakfastItemStatus.setAdapter(new AdapterItemStatus(getContext(), itemName, givenQuantity, orderedQuantity));
                    checkItemValidity(itemName, givenQuantity, orderedQuantity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkItemValidity(final ArrayList<String> itemName, ArrayList<String> givenQuantity, ArrayList<String> orderedQuantity) {
        for (int i = 0; i < itemName.size(); i++) {
            if ((Integer.parseInt(orderedQuantity.get(i))) == (Integer.parseInt(givenQuantity.get(i)) - 2)) {
                final int finalI = i;
                db_ref_store.child("Items").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.child(itemName.get(finalI)).exists()) {
                                db_ref_store.child("Items").child(itemName.get(finalI)).setValue(null);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
