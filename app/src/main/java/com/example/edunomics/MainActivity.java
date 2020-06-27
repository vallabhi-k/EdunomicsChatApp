package com.example.edunomics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SearchView etSearch;
    RecyclerView resultList;
    DatabaseReference myRef;
    ArrayList<Users> list;
    AdapterClass adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch=findViewById(R.id.etSearch);
        resultList=findViewById(R.id.resultList);

        myRef= FirebaseDatabase.getInstance().getReference("user");
        resultList.setHasFixedSize(true);
        resultList.setLayoutManager(new LinearLayoutManager(this));
        
        myRef = FirebaseDatabase.getInstance().getReference().child("user");



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(myRef!=null)
        {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<Users>();
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        Users s = dataSnapshot1.getValue(Users.class);
                        list.add(s);
                    }
                    adapter = new AdapterClass(MainActivity.this,list);
                    resultList.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
                }
            });

            if(etSearch!=null)
            {
                etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        search(s);
                        return true;
                    }
                });
            }

        }
    }

    private void search(String s)
    {
        ArrayList<Users> mylist = new ArrayList<>();
        for(Users obj : list)
        {
            if((obj.getName().toLowerCase().contains(s.toLowerCase())) || ((obj.getStatus().toLowerCase().contains(s.toLowerCase())) ) )
            mylist.add(obj);

        }
        AdapterClass adapterClass = new AdapterClass(MainActivity.this,mylist);
        resultList.setAdapter(adapterClass);


    }
}
