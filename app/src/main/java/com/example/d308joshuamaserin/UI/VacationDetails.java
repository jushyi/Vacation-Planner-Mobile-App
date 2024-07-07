package com.example.d308joshuamaserin.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d308joshuamaserin.R;
import com.example.d308joshuamaserin.database.Repository;
import com.example.d308joshuamaserin.entities.Excursion;
import com.example.d308joshuamaserin.entities.Vacation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    String title;
    String hotel;
    int vacationID;

    EditText editTitle;
    EditText editHotel;
    TextView editStartDate;
    TextView editEndDate;

    Repository repository;
    Vacation currentVacation;
    int numExcursions;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStartDate = Calendar.getInstance();
    final Calendar myCalendarEndDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        repository = new Repository(getApplication());
        editTitle = findViewById(R.id.titleText);
        editHotel = findViewById(R.id.hotelText);
        vacationID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        hotel = getIntent().getStringExtra("hotel");
        editTitle.setText(title);
        editHotel.setText(hotel);

        //date info
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editStartDate = findViewById(R.id.startDate);
        editEndDate = findViewById(R.id.endDate);

        //date picker dialog
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(VacationDetails.this,
                        startDate,
                        myCalendarStartDate.get(Calendar.YEAR),
                        myCalendarStartDate.get(Calendar.MONTH),
                        myCalendarStartDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //sets the calendar to selected date
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStartDate.set(Calendar.YEAR, year);
                myCalendarStartDate.set(Calendar.MONTH, month);
                myCalendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        //date picker dialog
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(VacationDetails.this,
                        endDate,
                        myCalendarEndDate.get(Calendar.YEAR),
                        myCalendarEndDate.get(Calendar.MONTH),
                        myCalendarEndDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //sets the calendar to selected date
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEndDate.set(Calendar.YEAR, year);
                myCalendarEndDate.set(Calendar.MONTH, month);
                myCalendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    //updates the date displayed
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStartDate.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEndDate.getTime()));
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
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            String startDateString = sdf.format(myCalendarStartDate.getTime());
            String endDateString = sdf.format(myCalendarEndDate.getTime());
            //if the vacation doesn't exist makes a new vacation
            if (vacationID == -1) {
                //if the vacation list is empty, make this vacation its first vacation, otherwise makes it last
                if (repository.getmAllVacations().size() == 0) vacationID = 1;
                else vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                vacation = new Vacation(vacationID,
                        editTitle.getText().toString(),
                        editHotel.getText().toString(),
                        startDateString,
                        endDateString);
                repository.insert(vacation);
                this.finish();
            }
            else {
                //if the vacation already exists, updates existing vacation with new inputted info
                vacation = new Vacation(vacationID,
                        editTitle.getText().toString(),
                        editHotel.getText().toString(),
                        startDateString, endDateString);
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