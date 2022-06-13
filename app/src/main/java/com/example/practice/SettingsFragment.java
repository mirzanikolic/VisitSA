package com.example.practice;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView hello_text;
    private Button pwbutton;
    private Button emailbutton;
    private Button logoutbutton;
    private EditText enter1;
    private EditText enternew1;
    private EditText enternew2;
    private EditText enter2;
    private EditText enternew3;



    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view=inflater.inflate(R.layout.fragment_settings, container, false);
        Button logout = (Button) view.findViewById(R.id.button_logout);
        Bundle bundle = this.getArguments();
        long id=bundle.getLong("user_id");

        hello_text = view.findViewById(R.id.hello);
        User user = AppDatabase.getInstance(getContext()).userDao().getUserId(id);
        hello_text.setText("You are logged in as: "+user.getUsername().toString());

        pwbutton = view.findViewById(R.id.change_pwbtn);
        emailbutton = view.findViewById(R.id.change_emailbtn);
        logoutbutton = view.findViewById(R.id.button_logout);
        enter1 = view.findViewById(R.id.enter1);
        enternew1= view.findViewById(R.id.enternew1);
        enternew2= view.findViewById(R.id.enternew2);
        enter2= view.findViewById(R.id.enter2);
        enternew3= view.findViewById(R.id.enternewemail);

        pwbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = enter1.getText().toString();
                if(enternew1.getText().toString().equals(enternew2.getText().toString()) && password.equals(user.getPassword()) && enternew1.getText().toString().length()>2 ){
                    String newPassword = enternew1.getText().toString();
                    user.setPassword(newPassword);
                    AppDatabase.getInstance(getContext()).userDao().updateUser(user);
                    Toast.makeText(getContext(),"Your password has been updated",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"Required fields not inserted properly",Toast.LENGTH_LONG).show();

                }
            }
        });

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Password = enter2.getText().toString();
                String newEmail = enternew3.getText().toString();
                if(Password.equals(user.getPassword()) && isEmailValid(newEmail)){
                    user.setEmail(newEmail);
                    AppDatabase.getInstance(getContext()).userDao().updateUser(user);
                    Toast.makeText(getContext(),"Your E-mail address has been updated",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"Required fields not inserted properly",Toast.LENGTH_LONG).show();
                }
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}