package com.example.employeetripsandtramps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link expenses_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class expenses_details extends Fragment {
TextView searchExpenseID,userName,amount,details;
Button update,delete,search;
DatabaseReference dbRef;
SearchExpenses expenses;
Boolean viewCheck = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public expenses_details() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static expenses_details newInstance(String param1, String param2) {
        expenses_details fragment = new expenses_details();
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
        return inflater.inflate(R.layout.fragment_expenses_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchExpenseID = (TextView)  getView().findViewById(R.id.editid);
        userName = (TextView)  getView().findViewById(R.id.editname);
        amount = (TextView)  getView().findViewById(R.id.editExpAmount);
        details = (TextView)  getView().findViewById(R.id.editExpDetails);
        update = (Button) getView().findViewById(R.id.updateExpButton);
        delete = (Button) getView().findViewById(R.id.expDeleteButton);
        search = (Button) getView().findViewById(R.id.searchExpenseDetails);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Expense");
                dbRef.child(searchExpenseID.getText().toString().trim());
                viewCheck = false;
            }
        });



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Expense").child(searchExpenseID.getText().toString().trim());
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            expenses.setExpenseID(searchExpenseID.getText().toString().trim());
                            expenses.setUsern(snapshot.child("usern").getValue().toString());
                            expenses.setAmountt(snapshot.child("amountt").getValue().toString());
                            expenses.setDetails(snapshot.child("details").getValue().toString());

                            userName.setText(expenses.getUsern());
                            amount.setText(expenses.getAmountt());
                            details.setText(expenses.getDetails());
                            viewCheck = true;
                        }
                        else{
                            userName.setText("Does not Exist");
                            amount.setText("Does not Exist");
                            details.setText("Does not Exist");
                            Toast.makeText(getActivity(),"Expense doesnt not Exist!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewCheck == true){
                    final SearchExpenses e1 = null;
                    e1.setUsern(userName.getText().toString().trim());
                    e1.setDetails(details.getText().toString().trim());
                    e1.setAmountt(amount.getText().toString().trim());
                    e1.setExpenseID(searchExpenseID.getText().toString().trim());

                    dbRef = FirebaseDatabase.getInstance().getReference().child("Expense");
                    HashMap hashMap = new HashMap();
                    hashMap.put("amountt",e1.getAmountt());
                    hashMap.put("usern",e1.getUsern());
                    hashMap.put("details",e1.getDetails());

                    dbRef.child(String.valueOf(e1.getExpenseID())).updateChildren(hashMap);
                    Toast.makeText(getActivity(), "Data Succesfully Updated", Toast.LENGTH_SHORT).show();
                    viewCheck = false;
                }
                else{
                    Toast.makeText(getActivity(),"Please Search for a Expense first!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}