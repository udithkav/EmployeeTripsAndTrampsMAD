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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Work_Details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Work_Details extends Fragment {
    EditText name, eid, role, gender;
    EditText invNumber;
    Button assign;
    Button update, delete;
    DatabaseReference reff;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Work_Details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Work_Details.
     */
    // TODO: Rename and change types and number of parameters
    public static Work_Details newInstance(String param1, String param2) {
        Work_Details fragment = new Work_Details();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work__details, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (EditText) getView().findViewById(R.id.name1);
        eid = (EditText) getView().findViewById(R.id.employee_id2);
        role = (EditText) getView().findViewById(R.id.role1);
        gender = (EditText) getView().findViewById(R.id.gender2);
        assign= (Button) getView().findViewById(R.id.assign);
        update =(Button) getView().findViewById(R.id.update1);
        delete = (Button) getView().findViewById(R.id.delete1);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                reff = FirebaseDatabase.getInstance().getReference().child("Assign");
                Assign asn = new Assign();

                asn.setName(name.getText().toString().trim());
                asn.setEid(eid.getText().toString().trim());
                asn.setRole(role.getText().toString().trim());
                asn.setGender(gender.getText().toString().trim());


            }
        });
    }
}