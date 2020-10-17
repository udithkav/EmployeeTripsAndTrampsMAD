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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Work_Assign#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Work_Assign extends Fragment {
    TextView name2,employeeID,jobRole,gender1;
    Button add;
    DatabaseReference reff;
    int maxid=0;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work__assign, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name2 =(TextView) getView().findViewById(R.id.name1);
        employeeID =(TextView) getView().findViewById(R.id.emp_id);
        jobRole =(TextView) getView().findViewById(R.id.job_role);
        gender1 =(TextView) getView().findViewById(R.id.gender2);
        add =(Button) getView().findViewById(R.id.assign);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Assign asn = new Assign();
                String name = name2.getText().toString().trim();
                String emp_id = employeeID.getText().toString().trim();
                String role1 = jobRole.getText().toString().trim();
                String gender = gender1.getText().toString().trim();

                Boolean emailcheck = isValid(name);

                asn.setGender(gender);
                asn.setRole(role1);
                asn.setEmp_id(emp_id);
                asn.setName(name);

                if(emailcheck==true){
                    reff = FirebaseDatabase.getInstance().getReference().child("Assign");
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                maxid = (int) dataSnapshot.getChildrenCount();
                                maxid = maxid + 1;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    reff.child(String.valueOf(maxid)).setValue(asn);
                    Toast.makeText(getActivity(), "Data Successfully Inserted", Toast.LENGTH_SHORT).show();
                    emailcheck=false;
                }
                else
                {
                    Toast.makeText(getActivity(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }

            }

        });



    }
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}