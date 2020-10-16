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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateEmployeeDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateEmployeeDetails extends Fragment {
    EditText f_Name, l_Name, d_O_B, e_M_A_I_L, p_osition, n_i_c, p_a_s_s_wrd;
    String id;
    EditText search_employee;
    Button searchButton, update;
    DatabaseReference reff;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateEmployeeDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateEmployeeDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateEmployeeDetails newInstance(String param1, String param2) {
        UpdateEmployeeDetails fragment = new UpdateEmployeeDetails();
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
        return inflater.inflate(R.layout.fragment_update_employee_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        f_Name = (EditText) getView().findViewById(R.id.FirstName);
        l_Name = (EditText) getView().findViewById(R.id.lastName);
        d_O_B = (EditText) getView().findViewById(R.id.dateofbirth);
        e_M_A_I_L = (EditText) getView().findViewById(R.id.email);
        p_osition = (EditText) getView().findViewById(R.id.selectPosition);
        n_i_c = (EditText) getView().findViewById(R.id.nationalID);
        p_a_s_s_wrd = (EditText) getView().findViewById(R.id.password);
        search_employee = (EditText) getView().findViewById(R.id.search_Employee_NumberUpdate);
        searchButton = (Button) getView().findViewById(R.id.searchButtonUpdate);
        update = (Button) getView().findViewById(R.id.UpdateButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("Employee").child(search_employee.getText().toString().trim());
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Employee e1 = new Employee();
                            e1.setEmpNumber(search_employee.getText().toString());
                            e1.setPosition(snapshot.child("position").getValue().toString());
                            e1.setFirstName(snapshot.child("firstName").getValue().toString());
                            e1.setLastName(snapshot.child("lastName").getValue().toString());
                            e1.setEmail(snapshot.child("email").getValue().toString());
                            e1.setNic(snapshot.child("nic").getValue().toString());
                            e1.setPassword(snapshot.child("password").getValue().toString());
                            e1.setDob(snapshot.child("dob").getValue().toString());


                            f_Name.setText(e1.getFirstName());
                            l_Name.setText(e1.getLastName());
                            d_O_B.setText(e1.getDob());
                            e_M_A_I_L.setText(e1.getEmail());
                            p_osition.setText(e1.getPosition());
                            n_i_c.setText(e1.getNic());
                            p_a_s_s_wrd.setText(e1.getPassword());

                        } else {
                            f_Name.setText("DOES NOT EXIST");
                            l_Name.setText("DOES NOT EXIST");
                            d_O_B.setText("DOES NOT EXIST");
                            e_M_A_I_L.setText("DOES NOT EXIST");
                            p_osition.setText("DOES NOT EXIST");
                            n_i_c.setText("DOES NOT EXIST");
                            p_a_s_s_wrd.setText("DOES NOT EXIST");
                            Toast.makeText(getActivity(), "The Employee Number You Entered Does Not Exist!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Employee emp = new Employee();
                        String fName = f_Name.getText().toString().trim();
                        String LName = l_Name.getText().toString().trim();
                        String d_ob = d_O_B.getText().toString().trim();
                        String e_mail = e_M_A_I_L.getText().toString().trim();
                        String n_ic = n_i_c.getText().toString().trim();
                        String pass_word = p_a_s_s_wrd.getText().toString().trim();
                        String pos_tion = p_osition.getText().toString().trim();
                        boolean passwordValidation = isValidPassword(pass_word);
                        boolean nicValidationm = nicValidation(n_ic);
                        boolean emailValidation = isValid(e_mail);
                        boolean dateValidation = validateDate(d_ob);
                        boolean checkFLname = firstAndLastNameValidation(fName,LName);
                        emp.setFirstName(fName);
                        emp.setLastName(LName);
                        emp.setDob(d_ob);
                        emp.setEmail(e_mail);
                        emp.setNic(n_ic);
                        emp.setPassword(pass_word);
                        emp.setPosition(pos_tion);
                        emp.setEmpNumber(search_employee.getText().toString());

                        if(emailValidation== true && passwordValidation==true && dateValidation==true && nicValidationm==true && checkFLname==true){
                            reff = FirebaseDatabase.getInstance().getReference().child("Employee");
                            HashMap hashMap = new HashMap();
                            hashMap.put("dob",emp.getDob());
                            hashMap.put("email",emp.getEmail());
                            hashMap.put("firstName",emp.getFirstName());
                            hashMap.put("lastName",emp.getLastName());
                            hashMap.put("nic",emp.getNic());
                            hashMap.put("password",emp.getPassword());
                            hashMap.put("position",emp.getPosition());
                            reff.child(String.valueOf(emp.getEmpNumber())).updateChildren(hashMap);
                            Toast.makeText(getActivity(), "Data Succesfully Updated", Toast.LENGTH_SHORT).show();
                            emailValidation = false;
                            passwordValidation= false;
                            dateValidation=false;
                            nicValidationm=false;
                            checkFLname=false;
                        }
                        else{
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


}