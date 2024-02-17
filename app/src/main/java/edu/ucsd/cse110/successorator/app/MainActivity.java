package edu.ucsd.cse110.successorator.app;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import edu.ucsd.cse110.successorator.app.data.db.GoalDao;
import edu.ucsd.cse110.successorator.app.data.db.GoalDao_Impl;
import edu.ucsd.cse110.successorator.app.data.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.app.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.app.ui.GoalsListAdapter;
import edu.ucsd.cse110.successorator.app.ui.dialog.AddGoalFragment;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
//import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActivityMainBinding view;
    private GoalsListAdapter adapter;
    private MainViewModel model;
    private TextView textViewDate;
    private Handler handler;// won't need later when we do fragments


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_title);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        var database = Room.databaseBuilder(
                        getApplicationContext(),
                        SuccessoratorDatabase.class,
                        "successorator-database"
                )
                .allowMainThreadQueries()
                .build();

        //var dataSource = InMemoryDataSource.fromDefault();
        var dataSource = new RoomGoalRepository(database.goalDao());
        this.model = new MainViewModel(dataSource);
//        var modelOwner = this;
//        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
//        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
//        this.model = modelProvider.get(MainViewModel.class);

        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        //resources from the strings file
        Resources res = getResources();
        view.defaultGoals.setText(res.getString(R.string.default_goals));
        //model.getDisplayedText().observe(text -> view.defaultGoals.setText(text));
        this.adapter = new GoalsListAdapter(MainActivity.this, List.of());
        /*
        model.getOrderedGoals().observe(goals -> {
            if (goals == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(goals)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });
        */
        adapter.addAll(model.getOrderedGoals());
        view.taskList.setAdapter(adapter);

        view.addButton3.setOnClickListener(v -> {
            var dialogFragment = AddGoalFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "AddGoalFragment");
        });

        textViewDate = findViewById(R.id.text_view_date);
        handler = new Handler();

        // Initial update
        updateDate();

        // Schedule periodic updates (e.g., every minute)
        handler.postDelayed(dateUpdater, 60000);
        /*
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        String date = new SimpleDateFormat("EEEE, M/dd", Locale.getDefault()).format(new Date());
        TextView dateTextView = view.dateTextView;
        if (dateTextView != null) {
            dateTextView.setText(date);
        }
        setContentView(view.getRoot());

 */
    }
    private Runnable dateUpdater = new Runnable() {
        @Override
        public void run() {
            updateDate();
            // Schedule the next update
            handler.postDelayed(this, 60000);
        }
    };

    // Method to update the date
    private void updateDate() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        textViewDate.setText(currentDate);
    }

    @Override
    protected void onDestroy() {
        // Remove callbacks to prevent memory leaks
        handler.removeCallbacks(dateUpdater);
        super.onDestroy();
    }
}
