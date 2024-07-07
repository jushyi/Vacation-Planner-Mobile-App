package com.example.d308joshuamaserin.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.d308joshuamaserin.R;
import com.example.d308joshuamaserin.database.Repository;
import com.example.d308joshuamaserin.entities.Excursion;

import java.util.Random;

public class ExcursionDetails extends AppCompatActivity {

    String title;
    int excursionID;
    int vacationID;
    EditText editTitle;
    Repository repository;
    Excursion currentExcursion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.excursionTitle);
        editTitle.setText(title);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
    }

    //makes menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //takes user back to previous page
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        //save excursion menu item
        if (item.getItemId() == R.id.saveExcursion) {
            Excursion excursion;
            //if the excursion doesn't exist makes a new excursion
            if (excursionID == -1) {
                //if the excursion list is empty, make this excursion its first excursion, otherwise makes it last
                if (repository.getmAllExcursions().size() == 0) excursionID = 1;
                else excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID);
                repository.insert(excursion);
                this.finish();
            }
            else {
                //if the excursion already exists, updates existing excursion with new inputted info
                excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID);
                repository.update(excursion);
                this.finish();
            }
            return true;
        }

        //delete excursion menu item
        if (item.getItemId() == R.id.deleteExcursion) {
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) currentExcursion = excursion;
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}