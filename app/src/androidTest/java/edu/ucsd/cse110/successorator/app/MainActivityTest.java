package edu.ucsd.cse110.successorator.app;

import static androidx.test.core.app.ActivityScenario.launch;

import static junit.framework.TestCase.assertEquals;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.successorator.app.MainActivity;
import edu.ucsd.cse110.successorator.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private MainViewModel model;
    private GoalRepository repository;

    @Test
    public void addGoals() {
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            // Observe the scenario's lifecycle to wait until the activity is created.
            scenario.onActivity(activity -> {
                var rootView = activity.findViewById(R.id.root);

                this.model = activity.getMainViewModel();
                Goal goal1 = new Goal(1, "Goal 1", false);
                Goal goal2 = new Goal(2, "Goal 2", false);

                this.repository = model.getRepo();
                model.clearRepository();
                assertEquals(0, repository.count());

                model.addGoal(goal1);
                assertEquals(1, repository.count());
                assertEquals("Goal 1", repository.tempFind(1).description());
                assertEquals(false, repository.tempFind(1).completed());

                model.addGoal(goal2);
                assertEquals(2, repository.count());
                assertEquals("Goal 2", repository.tempFind(2).description());
                assertEquals(false, repository.tempFind(2).completed());

            });

            // Simulate moving to the started state (above will then be called).
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }
}