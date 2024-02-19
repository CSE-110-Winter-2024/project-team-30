package edu.ucsd.cse110.successorator.lib.domain;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface GoalRepository {
    Subject<Goal> find(int id);
    Goal tempFind(int id);

    Subject<List<Goal>> findAll();

    Subject<List<Goal>> findCompleted(Boolean completed);

    void save(Goal goal);

    void save(List<Goal> goals);

    void changeCompleted(int id);

    void add(Goal goal);

    int count();

    void remove(int id);
    ArrayList<Goal> tempFindAll();

    void deleteCompleted();

    void clear();
}