package com.example.practice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText input_username, input_password;
    Button button_login, button_register;
    public final static String EXTRA_MESSAGE = "com.example.visit.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_username = findViewById(R.id.username);
        input_password = findViewById(R.id.password);
        button_login = findViewById(R.id.login_button);
        button_register = findViewById(R.id.register_button);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textUsername = input_username.getText().toString();
                String textPassword = input_password.getText().toString();
                if (textUsername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username isn't entered", Toast.LENGTH_LONG).show();
                }  else if (textPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password isn't entered", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (textUsername.equals("admin") && textPassword.equals("admin123")) {
                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intent);
                    } else {
                        AppDatabase instance = AppDatabase.getInstance(getApplicationContext());
                        final UserDao userDao = instance.userDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                User user = userDao.login(textUsername, textPassword);
                                if (user == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Credentials are invalid", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra(EXTRA_MESSAGE, user.getId());
                                    startActivity(intent);
                                }
                            }
                        }).start();
                    }
                }
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        input_username.setText("");
        input_password.setText("");

    }
}