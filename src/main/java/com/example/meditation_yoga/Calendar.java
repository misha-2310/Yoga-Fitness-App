package com.example.meditation_yoga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

import com.example.meditation_yoga.Database.YogaDB;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Calendar<CalendarDay> extends AppCompatActivity {

    CalendarView materialCalendarView;
    HashSet<CalendarDay> list = new HashSet<>();

    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        yogaDB = new YogaDB(this);

        materialCalendarView = (CalendarView)findViewById(R.id.calendar);
        List<String> workoutDay = yogaDB.getWorkoutDays();
        HashSet<Calendar> convertedList = new HashSet<>();

    }
}