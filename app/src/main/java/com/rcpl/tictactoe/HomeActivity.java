package com.rcpl.tictactoe;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    EditText ed1;
    RadioGroup rg1;
    String user,NEW;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    RadioButton rb1,rb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ed1 = (EditText) findViewById(R.id.edName);
        rg1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rg1.setOnCheckedChangeListener(this);
        rb1 = (RadioButton) findViewById(R.id.radioButton1);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        NEW = b.getString("NEW");
        int count = b.getInt("C");
        myRef=database.getReference(NEW+"/GAME");

        //mydel=database.getReference("Play");
        //mydel.removeValue();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
    {
        switch (checkedId)
        {
            case R.id.radioButton1: user="zero";
                                    break;
            case R.id.radioButton2: user="cross";
                                    break;
        }

    }

    public void play(View v)
    {
        String name = ed1.getText().toString();

        Toast.makeText(this, "got user", Toast.LENGTH_SHORT).show();

        myRef.child(user).setValue(name);



        myRef=database.getReference(NEW+"/Play/b11");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");
        myRef=database.getReference(NEW+"/Play/b12");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");
        myRef=database.getReference(NEW+"/Play/b13");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");
        myRef=database.getReference(NEW+"/Play/b21");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");
        myRef=database.getReference(NEW+"/Play/b22");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");
        myRef=database.getReference(NEW+"/Play/b23");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");
        myRef=database.getReference(NEW+"/Play/b31");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");
        myRef=database.getReference(NEW+"/Play/b32");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");
        myRef=database.getReference(NEW+"/Play/b33");
        myRef.child("LOCK").setValue("false");
        myRef.child("Value").setValue("");


        Intent i = new Intent(HomeActivity.this,gameActivity.class);
        i.putExtra("NEW",NEW);
        startActivity(i);
        finish();
    }


}
