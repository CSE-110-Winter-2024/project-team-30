package edu.ucsd.cse110.successorator.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.ucsd.cse110.successorator.app.MainViewModel;
import edu.ucsd.cse110.successorator.app.databinding.FragmentPendingListBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class PendingListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentPendingListBinding view;
    private GoalListAdapter incompleteAdapter;

    public PendingListFragment() {
        // Required empty public constructor
    }

    public static PendingListFragment newInstance() {
        PendingListFragment fragment = new PendingListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentPendingListBinding.inflate(inflater, container, false);

        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        // Initialize the Adapter (with an empty list for now) for incomplete tasks
        this.incompleteAdapter = new GoalListAdapter(requireContext(), List.of(), id -> {
            activityModel.changeToTodayViewComplete(id);
        }, 1);

        activityModel.getIncompleteGoals().registerObserver(goals -> {
            if (goals == null) return;

            var pendingGoals = new ArrayList<Goal>();
            for(Goal g : goals){
                if(g.repType().equals("pending")){
                    pendingGoals.add(g);
                }

            }
            incompleteAdapter.clear();
            incompleteAdapter.addAll(pendingGoals); // remember the mutable copy here!
            incompleteAdapter.notifyDataSetChanged();
        });

        // Set the adapter on the ListView
        view.uncompletedGoalList.setAdapter(incompleteAdapter);

        return view.getRoot();
    }
}