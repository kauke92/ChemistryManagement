package UnitTest_Lu;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.jason.mooo.Login;
import com.example.jason.mooo.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by LU on 2015/10/18.
 */
public class BeforeTestLoginFirst extends ActivityInstrumentationTestCase2<Login> {
    Login login;
    String name;
    public BeforeTestLoginFirst(){
        super(Login.class);
    }
    @Override
    public void setUp() throws Exception {
        super.setUp();
        login = getActivity();
        name =  login.findViewById(R.id.username_text).toString();

    }

    @SmallTest
    public void testLoginToAccount() {
        onView(withId(R.id.username_text)).perform(typeText("jasoncorcoran0306@gmail.com")).check(matches(withText("jasoncorcoran0306@gmail.com")));
        onView(withId(R.id.password_text)).perform(typeText("password")).check(matches(withText("password")));
        onView(withId(R.id.button_login)).perform(click());
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
