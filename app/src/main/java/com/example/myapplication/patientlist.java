package com.example.myapplication;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class patientlist extends AppCompatActivity {

    RecyclerView recyclerView;
    adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);

        recyclerView = findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        FirebaseRecyclerOptions<patients> options =
                new FirebaseRecyclerOptions.Builder<patients>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Patient_Questionnaire"), patients.class)
                        .build();

        myAdapter=new adapter(options);
        recyclerView.setAdapter(myAdapter);


    }
    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView= (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<patients> options =
                new FirebaseRecyclerOptions.Builder<patients>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Patient_Questionnaire").orderByChild("Patient_name").startAt(s).endAt(s+"\uf8ff"), patients.class)
                        .build();

        myAdapter=new adapter(options);
        myAdapter.startListening();
        recyclerView.setAdapter(myAdapter);


    }
}