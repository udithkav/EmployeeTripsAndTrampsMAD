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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Work_assign_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Work_assign_details extends Fragment {
    EditText name_2,employee_ID,job_Role,gender_1;
    EditText empno;
    Button add;
    Button update,delete;
    DatabaseReference reff;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Work_assign_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Work_assign_details.
     */
    // TODO: Rename and change types and number of parameters
    public static Work_assign_details newInstance(String param1, String param2) {
        Work_assign_details fragment = new Work_assign_details();
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
        return inflater.inflate(R.layout.fragment_work_assign_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name_2 =(EditText) getView().findViewById(R.id.ename1);
        employee_ID =(EditText) getView().findViewById(R.id.employeeid1);
        job_Role =(EditText) getView().findViewById(R.id.jobrole1);
        gender_1 =(EditText) getView().findViewById(R.id.empgender1);
        empno =(EditText) getView().findViewById(R.id.search1);
        update =(Button)getView().findViewById(R.id.update1);
        delete =(Button) getView().findViewById(R.id.delete1);
        add =(Button) getView().findViewById(R.id.search2);

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Assign asn = new Assign();
                asn.setEmpno(empno.getText().toString());
                reff = FirebaseDatabase.getInstance().getReference().child("Assign");
                reff.child(asn.getEmpno()).removeValue();
            }
        });

        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            HashMap hashMap = new HashMap();
            reff = FirebaseDatabase.getInstance().getReference().child("Assign");
            Assign ass = new Assign();

            ass.setName(name_2.getText().toString().trim());
            ass.setEmp_id(employee_ID.getText().toString().trim());
            ass.setRole(job_Role.getText().toString().trim());
            ass.setGender(gender_1.getText().toString().trim());
            ass.setEmpno(empno.getText().toString().trim());

            hashMap.put("name",ass.getName());
            hashMap.put("emp_id",ass.getEmp_id());
            hashMap.put("role",ass.getRole());
            hashMap.put("gender",ass.getGender());

            reff.child(ass.getEmpno()).updateChildren(hashMap);
            Toast.makeText(getActivity(),"Data Succesfully Updated",Toast.LENGTH_SHORT).show();

         }
        });

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            reff = FirebaseDatabase.getInstance().getReference().child("Assign").child(empno.getText().toString());
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Assign assi = new Assign();
                        assi.setGender(dataSnapshot.child("gender").getValue().toString());
                        assi.setRole(dataSnapshot.child("role").getValue().toString());
                        assi.setEmp_id(dataSnapshot.child("emp_id").getValue().toString());
                        assi.setName(dataSnapshot.child("name").getValue().toString());
                        name_2.setText(assi.getName());
                        employee_ID.setText(assi.getEmp_id());
                        job_Role.setText(assi.getRole());
                        gender_1.setText(assi.getGender());

                    }
                    else
                        {
                        name_2.setText("Employee has not Assigned");
                        employee_ID.setText("Employee has not Assigned");
                        job_Role.setText("Employee has not Assigned");
                        gender_1.setText("Employee has not Assigned");
                        Toast.makeText(getActivity(), "Employee has not Assigned", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }

        });
    }
}