package UnitTest_Lu;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jason.mooo.R;
import com.example.jason.mooo.livestock_main;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by LU on 2015/10/18.
 */
public class LongPressDeleteTest extends ActivityInstrumentationTestCase2<livestock_main> {
    BeforeTestLoginFirst login = new BeforeTestLoginFirst();

    livestock_main longpress;
    ListView cows;
    ArrayAdapter<String> adapter;
    static int count = 1;
    static int numOfItem = 0;

    public LongPressDeleteTest(){
        super(livestock_main.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        longpress= getActivity();
        cows = (ListView) longpress.findViewById(R.id.cowList);
        if(count == 1) {
            SystemClock.sleep(3000);
            onView(withId(R.id.button_add_livestock)).perform(click());
            onView(withId(R.id.idText)).perform(typeText("testonly")).check(matches(withText("testonly")));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.addCow)).perform(click());
            count = 2;
            numOfItem = cows.getAdapter().getCount();
        }
    }

    @SmallTest
    public void test1ListShouldNotBeEmpty() {
        assertNotNull(cows);
        cows.deferNotifyDataSetChanged();
        assertEquals(cows.getAdapter().getCount(), numOfItem);
    }

    @SmallTest
    public void test2LongPress(){
        onView(withText("testonly")).perform(longClick());
    }

    @SmallTest
    public void test3CancelDelete(){
        onView(withText("testonly")).perform(longClick());
        onView(withText("Delete")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Cancel")).perform(click());
        cows.deferNotifyDataSetChanged();
        assertEquals(cows.getAdapter().getCount(), numOfItem);
    }

    @SmallTest
    public void test4ConfirmDelete(){
        onView(withText("testonly")).perform(longClick());
        onView(withText("Delete")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Confirm")).perform(click());
        count = 1;
        cows.deferNotifyDataSetChanged();
        numOfItem = numOfItem - 1;
        assertEquals(cows.getAdapter().getCount(), numOfItem);

        SystemClock.sleep(1000);
        onView(withId(R.id.button_profile)).perform(click());
        onView(withId(R.id.button_logout)).perform(click());
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }

}
