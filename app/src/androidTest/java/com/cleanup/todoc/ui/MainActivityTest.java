package com.cleanup.todoc.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;

import static org.hamcrest.Matchers.allOf;

import android.app.Activity;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAction;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.R;
import com.cleanup.todoc.ViewModelFactory;
import com.cleanup.todoc.database.CleanupDatabase;
import com.cleanup.todoc.repository.Repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MainActivity activityRef;
    private ViewModel vm;
    private CleanupDatabase db;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup () {

        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), CleanupDatabase.class).allowMainThreadQueries().build();
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> activityRef = activity);
        vm = new ViewModelProvider(activityRef, ViewModelFactory.getInstance()).get(MainViewModel.class);
    }

    @After
    public void tearDown() {
        activityRef = null;
    }

    @Test
    public void addActivity () {
        String textTaskText = "Task test";

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(textTaskText));
        onView(allOf(withId(R.id.project_spinner), withSpinnerText("Project Lucidia"))).perform(click());

    }










}
