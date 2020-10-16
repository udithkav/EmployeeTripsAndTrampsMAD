package com.example.employeetripsandtramps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Work_Assign#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Work_Assign extends Fragment {
    EditText name, eid, role, gender;

    Button assign;
    DatabaseReference reff;
    int maxid = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Work_Assign() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Work_Assign.
     */
    // TODO: Rename and change types and number of parameters
    public static Work_Assign newInstance(String param1, String param2) {
        Work_Assign fragment = new Work_Assign();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (EditText) getView().findViewById(R.id.name);
        eid = (EditText) getView().findViewById(R.id.employee_id);
        role = (EditText) getView().findViewById(R.id.role);
        gender = (EditText) getView().findViewById(R.id.gender);

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Assign asn = new Assign();

                String Name = name.getText().toString().trim();
                String Eid = eid.getText().toString().trim();
                String Role = role.getText().toString().trim();
                String Gender = gender.getText().toString().trim();
                asn.setGender(Gender);
                asn.setRole(Role);
                asn.setEid(Eid);
                asn.setName(Name);

                reff = FirebaseDatabase.getInstance().getReference().child("Assign");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            maxid = (int) snapshot.getChildrenCount();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                maxid = maxid + 1;
                reff.child(String.valueOf(maxid)).setValue(asn);
                Toast.makeText(getActivity(), "Data Successfully Inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}