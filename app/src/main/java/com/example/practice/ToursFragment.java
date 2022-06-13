package com.example.practice;

import static android.graphics.Color.RED;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToursFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToursFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String CHANNEL_ID="My channel";
    public static final int NOT_ID=1;

    public ToursFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tours.
     */
    // TODO: Rename and change types and number of parameters
    public static ToursFragment newInstance(String param1, String param2) {
        ToursFragment fragment = new ToursFragment();
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
        View view = inflater.inflate(R.layout.fragment_tours, container, false);
        Button button_reg = view.findViewById(R.id.button_tour1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel(CHANNEL_ID, "My custom channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getActivity().getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);
        }


        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_reg.setEnabled(false);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(getActivity(),CHANNEL_ID );
                builder.setContentTitle("Registered")
                        .setContentText("You have successfully registered for our tour!")
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setColor(RED);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
                managerCompat.notify(NOT_ID, builder.build());
            }
        });
        return view;
    }

}
