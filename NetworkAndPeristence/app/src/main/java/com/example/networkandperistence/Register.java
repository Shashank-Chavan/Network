package com.example.networkandperistence;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
    TextView user;
    TextView pass;
    Button Reg;
    Button list1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        DataBase db = new DataBase(view.getContext());
        user = view.findViewById(R.id.user_name);
        pass = view.findViewById(R.id.password);
        Reg = view.findViewById(R.id.register_button);
        String user_name = user.getText().toString();
        String password = pass.getText().toString();
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params p = new params(user_name,password);
                p.setUsername(user_name);
                p.setPassword(password);
                Boolean insert = db.Register(p);
                if(insert==true){
                    Toast.makeText(view.getContext(),"registered successfully",Toast.LENGTH_SHORT);
                    getFragmentManager().beginTransaction().replace(R.id.Container,new LoginPage()).commit();
                }
                else{
                    Toast.makeText(view.getContext(),"registration failed",Toast.LENGTH_SHORT);
                }
            }
        });
        return view;
    }
}