package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public ArrayList<String> titles;
    public ArrayList<String> notes;
    Context context;
    Intent intent;
    int current;


    public MyAdapter(Context c, ArrayList<String> titleList,ArrayList<String> notesList ){
        context=c;
        titles=titleList;
        notes=notesList;
        current=-1;
        intent= new Intent(context, Editor.class);
    }
    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent,
                                                     int viewType) {
        LayoutInflater i= LayoutInflater.from(context);
        View v= i.inflate(R.layout.row, parent, false);


        return new MyViewHolder(v);

    }





    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (titles.get(position).equals("")){
            if (notes.get(position).length()<11)
                holder.t1.setText(notes.get(position));
            else
                holder.t1.setText(notes.get(position).substring(0,10));
            holder.t2.setText("");

        }
        else {
            if (titles.get(position).length()>15)
                holder.t1.setText(titles.get(position).substring(0,15));
            else
                holder.t1.setText(titles.get(position));

            int noteLength=15;
            if (notes.get(position).length()<16)
                noteLength=notes.get(position).length();
            String notesSub=notes.get(position).substring(0,noteLength);
            for (int a=0; a<noteLength; a++){
                if (notesSub.charAt(a)=='\n') {
                    notesSub = notesSub.substring(0, a);
                    break;
                }
            }

           holder.t2.setText(notesSub);

        }



       holder.b.setOnClickListener( new View.OnClickListener(){
           @Override
           public void onClick(View v){
               titles.remove(position);
               notes.remove(position);
               notifyItemRemoved(position);

           }
       });

       holder.base.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){

               intent.putExtra("title", titles.get(position));
               intent.putExtra("note", notes.get(position));
               intent.putExtra("position", position);
               current=position;

               ((MainActivity)(context)).startActivityForResult(intent,0);


           }
       });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return titles.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder {

        TextView t1;
        TextView t2;
        ConstraintLayout base;
        Button b;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.title);
            t2 = itemView.findViewById(R.id.text);
            base=itemView.findViewById(R.id.row_root);
            b=itemView.findViewById(R.id.delete_button);
        }
    }



}