package com.example.employeetripsandtramps;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEmp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEmp extends Fragment {
    EditText firstName,lastName,dateOfBirth,email,nic,password;
    Button add;
    Spinner position;
    DatabaseReference reff;
    private FirebaseAuth mAuth;
    int maxid=0;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public AddEmp() {
        // Required empty public constructor
    }
    public static AddEmp newInstance(String param1, String param2) {
        AddEmp fragment = new AddEmp();
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

        return inflater.inflate(R.layout.fragment_add_emp, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        String[] items = new String[]{"Human Resource Manager", "Inventory Manager","Finance Manager","Workflow Manager","Other"};
        position = (Spinner)getView().findViewById(R.id.selectPosition);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        position.setAdapter(adapter);

        firstName =(EditText) getView().findViewById(R.id.FirstName);
        lastName =(EditText) getView().findViewById(R.id.lastName);
        dateOfBirth =(EditText) getView().findViewById(R.id.dateofbirth);
        email =(EditText) getView().findViewById(R.id.email);
        nic =(EditText) getView().findViewById(R.id.nationalID);
        password =(EditText) getView().findViewById(R.id.password);
        add = (Button)getView().findViewById(R.id.addButton);
        position = (Spinner)getView().findViewById(R.id.selectPosition);
        mAuth = FirebaseAuth.getInstance();
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Employee emp = new Employee();
                String fName = firstName.getText().toString().trim();
                String LName = lastName.getText().toString().trim();
                String d_ob =dateOfBirth.getText().toString().trim();
                String e_mail = email.getText().toString().trim();
                String n_ic = nic.getText().toString().trim();
                String pass_word = password.getText().toString().trim();
                String pos_tion = position.getSelectedItem().toString();
                emp.setFirstName(fName);
                emp.setLastName(LName);
                emp.setDob(d_ob);
                emp.setEmail(e_mail);
                emp.setNic(n_ic);
                emp.setPassword(pass_word);
                emp.setPosition(pos_tion);

                reff= FirebaseDatabase.getInstance().getReference().child("Employee");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            maxid = (int) snapshot.getChildrenCount();
                            maxid=maxid+1;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reff.child(String.valueOf(maxid)).setValue(emp);
                mAuth.createUserWithEmailAndPassword(emp.getEmail(), emp.getPassword());

                Toast.makeText(getActivity(),"Data Succesfully Inserted",Toast.LENGTH_SHORT).show();
                clearControls();

            }
        });









    }
    public void clearControls(){
        firstName.setText("");
        lastName.setText("");
        dateOfBirth.setText("");
        email.setText("");
        nic.setText("");
        password.setText("");
    }
}