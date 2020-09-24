package com.example.notrealtwitter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    //Declarations
    private EditText edtEmail, edtUsername, edtPwd;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        //Initalizations
        edtEmail = findViewById(R.id.edtTxtEmail);
        edtUsername = findViewById(R.id.edtTxtUserName);
        edtPwd = findViewById(R.id.editTxtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
    }


    public void signupisPressed(View btnView){

        Toast.makeText(this, "Signed Up Pressed",Toast.LENGTH_SHORT).show();

        try{

            //Sign Up with Parse
            ParseUser user = new ParseUser();
            user.setUsername(edtUsername.getText().toString());
            user.setPassword(edtPwd.getText().toString());
            user.setEmail(edtEmail.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){
                        ParseUser.logOut();
                        alertDisplayer("Account Created Successfully!","Please verify your email before Login",false);
                    } else {
                        ParseUser.logOut();
                        alertDisplayer("Error Account Creation Failed.","Account could not be created" + " :" + e.getMessage(),true);
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();

        }
    }


    private void alertDisplayer(String title, String message, final boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    if (!error) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                }


        });

    AlertDialog ok = builder.create();
    ok.show();
    }

}