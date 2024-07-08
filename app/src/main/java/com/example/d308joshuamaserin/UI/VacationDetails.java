package com.example.d308joshuamaserin.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class VacationDetails extends AppCompatActivity {

    String title;
    String hotel;
    int vacationID;
    String setStartDate;
    String setEndDate;

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

    List<Excursion> filteredExcursions = new ArrayList<>();

    //for alert
    Random rand = new Random();
    int numAlert = rand.nextInt(99999);

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
        setStartDate = getIntent().getStringExtra("startDate");
        setEndDate = getIntent().getStringExtra("endDate");
        editTitle.setText(title);
        editHotel.setText(hotel);
        numAlert = rand.nextInt(99999);

        //makes button to add Excursions
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                startActivity(intent);
            }
        });

        //recyclerview populates excursions associated with this vacation
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (Excursion e: repository.getMAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setMExcursions(filteredExcursions);

        //date info
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        //for displaying date
        if (setStartDate != null) {
            try {
                Date startDate = sdf.parse(setStartDate);
                Date endDate = sdf.parse(setEndDate);
                myCalendarStartDate.setTime(startDate);
                myCalendarEndDate.setTime(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        editStartDate = findViewById(R.id.startDate);
        editEndDate = findViewById(R.id.endDate);

        //date picker dialog
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //for displaying date
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = setStartDate;
                try {
                    myCalendarStartDate.setTime(sdf.parse(info));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

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

                //for displaying date
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = setEndDate;
                try {
                    myCalendarEndDate.setTime(sdf.parse(info));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

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
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            String startDateString = sdf.format(myCalendarStartDate.getTime());
            String endDateString = sdf.format(myCalendarEndDate.getTime());

            //validation for vacation dates
            try {
                Date startDate = sdf.parse(startDateString);
                Date endDate = sdf.parse(endDateString);
                if (endDate.before(startDate)) {
                    Toast.makeText(this, "End date cannot be before start date", Toast.LENGTH_LONG).show();
                } else {
                    Vacation vacation;
                    //if the vacation doesn't exist makes a new vacation
                    if (vacationID == -1) {
                        //if the vacation list is empty, make this vacation its first vacation, otherwise makes it last
                        if (repository.getMAllVacations().size() == 0) vacationID = 1;
                        else
                            vacationID = repository.getMAllVacations().get(repository.getMAllVacations().size() - 1).getVacationId() + 1;
                        vacation = new Vacation(vacationID,
                                editTitle.getText().toString(),
                                editHotel.getText().toString(),
                                startDateString,
                                endDateString);
                        repository.insert(vacation);
                        this.finish();
                    } else {
                        //if the vacation already exists, updates existing vacation with new inputted info
                        vacation = new Vacation(vacationID,
                                editTitle.getText().toString(),
                                editHotel.getText().toString(),
                                startDateString, endDateString);
                        repository.update(vacation);
                        this.finish();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //delete vacation menu item
        if (item.getItemId() == R.id.deleteVacation) {
            for (Vacation vac : repository.getMAllVacations()) {
                if (vac.getVacationId() == vacationID) currentVacation = vac;
            }
            numExcursions = 0;
            for (Excursion excursion : repository.getMAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }
            //if the vacation has any associated excursions, does not allow user to delete vacation and shows message.
            //Otherwise deletes vacation and gives user confirmation message
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            }
            else {
                Toast.makeText(VacationDetails.this, "Cannot delete a vacation if it has excursions", Toast.LENGTH_LONG).show();
            }
        }

        //alert for start date
        if (item.getItemId() == R.id.alertStart) {
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "Vacation " + title + " is starting";
            alertPicker(dateFromScreen, alert);

            return true;
        }

        //alert for end date
        if (item.getItemId() == R.id.alertEnd) {
            String dateFromScreen = editEndDate.getText().toString();
            String alert = "Vacation " + title + " is ending";
            alertPicker(dateFromScreen, alert);

            return true;
        }

        //alert for both start and end date
        if (item.getItemId() == R.id.alertBoth) {
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "Vacation " + title + " is starting";
            alertPicker(dateFromScreen, alert);
            dateFromScreen = editEndDate.getText().toString();
            alert = "Vacation " + title + " is ending";
            alertPicker(dateFromScreen, alert);

            return true;
        }

        //vacation sharing feature
        if (item.getItemId() == R.id.share) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE, "Vacation Details!");
            StringBuilder shareData = new StringBuilder();
            shareData.append("Vacation title: " + editTitle.getText().toString() + "\n");
            shareData.append("Hotel Name: " + editHotel.getText().toString() + "\n");
            shareData.append("Start Date: " + editStartDate.getText().toString() + "\n");
            shareData.append("End Date: " + editEndDate.getText().toString() + "\n");
            for (int i = 0; i < filteredExcursions.size(); i++) {
                shareData.append("Excursion " + (i + 1) + ": " + filteredExcursions.get(i).getExcursionTitle() + "\n");
                shareData.append("Excursion " + (i + 1) + " Date: " + filteredExcursions.get(i).getExcursionDate() + "\n");
            }
            sentIntent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }

        return true;
    }

    //the logic to set the date alarm
    public void alertPicker(String dateFromScreen, String alert) {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateFromScreen);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
        intent.putExtra("key", alert);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        numAlert = rand.nextInt(99999);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //recyclerview populates excursions associated with this vacation
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e: repository.getMAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setMExcursions(filteredExcursions);

        updateLabelStart();
        updateLabelEnd();
    }
}