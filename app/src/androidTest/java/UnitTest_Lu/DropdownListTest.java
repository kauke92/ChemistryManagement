package UnitTest_Lu;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jason.mooo.R;
import com.example.jason.mooo.template_pages;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by LU on 2015/10/18.
 */
public class DropdownListTest extends ActivityInstrumentationTestCase2<template_pages> {
    template_pages dropdwonList;
    Spinner drugs;
    Spinner amount;
    Spinner days;

    public DropdownListTest(){
        super(template_pages.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dropdwonList= getActivity();
        drugs = (Spinner) dropdwonList.findViewById(R.id.Drugs);
        amount = (Spinner) dropdwonList.findViewById(R.id.amount);
        days = (Spinner) dropdwonList.findViewById(R.id.days);
    }

    @SmallTest
    public void test1DefaultValue() {
        assertEquals(drugs.getAdapter().getCount(), 0);
        assertEquals(amount.getAdapter().getItem(0), "5");
        assertEquals(days.getAdapter().getItem(0), "5 Days");

    }
    @SmallTest
    public void test2ChooseDefault() {
        onView(withId(R.id.livestock_info_bottom_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.button_add_livestock)).perform(click());
        onView(withId(R.id.idText)).perform(typeText("testonly")).check(matches(withText("testonly")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addCow)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("testonly")).perform(click());

        assertEquals(drugs.getAdapter().getItem(0).toString(), "test - 1");
        assertEquals(amount.getAdapter().getItem(0), "5");
        assertEquals(days.getAdapter().getItem(0), "5 Days");

        onView(withId(R.id.administer)).perform(click());
        SystemClock.sleep(3000);
    }

    @SmallTest
    public void test3ChangeValue() {
        onView(withId(R.id.livestock_info_bottom_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.button_add_livestock)).perform(click());
        onView(withId(R.id.idText)).perform(typeText("testonly")).check(matches(withText("testonly")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addCow)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("testonly")).perform(click());

        assertEquals(drugs.getAdapter().getItem(1).toString(), "test - 1");
        assertEquals(amount.getAdapter().getItem(1), "10");
        assertEquals(days.getAdapter().getItem(1), "10 Days");

        onData(anything()).inAdapterView(withId(R.id.Drugs)).atPosition(1).perform(click());
        Espresso.pressBack();
        SystemClock.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.amount)).atPosition(1).perform(click());
        Espresso.pressBack();
        SystemClock.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.days)).atPosition(1).perform(click());
        Espresso.pressBack();
        SystemClock.sleep(2000);

        onView(withId(R.id.administer)).perform(click());
        SystemClock.sleep(2000);

        onView(withId(R.id.button_profile)).perform(click());
        onView(withId(R.id.button_logout)).perform(click());
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }



}
