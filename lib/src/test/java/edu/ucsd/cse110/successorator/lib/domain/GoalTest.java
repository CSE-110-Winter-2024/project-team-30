package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

public class GoalTest {

    @org.junit.Test
    public void id() {
        var goal = new Goal(3, "Eat Breakfast", true);
        assertEquals(Integer.valueOf(3), goal.id());
    }

    @org.junit.Test
    public void description() {
        var goal = new Goal(3, "Eat Breakfast", true);
        assertEquals("Eat Breakfast", goal.description());
    }

    @org.junit.Test
    public void completed() {
        var goal = new Goal(3, "Eat Breakfast", true);
        assertEquals(true, goal.completed());
    }
}