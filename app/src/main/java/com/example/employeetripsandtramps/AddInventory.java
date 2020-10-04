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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddInventory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddInventory extends Fragment {
    TextView purchaseDate,inventoryType,inventoryBrand,amountSpent;
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

    public AddInventory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddInventory.
     */
    // TODO: Rename and change types and number of parameters
    public static AddInventory newInstance(String param1, String param2) {
        AddInventory fragment = new AddInventory();
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
        return inflater.inflate(R.layout.fragment_add_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        purchaseDate = (TextView) getView().findViewById(R.id.purchaseDate);
        inventoryType = (TextView) getView().findViewById(R.id.inventoryType);
        inventoryBrand = (TextView) getView().findViewById(R.id.Brand);
        amountSpent = (TextView) getView().findViewById(R.id.invamount);
        add = (Button) getView().findViewById(R.id.addInv);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inventory inv = new Inventory();

                String purchase_Date =purchaseDate.getText().toString().trim() ;
                String inventory_type=inventoryType.getText().toString().trim() ;
                String brand = inventoryBrand.getText().toString().trim() ;
                String amout= amountSpent.getText().toString();
                inv.setAmout(amout);
                inv.setBrand(brand);
                inv.setInventory_type(inventory_type);
                inv.setPurchaseDate(purchase_Date);

                reff = FirebaseDatabase.getInstance().getReference().child("Inventory");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            maxid =(int) dataSnapshot.getChildrenCount();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                maxid=maxid+1;
                reff.child(String.valueOf(maxid)).setValue(inv);
                Toast.makeText(getActivity(),"Data Successfully Inserted", Toast.LENGTH_SHORT).show();

            }
        });

    }
}