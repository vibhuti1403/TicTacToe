package com.rcpl.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.concurrent.locks.Lock;

public class gameActivity extends AppCompatActivity {
    TextView tv,tv2;
    int Turn;
    String Player,NEW;
   String Locked[] = new String[9];
    int i,btn;
    Map<String,String> map;
    Set<String> keys;
    Button b11,b12,b13,b21,b22,b23,b31,b32,b33;
   FirebaseDatabase database = FirebaseDatabase.getInstance();
   DatabaseReference myRef,myt;
    DatabaseReference mychance;
    String values[]=new String[9];
    String btnVal[]= new String [9];
    int valInt[]= {2,2,2,2,2,2,2,2,2};
    String opt="zero";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        for(i=0;i<9;i++)
            Locked[i]="false";

        b11 = (Button) findViewById(R.id.TopL);
        b12 = (Button) findViewById(R.id.TopM);
        b13 = (Button) findViewById(R.id.TopR);

        b21 = (Button) findViewById(R.id.MidL);
        b22 = (Button) findViewById(R.id.MidM);
        b23 = (Button) findViewById(R.id.MidR);

        b31 = (Button) findViewById(R.id.BotL);
        b32 = (Button) findViewById(R.id.BotM);
        b33 = (Button) findViewById(R.id.BotR);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        NEW = b.getString("NEW");
        myRef=database.getReference(NEW+"/Play");
        myt=database.getReference(NEW+"/TURN");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                btn=0;
            //    String val;
                Map<String,Map<String,String>> map1 =(Map) dataSnapshot.getValue();
                map1 = new TreeMap<String, Map<String, String>>(map1);
                keys = map1.keySet();
                Iterator<String> itr = keys.iterator();

                while(itr.hasNext())
                {
                    String roll = itr.next();
                    Map<String,String> childMap = map1.get(roll);
                    String val = childMap.get("Value");
                    btnVal[btn]=val;
                    btn++;

                    b11.setText(btnVal[0]); b12.setText(btnVal[1]); b13.setText(btnVal[2]);
                    b21.setText(btnVal[3]); b22.setText(btnVal[4]); b23.setText(btnVal[5]);
                    b31.setText(btnVal[6]); b32.setText(btnVal[7]); b33.setText(btnVal[8]);
                    //Toast.makeText(

                }
                decide(valInt);
                // keys.clear();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layout1);
        cl.setBackgroundColor(Color.rgb(255,255,0));

        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);



        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myPlay=database1.getReference(NEW+"/GAME");
        myt.setValue(1);


        myPlay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map = (Map)dataSnapshot.getValue();
               // map = new TreeMap<String,String>(map);//to sort values
                Set<String> keys = map.keySet();
                Iterator<String> itr = keys.iterator();
                Player = map.get("zero").toString();
                        //Toast.makeText(gameActivity.this, "ROLL: "+Player, Toast.LENGTH_SHORT).show();
                //tv.setText("TURN: " + Player);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tv.setText("NULL");

            }

        });



        myt.addValueEventListener(new ValueEventListener() {
            @Override0
            public void onDataChange(DataSnapshot dataSnapshot) {
                Turn =dataSnapshot.getValue(Integer.class);
                if(Turn == 1)
                    tv.setText("PLAYER WITH CHARACTER  ZERO (O) ");
                if(Turn == 2)
                    tv.setText("PLAYER WITH CHARACTER  CROSS (X) ");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }





    public void submit(View v)
        {   //String opt="zero";

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    i=0;
                    Map<String,Map<String,String>> map1 =(Map) dataSnapshot.getValue();
                    map1 = new TreeMap<String, Map<String, String>>(map1);
                    keys = map1.keySet();
                    Iterator<String> itr = keys.iterator();

                    while(itr.hasNext())
                    {
                        String roll = itr.next();
                        Map<String,String> childMap = map1.get(roll);
                        String lock = childMap.get("LOCK");
                        Locked[i]=lock;
                       //Toast.makeText(gameActivity.this, "i="+i+"LOCK"+Locked[i], Toast.LENGTH_SHORT).show();
                        i++;
                       // Toast.makeText(gameActivity.this, "i="+i, Toast.LENGTH_SHORT).show();

                    }

                   // keys.clear();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            if(Turn ==1)
            {
                myt.setValue(2);
                opt = "zero";
            }

            if(Turn ==2) {
                myt.setValue(1);
                opt = "cross";
                         }

            switch(v.getId())
            {
                case R.id.TopL:

                                if(opt.equals("zero"))
                                {   if(Locked[0].equals("false"))
                                    {
                                        b11.setText("O");
                                    mychance=database. getReference(NEW+"/Play/b11") ;
                                    mychance.child("LOCK").setValue("true");
                                    mychance.child("Value").setValue("O");
                                        winner();
                                    }
                                else
                                    Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                                }

                                if(opt.equals("cross"))
                                {   if(Locked[0].equals("false"))
                                    {
                                    b11.setText("CROSS");
                                    mychance=database. getReference(NEW+"/Play/b11") ;
                                    mychance.child("LOCK").setValue("true");
                                    mychance.child("Value").setValue("X");
                                        winner();
                                    }

                                else
                                    Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                                }

                                break;

                case R.id.TopM:
                    if(opt.equals("zero"))
                    {   if(Locked[1].equals("false"))
                    {
                        b12.setText("O");
                        mychance=database. getReference(NEW+"/Play/b12") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("O");
                        winner();
                    }
                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                    }

                    if(opt.equals("cross"))
                    {   if(Locked[1].equals("false"))
                    {
                        b12.setText("X");
                        mychance=database. getReference(NEW+"/Play/b12") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("X");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.TopR:
                    if(opt.equals("zero"))
                    {   if(Locked[2].equals("false"))
                    {
                        b13.setText("O");
                        mychance=database. getReference(NEW+"/Play/b13") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("O");
                        winner();
                    }
                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                    }

                    if(opt.equals("cross"))
                    {   if(Locked[2].equals("false"))
                    {
                        b13.setText("X");
                        mychance=database. getReference(NEW+"/Play/b13") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("X");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.MidL:
                    if(opt.equals("zero"))
                    {   if(Locked[3].equals("false"))
                    {
                        b21.setText("O");
                        mychance=database. getReference(NEW+"/Play/b21") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("O");
                        winner();
                    }
                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                    }

                    if(opt.equals("cross"))
                    {   if(Locked[3].equals("false"))
                    {
                        b21.setText("X");
                        mychance=database. getReference(NEW+"/Play/b21") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("X");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();
                    }

                    break;


                case R.id.MidM:
                    if(opt.equals("zero"))
                    {   if(Locked[4].equals("false"))
                    {
                        b22.setText("O");
                        mychance=database. getReference(NEW+"/Play/b22") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("O");
                        winner();
                    }
                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                    }

                    if(opt.equals("cross"))
                    {   if(Locked[4].equals("false"))
                    {
                        b22.setText("X");
                        mychance=database. getReference(NEW+"/Play/b22") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("X");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.MidR:
                    if(opt.equals("zero"))
                    {   if(Locked[5].equals("false"))
                    {
                        b23.setText("O");
                        mychance=database. getReference(NEW+"/Play/b23") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("O");
                        winner();
                    }
                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                    }

                    if(opt.equals("cross"))
                    {   if(Locked[5].equals("false"))
                    {
                        b23.setText("X");
                        mychance=database. getReference(NEW+"/Play/b23") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("X");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();
                    }

                    break;


                case R.id.BotL:
                    if(opt.equals("zero"))
                    {   if(Locked[6].equals("false"))
                    {
                        b31.setText("O");
                        mychance=database. getReference(NEW+"/Play/b31") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("O");
                        winner();
                    }
                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                    }

                    if(opt.equals("cross"))
                    {   if(Locked[6].equals("false"))
                    {
                        b31.setText("X");
                        mychance=database. getReference(NEW+"/Play/b31") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("X");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();
                    }

                    break;


                case R.id.BotM:
                    if(opt.equals("zero"))
                    {   if(Locked[7].equals("false")) {
                        b32.setText("O");
                        mychance = database.getReference(NEW+"/Play/b32");
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("O");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                    }

                    if(opt.equals("cross"))
                    {   if(Locked[7].equals("false"))
                    {
                        b32.setText("X");
                        mychance=database. getReference(NEW+"/Play/b32") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("X");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.BotR:
                    if(opt.equals("zero"))
                    {   if(Locked[8].equals("false"))
                    {
                        b33.setText("O");
                        mychance=database. getReference(NEW+"/Play/b33") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("O");
                        winner();
                    }
                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();

                    }

                    if(opt.equals("cross"))
                    {   if(Locked[8].equals("false"))
                    {
                        b33.setText("X");
                        mychance=database. getReference(NEW+"/Play/b33") ;
                        mychance.child("LOCK").setValue("true");
                        mychance.child("Value").setValue("X");
                        winner();
                    }

                    else
                        Toast.makeText(this, "Cannot change value", Toast.LENGTH_SHORT).show();
                    }

                    break;

            }

        }



        void winner()
        {//String won="";

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    i=0;
                    Map<String,Map<String,String>> map1 =(Map) dataSnapshot.getValue();
                    map1 = new TreeMap<String, Map<String, String>>(map1);
                    keys = map1.keySet();
                    Iterator<String> itr = keys.iterator();

                    while(itr.hasNext())
                    {
                        String roll = itr.next();
                        Map<String,String> childMap = map1.get(roll);
                        String val = childMap.get("Value");
                        values[i]=val;
                        //Toast.makeText(gameActivity.this, "i="+i+"LOCK"+Locked[i], Toast.LENGTH_SHORT).show();
                        i++;
                        //Toast.makeText(gameActivity.this, "i="+i, Toast.LENGTH_SHORT).show();

                    }
                    for(int j=0;j<9;j++)
                    {
                        if(values[j].equals("O"))
                            valInt[j]=0;
                        else
                        if(values[j].equals("X"))
                            valInt[j]=1;
                    }

                    decide(valInt);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


        void decide(int valInt[])
        {
            int k =0,c=0;
            if((valInt[0]==0) && (valInt[1]==0) && (valInt[2]==0))
            {

                b11.setBackgroundColor(Color.rgb(255,20,147));
                b12.setBackgroundColor(Color.rgb(255,20,147));
                b13.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("zero");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");

                tv.setText("");

            }
            else
            if((valInt[3]==0) && (valInt[4]==0) && (valInt[5]==0))
            {
                b21.setBackgroundColor(Color.rgb(255,20,147));
                b22.setBackgroundColor(Color.rgb(255,20,147));
                b23.setBackgroundColor(Color.rgb(255,20,147));

                over();
                Player = map.get("zero");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");
            }
            else
            if((valInt[6]==0) && (valInt[7]==0) && (valInt[8]==0))
            {
                b31.setBackgroundColor(Color.rgb(255,20,147));
                b32.setBackgroundColor(Color.rgb(255,20,147));
                b33.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("zero");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");
            }
            else
            if((valInt[0]==0) && (valInt[3]==0) && (valInt[6]==0))
            {
                b11.setBackgroundColor(Color.rgb(255,20,147));
                b21.setBackgroundColor(Color.rgb(255,20,147));
                b31.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("zero");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[1]==0) && (valInt[4]==0) && (valInt[7]==0))
            {
                b12.setBackgroundColor(Color.rgb(255,20,147));
                b22.setBackgroundColor(Color.rgb(255,20,147));
                b32.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("zero");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");
            }
            else
            if((valInt[2]==0) && (valInt[5]==0) && (valInt[8]==0))
            {
                b13.setBackgroundColor(Color.rgb(255,20,147));
                b23.setBackgroundColor(Color.rgb(255,20,147));
                b33.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("zero");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[0]==0) && (valInt[4]==0) && (valInt[8]==0))
            {
                b11.setBackgroundColor(Color.rgb(255,20,147));
                b22.setBackgroundColor(Color.rgb(255,20,147));
                b33.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("zero");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[2]==0) && (valInt[4]==0) && (valInt[6]==0))
            {
                b13.setBackgroundColor(Color.rgb(255,20,147));
                b22.setBackgroundColor(Color.rgb(255,20,147));
                b31.setBackgroundColor(Color.rgb(255,20,147));

                over();
                Player = map.get("zero");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }

            else
            if((valInt[0]==1) && (valInt[1]==1) && (valInt[2]==1))
            {
                b11.setBackgroundColor(Color.rgb(255,20,147));
                b12.setBackgroundColor(Color.rgb(255,20,147));
                b13.setBackgroundColor(Color.rgb(255,20,147));

                over();
                Player = map.get("cross");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[3]==1) && (valInt[4]==1) && (valInt[5]==1))
            {
                b21.setBackgroundColor(Color.rgb(255,20,147));
                b22.setBackgroundColor(Color.rgb(255,20,147));
                b23.setBackgroundColor(Color.rgb(255,20,147));

                over();
                Player = map.get("cross");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[6]==1) && (valInt[7]==1) && (valInt[8]==1))
            {
                b31.setBackgroundColor(Color.rgb(255,20,147));
                b32.setBackgroundColor(Color.rgb(255,20,147));
                b33.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("cross");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[0]==1) && (valInt[3]==1) && (valInt[6]==1))
            {
                b11.setBackgroundColor(Color.rgb(255,20,147));
                b21.setBackgroundColor(Color.rgb(255,20,147));
                b31.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("cross");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[1]==1) && (valInt[4]==1) && (valInt[7]==1))
            {
                b12.setBackgroundColor(Color.rgb(255,20,147));
                b22.setBackgroundColor(Color.rgb(255,20,147));
                b32.setBackgroundColor(Color.rgb(255,20,147));

                over();
                Player = map.get("cross");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[2]==1) && (valInt[5]==1) && (valInt[8]==1))
            {

                b13.setBackgroundColor(Color.rgb(255,20,147));
                b23.setBackgroundColor(Color.rgb(255,20,147));
                b33.setBackgroundColor(Color.rgb(255,20,147));
                over();
                Player = map.get("cross");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[0]==1) && (valInt[4]==1) && (valInt[8]==1))
            {
                b11.setBackgroundColor(Color.rgb(255,20,147));
                b22.setBackgroundColor(Color.rgb(255,20,147));
                b33.setBackgroundColor(Color.rgb(255,20,147));

                over();
                Player = map.get("cross");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
            if((valInt[2]==1) && (valInt[4]==1) && (valInt[6]==1))
            {
                b13.setBackgroundColor(Color.rgb(255,20,147));
                b22.setBackgroundColor(Color.rgb(255,20,147));
                b31.setBackgroundColor(Color.rgb(255,20,147));

                over();
                Player = map.get("cross");
                tv2.setBackgroundColor(Color.RED);
                tv2.setText(" "+Player.toUpperCase()+" WON");
                tv.setText("");

            }
            else
                {
                for (k = 0; k < 9; k++)
                    if (Locked[k].equals("true"))
                c++;

                if (c == 9) {
                    over();
                    tv2.setBackgroundColor(Color.RED);
                    tv2.setText("MATCH DRAW");
                    tv.setText("");
                    c = 0;
                }
            }

        }


        void over()
        {
            b11.setEnabled(false);
            b12.setEnabled(false);
            b13.setEnabled(false);

            b21.setEnabled(false);
            b22.setEnabled(false);
            b23.setEnabled(false);

            b31.setEnabled(false);
            b32.setEnabled(false);
            b33.setEnabled(false);


        }


}
