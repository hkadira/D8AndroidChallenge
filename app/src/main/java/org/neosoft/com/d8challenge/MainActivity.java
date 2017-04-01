package org.neosoft.com.d8challenge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {
    private Gson gson;
    private GsonBuilder builder;

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText username, password;
    private SharedPreferences mPref;
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String PREF_NAME = "sp_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();

        mPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        username = (EditText)findViewById(R.id.edTxtUName);
        password = (EditText)findViewById(R.id.edTxtPwd);

        String storedUserName = mPref.getString("USER_NAME", "");
        username.setText(storedUserName);
        String storedPwd = mPref.getString("PASSWORD", "");
        password.setText(storedPwd);

        if(isLoggedIn()){
            startActivity(new Intent(getBaseContext(),WelcomeActivity.class));
            finish();
        }

        Button saveButton = (Button)findViewById(R.id.btnLogin);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pwd = password.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)){
                    Toast.makeText(MainActivity.this, "All fields must be filled ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Login Successfull ", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = mPref.edit();

                    Person person = new Person(user,pwd);
                    String sPerson= convertPersonObjectToString(person);
                    editor.putString("USER",sPerson);

                    editor.putBoolean(IS_LOGIN, true);
                    editor.apply();
                    startActivity(new Intent(getBaseContext(),WelcomeActivity.class));
                    finish();
                }
            }
        });
    }

    private String convertPersonObjectToString(Person person){
        String objectString = gson.toJson(person);
        return objectString;
    }



    public boolean isLoggedIn(){
        return mPref.getBoolean(IS_LOGIN, false);
    }
}
