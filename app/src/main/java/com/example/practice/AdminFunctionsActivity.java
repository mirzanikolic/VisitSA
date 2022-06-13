package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminFunctionsActivity extends AppCompatActivity {
    EditText admin_username, admin_email, admin_password;
    Button update, delete;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_functions);

        admin_username = findViewById(R.id.admin_username);
        admin_email = findViewById(R.id.admin_email);
        admin_password = findViewById(R.id.admin_password);
        update = findViewById(R.id.button_update);
        delete = findViewById(R.id.button_delete);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            Intent intent = new Intent(AdminFunctionsActivity.this, AdminActivity.class);
            long id = extras.getLong(AdminActivity.EXTRA_MESSAGE);
            User user = AppDatabase.getInstance(this).userDao().getUserId(id);
            admin_username.setText(user.getUsername());
            admin_email.setText(user.getEmail());
            admin_password.setText(user.getPassword());
            update.setOnClickListener(view -> {
                user.setUsername(admin_username.getText().toString());
                user.setEmail(admin_email.getText().toString());
                user.setPassword(admin_password.getText().toString());
                AppDatabase.getInstance(getApplicationContext()).userDao().updateUser(user);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AdminFunctionsActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                                AdminFunctionsActivity.this.startActivity(intent);
                            }
                        });
                    }
                }).start();
            });
            delete.setOnClickListener(view -> {
                AppDatabase.getInstance(getApplicationContext()).userDao().delete(user);
                Toast.makeText(AdminFunctionsActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            });
        }
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
}