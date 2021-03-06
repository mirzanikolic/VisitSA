package com.example.practice;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        TextView welcome = (TextView) view.findViewById(R.id.welcome_username);
        TextView welcome_username = (TextView) view.findViewById(R.id.username);
        CardView cardRestaurant = (CardView) view.findViewById(R.id.cardView_restaurants);
        CardView cardAttractions = (CardView) view.findViewById(R.id.cardView_attractions);
        CardView cardMuseums = (CardView) view.findViewById(R.id.cardView_museums);
        CardView cardNightlife = (CardView) view.findViewById(R.id.cardView_nightlife);
        CardView cardTours = (CardView) view.findViewById(R.id.cardView_tours);

        Bundle bundle = this.getArguments();
        if(bundle !=null){
            long id=bundle.getLong("user_id");
            User user = AppDatabase.getInstance(getContext()).userDao().getUserId(id);
            welcome_username.setText(user.getUsername().toString());
        }

        cardRestaurant.setOnClickListener(this);
        cardAttractions.setOnClickListener(this);
        cardMuseums.setOnClickListener(this);
        cardNightlife.setOnClickListener(this);
        cardTours.setOnClickListener(this);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.cardView_restaurants:
                switchFragment(new RestaurantsFragment());
                break;
            case R.id.cardView_attractions:
                switchFragment(new AttractionsFragment());
                break;
            case R.id.cardView_museums:
                switchFragment(new MuseumsFragment());
                break;
            case R.id.cardView_nightlife:
                switchFragment(new NightlifeFragment());
                break;
            case R.id.cardView_tours:
                switchFragment(new ToursFragment());
                break;
        }
    }
    public void switchFragment(Fragment fragment){
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction().replace(R.id.FragmentContainer,fragment).setReorderingAllowed(true).addToBackStack("name").commit();
    }
}