package com.example.myapplication;

import android.widget.Adapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

//import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.RadioGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> notes;
    ArrayList<String> titles;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private final String TITLE_FILE= "titles.txt";
    private final String NOTE_FILE= "notes.txt";
    private final String EMPTY_TITLE=Character.toString((char)(2));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        notes=new ArrayList<String>();
        titles=new ArrayList<String>();




        load();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        myAdapter= new MyAdapter(this, titles, notes);

        //layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onNewButtonPress(View view){
        Intent intent= new Intent(this, Editor.class);
        intent.putExtra("position", notes.size());

        startActivityForResult(intent,1);
    }

    public void save(){
        FileOutputStream tstream=null;
        FileOutputStream nstream=null;
        try {
            tstream = openFileOutput(TITLE_FILE, MODE_PRIVATE);
            nstream = openFileOutput(NOTE_FILE, MODE_PRIVATE);

            for (int i=0; i<myAdapter.notes.size(); i++){
                nstream.write(myAdapter.notes.get(i).getBytes());
                if (myAdapter.titles.get(i).equals(""))
                    tstream.write(EMPTY_TITLE.getBytes());
                else
                    tstream.write(myAdapter.titles.get(i).getBytes());

                nstream.write("\n".getBytes());

                tstream.write("\n".getBytes());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (nstream!=null) {
                try {
                    nstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(tstream!=null) {
                try {
                    tstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void load(){
        FileInputStream tstream= null;
        FileInputStream nstream= null;

        try {
            tstream=openFileInput(TITLE_FILE);
            nstream=openFileInput(NOTE_FILE);

            //Read titles
            String line;
            InputStreamReader isr= new InputStreamReader(tstream);
            BufferedReader reader = new BufferedReader(isr);
            while ((line=reader.readLine())!=null){
                if (line.charAt(0)==(char)2)
                    titles.add("");

                else
                    titles.add(line);

            }
            //read notes
            isr= new InputStreamReader(nstream);
            reader = new BufferedReader(isr);
            while ((line=reader.readLine())!=null){

                    notes.add(line);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FILE NOT FOUND");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (nstream!=null) {
                try {
                    nstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(tstream!=null) {
                try {
                    tstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        save();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("ONACTIVITYRESULT");
        super.onActivityResult(requestCode, resultCode, data);


        // Check that it is the SecondActivity with an OK result
        if (resultCode == RESULT_OK) {
            if (requestCode == 0){

                if(!(data.getStringExtra("returnTitle").equals("")&&data.getStringExtra("returnNote").equals(""))) {
                    myAdapter.titles.set(data.getIntExtra("position", 0), data.getStringExtra("returnTitle"));
                    myAdapter.notes.set(data.getIntExtra("position", 0), data.getStringExtra("returnNote"));
                    myAdapter.notifyItemChanged(data.getIntExtra("position", 0));
                }
                else
                {
                    myAdapter.titles.remove(data.getIntExtra("position", 0));
                    myAdapter.notes.remove(data.getIntExtra("position", 0));
                    for (int i=data.getIntExtra("position", 0); i<myAdapter.titles.size(); i++)
                    myAdapter.notifyItemRemoved(data.getIntExtra("position", 0));
                }

            }
            else if(requestCode == 1){
                if(!(data.getStringExtra("returnTitle").equals("")&&data.getStringExtra("returnNote").equals(""))) {

                    myAdapter.titles.add(data.getStringExtra("returnTitle"));
                    myAdapter.notes.add(data.getStringExtra("returnNote"));
                    myAdapter.notifyItemInserted(titles.size()-1);
                }
            }



        }
    }



}
