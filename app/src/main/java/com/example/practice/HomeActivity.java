package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.practice.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    public final static String EXTRA_MESSAGE = "com.example.visit.MESSAGE";


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        binding.bottomNavBar.setSelectedItemId(R.id.home);
        setContentView(binding.getRoot());


        AppDatabase instance = AppDatabase.getInstance(getApplicationContext());
        final UserDao userDao = instance.userDao();

        Bundle extras = getIntent().getExtras();

        long id = extras.getLong(MainActivity.EXTRA_MESSAGE);
        User user = instance.userDao().getUserId(id);
        FragmentChange(new HomeFragment(),id);

        binding.bottomNavBar.setOnItemSelectedListener(item ->{
            switch(item.getItemId()){
                case R.id.settings:
                    FragmentChange(new SettingsFragment(),id);
                    break;

                case R.id.home:
                    FragmentChange(new HomeFragment(),id);
                    break;

                case R.id.notifications:
                    FragmentChange(new NotificationsFragment(),id);
                    break;
            }
            return true;
        });


    }


    public void FragmentChange(Fragment fragment,long id){
        FragmentManager fm = getSupportFragmentManager();
        Bundle id_bundle = new Bundle();
        id_bundle.putLong("user_id",id);
        fragment.setArguments(id_bundle);
        fm.beginTransaction().replace(R.id.FragmentContainer,fragment).setReorderingAllowed(true).addToBackStack("name").commit();
    }
}