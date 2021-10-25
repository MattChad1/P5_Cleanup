package com.cleanup.todoc.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.util.RecyclerViewItemCountAssertion.withItemCount;
import static com.cleanup.todoc.util.TestUtils.childAtPosition;
import static org.hamcrest.Matchers.allOf;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.cleanup.todoc.R;
import com.cleanup.todoc.util.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainActivityTest {

    private MainActivity activityRef;
    int numTasks;

    @Before
    public void setup() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> activityRef = activity);
        RecyclerView recyclerView = activityRef.findViewById(R.id.list_tasks);
        numTasks = recyclerView.getAdapter().getItemCount();
    }

    @After
    public void tearDown() {
        activityRef = null;
    }

    @Test
    public void addTask() throws UiObjectNotFoundException, InterruptedException {
        String textTaskText = "Task test " + System.currentTimeMillis() / 1000;
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(textTaskText), closeSoftKeyboard());
        onView(withId(R.id.project_spinner)).perform(click());
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject spinnerItem = uiDevice.findObject(new UiSelector().text("Projet Lucidia"));
        spinnerItem.click();
        Thread.sleep(200);
        onView(withText(activityRef.getString(R.string.add))).perform(click());
        onView(withId(R.id.list_tasks)).check(withItemCount(numTasks + 1));
        onView(allOf(withId(R.id.lbl_task_name), withText(textTaskText))).check(matches(isDisplayed()));
    }

    @Test
    public void deleteTask() throws InterruptedException {
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(1, TestUtils.clickInItemView(R.id.img_delete)));
        Thread.sleep(200);
        onView(withId(R.id.list_tasks)).check(withItemCount(numTasks - 1));
    }

    @Test
    public void sortTasksAlphabetical() throws UiObjectNotFoundException, InterruptedException {
        String taskA = "a";
        String taskZ = "zzz";

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(taskA), closeSoftKeyboard());
        onView(withId(R.id.project_spinner)).perform(click());
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject spinnerItem = uiDevice.findObject(new UiSelector().text("Projet Tartampion"));
        spinnerItem.click();
        Thread.sleep(200);
        onView(withText(activityRef.getString(R.string.add))).perform(click());

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(taskZ), closeSoftKeyboard());
        onView(withId(R.id.project_spinner)).perform(click());
        spinnerItem.click();
        Thread.sleep(200);
        onView(withText(activityRef.getString(R.string.add))).perform(click());

        onView(allOf(withId(R.id.action_filter), withContentDescription("Filter"))).perform(click());
        onView(allOf(withId(R.id.title), withText(activityRef.getString(R.string.sort_alphabetical)))).perform(click());
        onView(childAtPosition(withId(R.id.list_tasks), 0)).check(matches(withChild(withChild(withText(taskA)))));
        onView(childAtPosition(withId(R.id.list_tasks), numTasks + 1)).check(matches(withChild(withChild(withText(taskZ)))));

        onView(allOf(withId(R.id.action_filter), withContentDescription("Filter"))).perform(click());
        onView(allOf(withId(R.id.title), withText(activityRef.getString(R.string.sort_alphabetical_invert)))).perform(click());
        onView(childAtPosition(withId(R.id.list_tasks), 0)).check(matches(withChild(withChild(withText(taskZ)))));
        onView(childAtPosition(withId(R.id.list_tasks), 0)).check(matches(withChild(withChild(withText(taskZ)))));
        onView(childAtPosition(withId(R.id.list_tasks), numTasks + 1)).check(matches(withChild(withChild(withText(taskA)))));
    }

    @Test
    public void sortTasksByTime() throws UiObjectNotFoundException, InterruptedException {
        String textTaskText = "Recent task test " + System.currentTimeMillis() / 1000;

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(textTaskText), closeSoftKeyboard());
        onView(withId(R.id.project_spinner)).perform(click());
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject spinnerItem = uiDevice.findObject(new UiSelector().text("Projet Tartampion"));
        spinnerItem.click();
        Thread.sleep(200);
        onView(withText(activityRef.getString(R.string.add))).perform(click());

        onView(allOf(withId(R.id.action_filter), withContentDescription("Filter"))).perform(click());
        onView(allOf(withId(R.id.title), withText(activityRef.getString(R.string.sort_recent_first)))).perform(click());
        onView(childAtPosition(withId(R.id.list_tasks), 0)).check(matches(withChild(withChild(withText(textTaskText)))));

        onView(allOf(withId(R.id.action_filter), withContentDescription("Filter"))).perform(click());
        onView(allOf(withId(R.id.title), withText(activityRef.getString(R.string.sort_oldest_first)))).perform(click());
        onView(childAtPosition(withId(R.id.list_tasks), numTasks)).check(matches(withChild(withChild(withText(textTaskText)))));
    }
}
