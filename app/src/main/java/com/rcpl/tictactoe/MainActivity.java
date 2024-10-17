package com.rcpl.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
        EditText ed1;
        TextView tv1;
        Button b1,b2;
        int count;
   String NEW;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference UserCount=database.getReference("COUNT");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = (EditText) findViewById(R.id.etNumber);
        tv1 = (TextView) findViewById(R.id.tvDisplay);
        b1 = (Button) findViewById(R.id.btnSubmit);
        b2 = (Button) findViewById(R.id.btnOK);
        ed1.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);

    }

    public void create(View v)
    {
        UserCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count =dataSnapshot.getValue(Integer.class);
                NEW="1403"+count;
                count++;
                UserCount.setValue(count);
                tv1.setText("YOUR CODE IS: "+NEW);
                b2.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void join(View v)
    {
        ed1.setVisibility(View.VISIBLE);
        b1.setVisibility(View.VISIBLE);

    }

    public void submit(View v)
    {

          String getUser = ed1.getText().toString();
        Intent i = new Intent(MainActivity.this,HomeActivity.class);
        i.putExtra("NEW",getUser);
        startActivity(i);
        finish();


    }

    public  void okClick(View v)
    {
        tv1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        Intent  i = new Intent(MainActivity.this,HomeActivity.class);
        i.putExtra("NEW",NEW);
        startActivity(i);
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ed1.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        tv1.setVisibility(View.INVISIBLE);

    }
}
