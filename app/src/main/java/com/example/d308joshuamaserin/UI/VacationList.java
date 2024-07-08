package com.example.d308joshuamaserin.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.d308joshuamaserin.R;
import com.example.d308joshuamaserin.database.Repository;
import com.example.d308joshuamaserin.entities.Excursion;
import com.example.d308joshuamaserin.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);

        //functionality for add vacations button
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });

        //recyclerview populates with vacation data from database
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    //makes menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    //copying recyclerview to update when adding a new vacation
    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //takes user back to previous page
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        //adds sample vacations and excursions to database from Vacation List menu option "Add Sample Data"
        if (item.getItemId() == R.id.sample) {
            repository = new Repository(getApplication());
            Vacation vacation = new Vacation(0, "Japan Trip", "HiltonInn", "07/07/24", "08/07/24");
            repository.insert(vacation);
            vacation = new Vacation(0, "Brazil Trip", "SpringHillSuites", "02/23/24", "06/08/24");
            repository.insert(vacation);
            Excursion excursion = new Excursion(0, "Tour", 1, "07/21/24");
            repository.insert(excursion);
            excursion = new Excursion(0, "Paragliding", 1, "07/13/24");
            repository.insert(excursion);
            return true;
        }

        return true;
    }
}