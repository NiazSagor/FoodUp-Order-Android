package com.angik.android.foodup_order;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.angik.android.foodup_order.Model.SelectionAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FastFoodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FastFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("ALL")
public class FastFoodFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private SelectionAdapter mAdapter;
    private CheckBox markAsAvailable;

    private OnFragmentInteractionListener mListener;

    public FastFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FastFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FastFoodFragment newInstance(String param1, String param2) {
        FastFoodFragment fragment = new FastFoodFragment();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fast_food, container, false);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Available Items Today");

        markAsAvailable = view.findViewById(R.id.mark);
        markAsAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    databaseReference.child("Fast Foods").setValue("Fast Foods");
                } else {
                    databaseReference.child("Fast Foods").setValue(null);
                }
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerView);

        ArrayList<String> name = new ArrayList<>();
        name.add("Alur Chap");
        name.add("Beguni");
        name.add("Chicken Bread");
        name.add("Chicken Bun");
        name.add("Chicken Fry");
        name.add("Chicken Porota");
        name.add("Chona");
        name.add("Dim Chap");
        name.add("Hot Chicken");
        name.add("Onthon");
        name.add("Package(Chona Muri)");
        name.add("Piaju");
        name.add("Pizza");
        name.add("Shingara");


        ArrayList<String> price = new ArrayList<>();
        price.add("3");
        price.add("2");
        price.add("20");
        price.add("35");
        price.add("60");
        price.add("50");
        price.add("10");
        price.add("10");
        price.add("40");
        price.add("10");
        price.add("18");
        price.add("2");
        price.add("40");
        price.add("5");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SelectionAdapter(getContext(), name, price, "Fast Foods", "breakfast");
        mRecyclerView.setAdapter(mAdapter);

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
}
