package com.example.practice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private ListView listView;
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listView = findViewById(R.id.item_list_container);
        setUpAdapter();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id = adapterView.getItemIdAtPosition(i);
                Intent intent = new Intent(AdminActivity.this, AdminFunctionsActivity.class);
                intent.putExtra(EXTRA_MESSAGE, id);
                startActivity(intent);
            }
        });
    }

    private void setUpAdapter(){
        List<User> users = AppDatabase.getInstance(getApplicationContext()).userDao().selectAll();
        UserListAdapter adapter = new UserListAdapter(getApplicationContext(), users);
        listView.setAdapter(adapter);
    }


    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit the admin page?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}
