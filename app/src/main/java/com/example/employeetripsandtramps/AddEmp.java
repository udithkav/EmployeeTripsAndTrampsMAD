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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String[] items = new String[]{"Other","Human Resource Manager", "Inventory Manager","Finance Manager","Workflow Manager"};
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
                boolean checkFLname = firstAndLastNameValidation(fName,LName);
                String d_ob =dateOfBirth.getText().toString().trim();
                boolean dateValidation = validateDate(d_ob);
                String e_mail = email.getText().toString().trim();
                boolean emailValidation = isValid(e_mail);
                String n_ic = nic.getText().toString().trim();
                boolean nicValidationm = nicValidation(n_ic);
                String pass_word = password.getText().toString().trim();
                boolean passwordValidation = isValidPassword(pass_word);
                String pos_tion = position.getSelectedItem().toString();
                emp.setFirstName(fName);
                emp.setLastName(LName);
                emp.setDob(d_ob);
                emp.setEmail(e_mail);
                emp.setNic(n_ic);
                emp.setPassword(pass_word);
                emp.setPosition(pos_tion);

                if(emailValidation== true && passwordValidation==true && dateValidation==true && nicValidationm==true && checkFLname==true){
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
                    emailValidation = false;
                    passwordValidation= false;
                    dateValidation=false;
                    nicValidationm=false;
                    checkFLname=false;
                    clearControls();

                }
                else {
                    if(emailValidation== false){
                        Toast.makeText(getActivity(),"Invalid Email Please Re-enter Email",Toast.LENGTH_SHORT).show();
                    }
                    if(passwordValidation==false){
                        Toast.makeText(getActivity(),"Please a Password with 1-@-n-N and between 8 and 20 characters",Toast.LENGTH_SHORT).show();
                    }
                    if(dateValidation==false){
                        Toast.makeText(getActivity(),"Please enter a valid Date Format: dd/MM/yyyy ",Toast.LENGTH_SHORT).show();
                    }
                    if(nicValidationm==false){
                        Toast.makeText(getActivity(),"Please enter a valid national id card eg: 927173024v ",Toast.LENGTH_SHORT).show();
                    }
                    if(checkFLname==false){
                        Toast.makeText(getActivity(),"First Name or Last Name cannot be empty",Toast.LENGTH_SHORT).show();
                    }



                }



            }
        });









    }
    public static boolean firstAndLastNameValidation(String fName, String lName){
        if(fName.isEmpty()||lName.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean validateDate(String strDate)
    {
        if (strDate.trim().equals(""))
        {
            return true;
        }
        else
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            simpleDateFormat.setLenient(false);
            try
            {
                Date javaDate = simpleDateFormat.parse(strDate);
            }
            catch (ParseException e)
            {
                return false;
            }
            return true;
        }
    }
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    public static boolean nicValidation(String nic){
        String regex = "^[0-9]{9}[vVxX]$";
        return nic.matches(regex);
    }
    public static boolean isValidPassword(String password)
    {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
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