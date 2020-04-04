package com.angik.android.foodup_order.Super;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.angik.android.foodup_order.DetailActivity;
import com.angik.android.foodup_order.Model.OrderAdapter;
import com.angik.android.foodup_order.R;
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
 * {@link SuperOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuperOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuperOrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private ArrayList<String> id;

    private SharedPreferences sp_vendor;

    private DatabaseReference databaseReference;

    private OnFragmentInteractionListener mListener;

    public SuperOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuperOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuperOrderFragment newInstance(String param1, String param2) {
        SuperOrderFragment fragment = new SuperOrderFragment();
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

        sp_vendor = getActivity().getSharedPreferences("vendor", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_super_order, container, false);

        switch (sp_vendor.getString("currentVendor", null)) {
            case "Shaowal":
                databaseReference = FirebaseDatabase.getInstance().getReference("Order Shaowal").child("Pick Up From Store");
                break;
            case "Kaftan":
                databaseReference = FirebaseDatabase.getInstance().getReference("Order Kaftan").child("Pick Up From Store");
                break;
            case "Khan":
                databaseReference = FirebaseDatabase.getInstance().getReference("Building").child("Pick Up From Store");
                break;
            case "Kutub":
                databaseReference = FirebaseDatabase.getInstance().getReference("Order Kutub").child("Pick Up From Store");
                break;

            default:
                databaseReference = FirebaseDatabase.getInstance().getReference("Building").child("Pick Up From Store");
        }

        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    id = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String orderID = Objects.requireNonNull(postSnapshot.getValue()).toString();//Values
                        id.add(orderID);
                    }

                    mAdapter = new OrderAdapter(getActivity(), id);
                    mRecyclerView.setAdapter(mAdapter);

                    mAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("id", id.get(position));
                            startActivity(intent);
                        }

                        @Override
                        public void onDeleteClick(int position) {
                            if (position == 0) {
                                showAlertDialog();
                                return;
                            }
                            databaseReference.child(id.get(position)).setValue(null);
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
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

    public void showAlertDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("এটা ডিলেট করার দরকার নাই");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
