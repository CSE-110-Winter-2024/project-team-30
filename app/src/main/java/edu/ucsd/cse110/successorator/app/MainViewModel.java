package edu.ucsd.cse110.successorator.app;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class MainViewModel extends ViewModel{
    private static final String LOG_TAG = "MainViewModel";
    private final GoalRepository goalRepository;

    private final SimpleSubject<List<Integer>> goalOrdering;
    private final SimpleSubject<List<Goal>> orderedGoals;
    private final SimpleSubject<String> displayedText;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert  app != null;
                        return new MainViewModel(app.getGoalRepository());
                    });

    public MainViewModel(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;

        this.goalOrdering = new SimpleSubject<>();
        this.orderedGoals = new SimpleSubject<>();
        this.displayedText = new SimpleSubject<>();

        // When the list of cards changes (or is first loaded), reset the ordering.
        goalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var ordering = new ArrayList<Integer>();
            for (int i = 0; i < goals.size(); i++) {
                ordering.add(i);
            }
            goalOrdering.setValue(ordering);
        });

        goalOrdering.observe(ordering -> {
            if (ordering == null) return;

            var goals = new ArrayList<Goal>();
            for (var id : ordering) {
                var goal = goalRepository.find(id).getValue();
                if (goal == null) return;
                goals.add(goal);
            }
            this.orderedGoals.setValue(goals);
        });
    }
    public SimpleSubject<List<Goal>> getOrderedGoals() {
        return orderedGoals;
    }

    public SimpleSubject<String> getDisplayedText() {
        return displayedText;
    }
}