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
import com.example.d308joshuamaserin.entities.Vacation;

public class VacationDetails extends AppCompatActivity {

    String title;
    int vacationID;

    EditText editTitle;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        repository = new Repository(getApplication());
        editTitle = findViewById(R.id.titleText);
        vacationID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        editTitle.setText(title);
    }

    //makes menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //takes user back to previous page
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        //save vacation menu item
        if (item.getItemId() == R.id.saveVacation) {
            Vacation vacation;
            //if the vacation doesn't exist makes a new vacation
            if (vacationID == -1) {
                //if the vacation list is empty, make this vacation its first vacation, otherwise makes it last
                if (repository.getmAllVacations().size() == 0) vacationID = 1;
                else vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                vacation = new Vacation(vacationID, editTitle.getText().toString());
                repository.insert(vacation);
                this.finish();
            }
            else {
                //if the vacation already exists, updates existing vacation with new inputted info
                vacation = new Vacation(vacationID, editTitle.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }

        //delete vacation menu item
        if (item.getItemId() == R.id.deleteVacation) {
            for (Vacation vac : repository.getmAllVacations()) {
                if (vac.getVacationId() == vacationID) currentVacation = vac;
            }
            numExcursions = 0;
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }
            //if the vacation has any associated excursions, does not allow user to delete vacation and shows message.
            //Otherwise deletes vacation and gives user confirmation message
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Cannot delete a vacation if it has excursions", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
}