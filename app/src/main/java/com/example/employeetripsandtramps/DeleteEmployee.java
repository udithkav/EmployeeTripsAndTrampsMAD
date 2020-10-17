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
 * Use the {@link DeleteEmployee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteEmployee extends Fragment {
    TextView Empl_Number,f_Name,l_Name,d_O_B,e_M_A_I_L,p_osition,n_i_c,p_a_s_s_wrd;
    EditText search_employee;
    Button searchButton,deleteButton;
    DatabaseReference reff;
    String userTypeRequired = "Human Resource Manager";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteEmployee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteEmployee.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteEmployee newInstance(String param1, String param2) {
        DeleteEmployee fragment = new DeleteEmployee();
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
        return inflater.inflate(R.layout.fragment_delete_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);        super.onViewCreated(view, savedInstanceState);

        Empl_Number = (TextView) getView().findViewById(R.id.empNumber);
        f_Name = (TextView) getView().findViewById(R.id.first_name);
        l_Name = (TextView) getView().findViewById(R.id.last_name);
        d_O_B = (TextView) getView().findViewById(R.id.date_of_birth);
        e_M_A_I_L = (TextView) getView().findViewById(R.id.e_mail);
        p_osition = (TextView) getView().findViewById(R.id.occupation);
        n_i_c = (TextView) getView().findViewById(R.id.national_ID);
        p_a_s_s_wrd = (TextView) getView().findViewById(R.id.pass_word);
        search_employee = (EditText) getView().findViewById(R.id.search_Employee_Number);
        searchButton = (Button) getView().findViewById(R.id.searchButton);
        deleteButton = (Button)getView().findViewById(R.id.buttondeleteEmp);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff= FirebaseDatabase.getInstance().getReference().child("Employee").child(search_employee.getText().toString().trim());
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Employee e1 = new Employee();
                            e1.setEmpNumber(search_employee.getText().toString());
                            e1.setPosition(snapshot.child("position").getValue().toString());
                            e1.setFirstName(snapshot.child("firstName").getValue().toString());
                            e1.setLastName(snapshot.child("lastName").getValue().toString());
                            e1.setEmail(snapshot.child("email").getValue().toString());
                            e1.setNic(snapshot.child("nic").getValue().toString());
                            e1.setPassword(snapshot.child("password").getValue().toString());
                            e1.setDob(snapshot.child("dob").getValue().toString());

                            Empl_Number.setText(e1.getEmpNumber());
                            f_Name.setText(e1.getFirstName());
                            l_Name.setText(e1.getLastName());
                            d_O_B.setText(e1.getDob());
                            e_M_A_I_L.setText(e1.getEmail());
                            p_osition.setText(e1.getPosition());
                            n_i_c.setText(e1.getNic());
                            p_a_s_s_wrd.setText(e1.getPassword());

                        }
                        else{
                            Empl_Number.setText("DOES NOT EXIST");
                            f_Name.setText("DOES NOT EXIST");
                            l_Name.setText("DOES NOT EXIST");
                            d_O_B.setText("DOES NOT EXIST");
                            e_M_A_I_L.setText("DOES NOT EXIST");
                            p_osition.setText("DOES NOT EXIST");
                            n_i_c.setText("DOES NOT EXIST");
                            p_a_s_s_wrd.setText("DOES NOT EXIST");
                            Toast.makeText(getActivity(),"The Employee Number You Entered Does Not Exist!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("Employee");
                reff.child(search_employee.getText().toString().trim()).removeValue();
            }
        });



    }

}