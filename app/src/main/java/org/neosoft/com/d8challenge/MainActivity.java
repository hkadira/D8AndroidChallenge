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
    public static final String USER_NAME = "user";
    public static final String PASSWORD = "password";
    public static final String PREF_NAME = "my_pref";
    Person person;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();
        person=new Person();

        mPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = mPref.edit();

        username = (EditText)findViewById(R.id.edTxtUName);
        password = (EditText)findViewById(R.id.edTxtPwd);

        username.setText("");
        password.setText("");

        /*String storedUserName = mPref.getString("USER_NAME", "");
        username.setText(storedUserName);
        String storedPwd = mPref.getString("PASSWORD", "");
        password.setText(storedPwd);*/

        Button saveButton = (Button)findViewById(R.id.btnLogin);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pwd = password.getText().toString();

                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(MainActivity.this, "User name can't be empty ", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(MainActivity.this, "Password can't be empty ", Toast.LENGTH_LONG).show();
                }else{
                    if(isLoggedIn(user,pwd)) {
                        Toast.makeText(MainActivity.this, "Login Successfull ", Toast.LENGTH_LONG).show();

                        person = new Person(user, pwd);
                        String sPerson = convertPersonObjectToString(person);
                        editor.putString("USER", sPerson);

                        editor.putBoolean(IS_LOGIN, true);
                        editor.apply();
                        startActivity(new Intent(getBaseContext(), WelcomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid credentials.Try again. ", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    private String convertPersonObjectToString(Person person){
        String objectString = gson.toJson(person);
        return objectString;
    }

    public boolean isLoggedIn(String user, String pwd){
        if (person.getUsername().equals(user) && person.getPassword().equals(pwd)){
            //Toast.makeText(MainActivity.this, "Match found", Toast.LENGTH_LONG).show();
            editor.putString(USER_NAME, user);
            editor.putString(PASSWORD, pwd);
            return true;
        }
        return false;
    }
}
