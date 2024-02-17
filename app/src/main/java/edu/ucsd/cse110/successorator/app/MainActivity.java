package edu.ucsd.cse110.successorator.app;

import android.os.Bundle;
<<<<<<< HEAD
import android.os.Handler;
=======
import android.view.View;
>>>>>>> 9aa69cf8a3a026c767941010bdbf229078107400
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.app.ui.GoalListAdapter;
import edu.ucsd.cse110.successorator.app.ui.GoalListFragment;
import edu.ucsd.cse110.successorator.app.ui.dialog.AddGoalFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
<<<<<<< HEAD
    private GoalsListAdapter adapter;
    private MainViewModel model;
    private TextView textViewDate;
    private Handler handler;// won't need later when we do fragments

=======
    private GoalListAdapter adapter;
    private MainViewModel model;
>>>>>>> 9aa69cf8a3a026c767941010bdbf229078107400

    //sets up the initial main activity view xml
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_title);
        
        //grab view by inflating xml layout file
        this.view = ActivityMainBinding.inflate(getLayoutInflater());

<<<<<<< HEAD
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

=======
        //set up the date
>>>>>>> 9aa69cf8a3a026c767941010bdbf229078107400
        String date = new SimpleDateFormat("EEEE, M/dd", Locale.getDefault()).format(new Date());
        TextView dateTextView = view.dateTextView;
        if (dateTextView != null) {
            dateTextView.setText(date);
        }

        //connect the add goal button to the addGoalDialogFragment onClick
        view.addGoalButton.setOnClickListener(v -> {
            var dialogFragment = AddGoalFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "AddGoalFragment");
        });

        //connect to the model(MainViewModel)
        var modelOwner = this;
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.model = modelProvider.get(MainViewModel.class);

        //setup the adapter for the list, so it can update it at the beginning
        this.adapter = new GoalListAdapter(getApplicationContext(), List.of());

        //check for changes in the database thorugh getOrderedGoals
        model.getOrderedGoals().registerObserver(goals -> {
            if (goals == null) {
                view.defaultGoals.setVisibility(View.VISIBLE);
                return;
            }
            if (goals.size() == 0) {
                view.defaultGoals.setVisibility(View.VISIBLE);
            } else {
                view.defaultGoals.setVisibility(View.INVISIBLE);
            }
            adapter.clear();
            adapter.addAll(new ArrayList<>(goals)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });
        
        //show the GoalListFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, GoalListFragment.newInstance())
                .commit();

        //set the current view this main activity that we just set up
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