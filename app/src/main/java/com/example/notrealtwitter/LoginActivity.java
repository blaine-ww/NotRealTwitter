package com.example.notrealtwitter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    //Declarations
    private EditText edtLoginUsername, edtLoginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializations
        edtLoginUsername = findViewById(R.id.editTxtLoginUsername);
        edtLoginPwd = findViewById(R.id.edtTxtLoginPassword);

    }

    public void loginIsPressed(View btnView){

        ParseUser.logInInBackground(edtLoginUsername.getText().toString(), edtLoginPwd.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser !=null){
                    if (parseUser.getBoolean("emailVerified")){
                        alertDisplayer("Login Successfull", "Welcome, " + edtLoginUsername.getText() + "!", false);
                    } else {
                        ParseUser.logOut();
                        alertDisplayer("Login Fail", "Please Verify Your Email first.",true);
                    }
                } else {
                    ParseUser.logOut();
                    alertDisplayer("Login Fail", e.getMessage() + "Please re-try",true);
                }
            }
        });
    }

    private void alertDisplayer(String title, String message, final boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (!error) {
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    }


                });

        AlertDialog ok = builder.create();
        ok.show();
    }
}