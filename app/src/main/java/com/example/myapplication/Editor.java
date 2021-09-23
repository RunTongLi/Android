package com.example.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;



public class Editor extends AppCompatActivity {

    EditText titleEdit;
    EditText noteEdit;
    String title;
    String note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        title="";
        note="";
        titleEdit= findViewById(R.id.editText1);
        noteEdit= findViewById(R.id.editText2);
        getData();
        setData();

    }

    private void getData(){
        if (getIntent().hasExtra("title")){
            title=getIntent().getStringExtra("title");
        }
        if(getIntent().hasExtra("note")){
            note=getIntent().getStringExtra("note");
        }


    }

    private void setData(){

        titleEdit.setText(title);
        noteEdit.setText(note);
    }

    public void saveOnExit(){
        Intent i = new Intent();

        i.putExtra("returnTitle", titleEdit.getText().toString());
        i.putExtra("returnNote", noteEdit.getText().toString());
        i.putExtra("position",getIntent().getIntExtra("position",0) );
        setResult(RESULT_OK, i);
    }

    public void onButtonClicked(View view){
        saveOnExit();
        finish();


    }

    @Override
    public void onBackPressed() {
       saveOnExit();
        super.onBackPressed();
    }


}
