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
 * Use the {@link inventory_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class inventory_details extends Fragment {
    EditText purchaseDate,inventoryType,inventoryBrand,amountSpent;
    EditText invNumber;
    Button add;
    Button update, delete;
    DatabaseReference reff;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public inventory_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment inventory_details.
     */
    // TODO: Rename and change types and number of parameters
    public static inventory_details newInstance(String param1, String param2) {
        inventory_details fragment = new inventory_details();
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
        return inflater.inflate(R.layout.fragment_inventory_details, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        purchaseDate = (EditText) getView().findViewById(R.id.purchaseDate1);
        inventoryType = (EditText) getView().findViewById(R.id.inventoryType1);
        inventoryBrand = (EditText) getView().findViewById(R.id.Brand1);
        amountSpent = (EditText) getView().findViewById(R.id.amount1);
        invNumber = (EditText) getView().findViewById(R.id.editText2);
        add = (Button) getView().findViewById(R.id.search);
        update =(Button) getView().findViewById(R.id.update);
        delete = (Button) getView().findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inventory inv = new Inventory();
                inv.setId(invNumber.getText().toString());
                reff = FirebaseDatabase.getInstance().getReference().child("Inventory");
                reff.child(inv.getId()).removeValue();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                reff = FirebaseDatabase.getInstance().getReference().child("Inventory");
               Inventory lof = new Inventory();

               lof.setPurchaseDate(purchaseDate.getText().toString().trim());
               lof.setInventory_type(inventoryType.getText().toString().trim());
               lof.setBrand(inventoryBrand.getText().toString().trim());
               lof.setAmout(amountSpent.getText().toString().trim());
               lof.setId(invNumber.getText().toString().trim());

               hashMap.put("purchaseDate",lof.getPurchaseDate());
               hashMap.put("inventory_type",lof.getInventory_type());
               hashMap.put("brand",lof.getBrand());
               hashMap.put("amout",lof.getAmout());

               reff.child(lof.getId()).updateChildren(hashMap);
                Toast.makeText(getActivity(),"Data Successfully Updated", Toast.LENGTH_SHORT).show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("Inventory").child(invNumber.getText().toString());
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Inventory inv = new Inventory();
                            inv.setAmout(snapshot.child("amout").getValue().toString());
                            inv.setBrand(snapshot.child("brand").getValue().toString());
                            inv.setInventory_type(snapshot.child("inventory_type").getValue().toString());
                            inv.setPurchaseDate(snapshot.child("purchaseDate").getValue().toString());
                            purchaseDate.setText(inv.getPurchaseDate());
                            inventoryType.setText(inv.getInventory_type());
                            inventoryBrand.setText(inv.getBrand());
                            amountSpent.setText(inv.getAmout());


                        }
                        else{
                            purchaseDate.setText("Inventory Doesn't Exist");
                            inventoryType.setText("Inventory Doesn't Exist");
                            inventoryBrand.setText("Inventory Doesn't Exist");
                            amountSpent.setText("Inventory Doesn't Exist");
                            Toast.makeText(getActivity(),"Inventory Doesn't Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

}