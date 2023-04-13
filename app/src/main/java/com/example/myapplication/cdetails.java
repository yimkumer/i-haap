package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class cdetails extends AppCompatActivity {

    RecyclerView recyclerView;
    cadapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdetails);
        recyclerView = findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        FirebaseRecyclerOptions<cpatients> options =
                new FirebaseRecyclerOptions.Builder<cpatients>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Registered_Patients"), cpatients.class)
                        .build();

        myAdapter=new cadapter(options);
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
        FirebaseRecyclerOptions<cpatients> options =
                new FirebaseRecyclerOptions.Builder<cpatients>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Registered_Patients").orderByChild("Patient_name").startAt(s).endAt(s+"\uf8ff"), cpatients.class)
                        .build();

        myAdapter=new cadapter(options);
        myAdapter.startListening();
        recyclerView.setAdapter(myAdapter);



    }
}