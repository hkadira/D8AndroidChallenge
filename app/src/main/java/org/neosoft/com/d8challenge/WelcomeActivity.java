package org.neosoft.com.d8challenge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class WelcomeActivity extends AppCompatActivity {
    TextView name;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private Gson gson;
    private GsonBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sharedpreferences = getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        gson = new Gson();

        String strUser= sharedpreferences.getString("USER","");
        Person p = convertStringToPersonObject(strUser);

        name = (TextView) findViewById(R.id.txtName);
        name.setText(p.getUsername());

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                finish();
                startActivity(new Intent(getBaseContext(),MainActivity.class));
            }
        });
    }

    private Person convertStringToPersonObject(String personString){
        Person person = gson.fromJson(personString, Person.class);
        return person;
    }
}