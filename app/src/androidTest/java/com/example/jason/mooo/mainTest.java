package com.example.jason.mooo;

//import android.test.InstrumentationRegistry;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.DatePicker;

import android.support.test.espresso.contrib.PickerActions;
import android.widget.EditText;

import com.example.jason.mooo.Resources.AlertButton;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
//import android.support.test.espresso.Espresso;
import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;
import org.hamcrest.Matchers;

/**
 * Created by jason on 13/10/15.
 */

/*
 Register
 Login
 Add New Medicine
 Purchase Medicine
 PDF Generate
 Add New Livestock
 Livestock History
 Delete Livestock
 Check Batches
 */

//Administer Medicine

public class mainTest {



    @Rule
    public ActivityTestRule<Login> activityTestRule =
            new ActivityTestRule<>(Login.class);



    @Test
    public void validateRegisterButtonClick(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.firstname_text))
                .check(matches(withText((""))));
    }



    @Test
    public void validateNavigateToProfile(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_profile)).perform(click());
        logout();
    }

    @Test
    public void validateLoginMissingUsername(){
        onView(withId(R.id.password_text)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_login)).perform(click());
        SystemClock.sleep(3000);
        onView(withText("The specified email or password is invalid.")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());



    }



    @Test
    public void validateNavigateToAddNewMedicine(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_Medicines)).perform(click());
        onView(withId(R.id.button_add_chemical)).perform(click());

        logout();


    }

    @Test
    public void validateNavigateToAddNewLivestock(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        onView(withId(R.id.button_add_livestock)).perform(click());

        logout();


    }

    @Test
    public void validateNavigateToLivestockInfo(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        onView(withText("bessie")).perform(click());
        logout();


    }

    @Test
    public void validateNavigateToChangePassword(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_profile)).perform(click());
        onView(withId(R.id.button_change_password)).perform(click());
        logout();
    }

    @Test
    public void validateIncorrectChangePassword(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_profile)).perform(click());
        onView(withId(R.id.button_change_password)).perform(click());
        onView(withId(R.id.old_password_change)).perform(typeText("wrongpassword"));
        onView(withId(R.id.new_password_change)).perform(typeText("wrongpassword2"));
        onView(withId(R.id.re_type_password_change)).perform(typeText("wrongpassword2"));
        onView(withId(R.id.button_confirm_change_password)).perform(click());
        onView(withText("The specified password is incorrect.")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());


        logout();
    }

    @Test
    public void validateNavigateToPurchaseMedicine(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        logout();
    }

    @Test
    public void validateNavigateToChangeEmail(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_profile)).perform(click());
        onView(withId(R.id.button_change_email)).perform(click());
        logout();
    }

    @Test
    public void validateLoginMissingPassword(){
        onView(withId(R.id.username_text)).perform(typeText("jasoncorcoran0306@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_login)).perform(click());
        SystemClock.sleep(3000);
        onView(withText("The specified email or password is invalid.")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

    }

    @Test
    public void validateLoginMissingUsernameAndPassword(){
        onView(withId(R.id.button_login)).perform(click());
        SystemClock.sleep(3000);
        onView(withText("The specified email or password is invalid.")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

    }

    @Test
    public void validateLoginIncorrectUsername(){
        onView(withId(R.id.username_text)).perform(typeText("jasoncorcoran0306awdawd@gmail.com"));
        onView(withId(R.id.password_text)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_login)).perform(click());
        SystemClock.sleep(3000);
        onView(withText("The specified email or password is invalid.")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());


    }

    @Test
    public void validateLoginIncorrectPassword(){
        onView(withId(R.id.username_text)).perform(typeText("jasoncorcoran0306@gmail.com"));
        onView(withId(R.id.password_text)).perform(typeText("passwordawdawdawdawd"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_login)).perform(click());
        SystemClock.sleep(3000);
        onView(withText("The specified email or password is invalid.")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

    }

    @Test
    public void validateLoginSuccess(){
        login();
        SystemClock.sleep(3000);
        logout();
    }



    @Test
    public void validateRegisterDifferentPasswords(){

        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.firstname_text)).perform(typeText("Jason"));
        onView(withId(R.id.surname_text)).perform(typeText("Corcoran"));
        onView(withId(R.id.email)).perform(typeText("jasoncorcoran0306@gmail.com"));
        onView(withId(R.id.password_text))
                .perform(typeText("password"));
        onView(withId(R.id.password2_text))
                .perform(typeText("password2"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_register)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Passwords don't match.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();

    }


    @Test
    public void validateRegisterMissingFirstName(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.surname_text)).perform(typeText("Corcoran"));
        onView(withId(R.id.email)).perform(typeText("jasoncorcoran0306@gmail.com"));
        onView(withId(R.id.password_text))
                .perform(typeText("password"));
        onView(withId(R.id.password2_text))
                .perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_register)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter your firstname.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();


    }

    @Test
    public void validateRegisterMissingSurname(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.firstname_text)).perform(typeText("Jason"));
        onView(withId(R.id.email)).perform(typeText("jasoncorcoran0306@gmail.com"));
        onView(withId(R.id.password_text))
                .perform(typeText("password"));
        onView(withId(R.id.password2_text))
                .perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_register)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter your surname.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();



    }

    @Test
    public void validateRegisterMissingEmail(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.firstname_text)).perform(typeText("Jason"));
        onView(withId(R.id.surname_text)).perform(typeText("Corcoran"));
        onView(withId(R.id.password_text))
                .perform(typeText("password"));
        onView(withId(R.id.password2_text))
                .perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_register)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter your Email.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();



    }

    @Test
    public void validateRegisterShortPassword(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.firstname_text)).perform(typeText("Jason"));
        onView(withId(R.id.surname_text)).perform(typeText("Corcoran"));
        onView(withId(R.id.password_text))
                .perform(typeText("passwor"));
        onView(withId(R.id.password2_text))
                .perform(typeText("passwor"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_register)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Password is too short. 8 characters are required.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();


    }




    @Test
    public void validRegisterEmailTaken(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.firstname_text)).perform(typeText("Jason")).check(matches(withText("Jason")));
        onView(withId(R.id.surname_text)).perform(typeText("Corcoran")).check(matches(withText("Corcoran")));
        onView(withId(R.id.email)).perform(typeText("jasoncorcoran0306a@gmail.com")).check(matches(withText("jasoncorcoran0306a@gmail.com")));
        onView(withId(R.id.password_text)).perform(typeText("password")).check(matches(withText("password")));
        onView(withId(R.id.password2_text)).perform(typeText("password")).check(matches(withText("password")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.username_text)).perform(typeText("jasoncorcoran0306@gmail.com")).check(matches(withText("jasoncorcoran0306@gmail.com")));
        onView(withId(R.id.password_text)).perform(typeText("password")).check(matches(withText("password")));
        onView(withText("Account with this Email Address already exists.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();




    }




/*
    @Test
    public void validatePurchaseMedicineInvalidNoMedicine(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(0).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.Batch)).perform(typeText("1")).check(matches(withText("1")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 18));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("No Medicine has been selected.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //onView(withText("OK")).perform(click());
        logout();
    }

    */

    @Test
    public void validatePurchaseMedicineInvalidRecievedDate(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(1).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.Batch)).perform(typeText("1")).check(matches(withText("1")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter a received date.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();
    }


    @Test
    public void validatePurchaseMedicineInvalidExpiryDate(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(1).perform(click());
        Espresso.pressBack();
        SystemClock.sleep(1000);
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.Batch)).perform(typeText("1")).check(matches(withText("1")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 18));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter an expiry date.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();
    }



    @Test
    public void validatePurchaseMedicineInvalidPurchasePlace(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(1).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.Batch)).perform(typeText("1")).check(matches(withText("1")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 18));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(500);
        onView(withText("Please enter a purchase place.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //onView(withText("OK")).perform(click());
        logout();
    }

    @Test
    public void validatePurchaseMedicineInvalidBatch(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(1).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        //onView(withId(R.id.Batch)).perform(typeText("1")).check(matches(withText("1")));
        //Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 18));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter a batch.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();
    }

    @Test
    public void validatePurchaseMedicineInvalidQuantity(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(1).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.Batch)).perform(typeText("1")).check(matches(withText("1")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 18));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter an amount.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();
    }

    //Integration Test: Purchase a Medicine and then Check that it exists as a batch.


    @Test
    public void validatePurchaseMedicineCheckBatch(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(0).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.Batch)).perform(typeText("example")).check(matches(withText("example")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 18));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_batch)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.batch)).atPosition(0).check(matches(isDisplayed()));
        logout();
    }




    //Junit Test: Checks the handling of Adding a new Medicine when no medicine name is provided.

    @Test
    public void validateAddNewMedicineEmptyMedicine(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_Medicines)).perform(click());
        onView(withId(R.id.button_add_chemical)).perform(click());
        // onView(withId(R.id.drugName)).perform(typeText("Herron")).check(matches(withText("Herron")));
        onView(withId(R.id.milkText)).perform(typeText("6")).check(matches(withText("6")));
        onView(withId(R.id.slaughterText)).perform(typeText("16")).check(matches(withText("16")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addDrug)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter a medicine name.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();

    }

    //Junit Test: Checks the handling of Adding a new Medicine when no withholding milk value is provided.

    @Test
    public void validateAddNewMedicineEmptyWithholdingMilk(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_Medicines)).perform(click());
        onView(withId(R.id.button_add_chemical)).perform(click());
        onView(withId(R.id.drugName)).perform(typeText("Herron")).check(matches(withText("Herron")));
        //onView(withId(R.id.milkText)).perform(typeText("6")).check(matches(withText("6")));
        onView(withId(R.id.slaughterText)).perform(typeText("16")).check(matches(withText("16")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addDrug)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter a withholding value for milk.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();

    }

    //Junit Test: Checks the handling of Adding a new Medicine when no withholding slaughter value is provided.

    @Test
    public void validateAddNewMedicineEmptyWithholdingSlaughter(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_Medicines)).perform(click());
        onView(withId(R.id.button_add_chemical)).perform(click());
        onView(withId(R.id.drugName)).perform(typeText("Herron")).check(matches(withText("Herron")));
        onView(withId(R.id.milkText)).perform(typeText("6")).check(matches(withText("6")));
        //onView(withId(R.id.slaughterText)).perform(typeText("16")).check(matches(withText("16")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addDrug)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Please enter a withholding value for slaughter.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();

    }




    /*
    @Test
    public void validateDuplicateAccount(){
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.firstname_text)).perform(typeText("Jason")).check(matches(withText("Jason")));
        onView(withId(R.id.surname_text)).perform(typeText("Corcoran")).check(matches(withText("Corcoran")));
        onView(withId(R.id.email)).perform(typeText("jasoncorcoran0306@gmail.com")).check(matches(withText("jasoncorcoran0306@gmail.com")));
        onView(withId(R.id.password_text)).perform(typeText("password")).check(matches(withText("password")));
        onView(withId(R.id.password2_text)).perform(typeText("password")).check(matches(withText("password")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.username_text)).perform(typeText("jasoncorcoran0306@gmail.com")).check(matches(withText("jasoncorcoran0306@gmail.com")));
        onView(withId(R.id.password_text)).perform(typeText("password")).check(matches(withText("password")));
        onView(withId(R.id.button_login)).perform(click());
        SystemClock.sleep(3000);
        logout();

    }

*/

    @Test
    public void validateUsernameLoginText() {
        onView(withId(R.id.username_text)).perform(typeText("sblake@outlook.com")).check(matches(withText("sblake@outlook.com")));
    }

    @Test
    public void validateAdministerMedicine() {
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(0).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        SystemClock.sleep(1000);
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 8, 25));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.Batch)).perform(typeText("1")).check(matches(withText("1")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        SystemClock.sleep(1000);
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 8, 18));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_middle_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("bessie")).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.administer)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        logout();

    }



    @Test
    public void validateRegisterLoginText(){
        onView(withId(R.id.username_text)).perform(typeText("jasoncorcoran@gmail.com")).check(matches(withText("jasoncorcoran@gmail.com")));


    }
    @Test
    public void validatePasswordLoginText() {
        onView(withId(R.id.password_text)).perform(typeText("password")).check(matches(withText("password")));
    }

    @Test
    public void validateToolbarNavigation(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_home_bottom_chemical)).perform(click());
        onView(withId(R.id.button_Medicine_bottom_home)).perform(click());
        onView(withId(R.id.button_home_bottom_livestock)).perform(click());
        onView(withId(R.id.button_livestockmain_bottom_home)).perform(click());
        onView(withId(R.id.button_home_bottom_home)).perform(click());
        logout();



    }

    @Test
    public void validateLoginCombinedText() {
        onView(withId(R.id.username_text)).perform(typeText("sblake@outlook.com")).check(matches(withText("sblake@outlook.com")));
        onView(withId(R.id.password_text)).perform(typeText("password")).check(matches(withText("password")));
    }

    @Test
    public void validateUsername(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.text_username)).check(matches(withText("Jasoncorcoran0306")));
        logout();
    }

    @Test
    public void validateAddNewMedicine(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_Medicines)).perform(click());
        onView(withId(R.id.button_add_chemical)).perform(click());
        onView(withId(R.id.drugName)).perform(typeText("Herron")).check(matches(withText("Herron")));
        onView(withId(R.id.milkText)).perform(typeText("6")).check(matches(withText("6")));
        onView(withId(R.id.slaughterText)).perform(typeText("16")).check(matches(withText("16")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addDrug)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        logout();

    }

    @Test
    public void validateUpdateQuantity(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_Medicines)).perform(click());
        onView(withId(R.id.button_add_chemical)).perform(click());
        onView(withId(R.id.drugName)).perform(typeText("qqqqq")).check(matches(withText("qqqqq")));
        onView(withId(R.id.milkText)).perform(typeText("6")).check(matches(withText("6")));
        onView(withId(R.id.slaughterText)).perform(typeText("16")).check(matches(withText("16")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addDrug)).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.button_profile)).perform(click());
        onView(withId(R.id.button_userinfo_bottom_home)).perform(click());
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(3000);
       // onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).check(matches(withText("Quant"))).perform(click());
        onData(hasToString(startsWith("qqqqq")))
                .inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(0)
                .perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        SystemClock.sleep(3000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.Batch)).perform(typeText("ork")).check(matches(withText("ork")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 18));
        SystemClock.sleep(3000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(3000);
        //onView(withText("OK")).perform(click());
        onView(withId(R.id.button_purchaseMedicine_bottom_livestock)).perform(click());
        SystemClock.sleep(3000);
        onData(hasToString(startsWith("bessie")))
                .inAdapterView(withId(R.id.cowList)).atPosition(0)
                .perform(click());
        SystemClock.sleep(3000);
        //onData(anything()).inAdapterView(withId(R.id.Drugs)).check(matches(withText("Quant"))).perform(click());
        onData(hasToString(startsWith("qqqqq - ork")))
                .inAdapterView(withId(R.id.Drugs)).atPosition(0)
                .perform(click());
        //onData(anything()).inAdapterView(withId(R.id.amount)).atPosition(0).perform(click());
        Espresso.pressBack();

        onData(hasToString(startsWith("5")))
                .inAdapterView(withId(R.id.amount)).atPosition(0)
                .perform(click());
        Espresso.pressBack();

        //onData(anything()).inAdapterView(withId(R.id.days)).atPosition(0).perform(click());
        onData(hasToString(startsWith("5 Days")))
                .inAdapterView(withId(R.id.days)).atPosition(0)
                .perform(click());
        Espresso.pressBack();

        SystemClock.sleep(3000);
        onView(withId(R.id.administer)).perform(click());
        SystemClock.sleep(3000);

        //onView(withText("OK")).perform(click());

        onView(withId(R.id.button_livestockmain_bottom_home)).perform(click());
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_batch)).perform(click());
        SystemClock.sleep(3000);
        //onData(anything()).inAdapterView(withId(R.id.batch)).check(matches(withText("Quant-Test"))).perform(click());
        onData(hasToString(startsWith("qqqqq - ork")))
                .inAdapterView(withId(R.id.batch)).atPosition(0)
                .perform(click());
        SystemClock.sleep(3000);
        onView(withText("Amount Left: 995")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
        logout();



    }

    //test for expiration

    //test for none left




    /*
    This test currently only works when there's nothing in the PDF and an error message is returned.
     */
    @Test
    public void validatePDFGenerate(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_pdf)).perform(click());
        onView(withId(R.id.from_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 8, 25));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.to_date)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.genReport)).perform(click());
        SystemClock.sleep(500);
        Espresso.pressBack();
        //onView(withText("OK")).perform(click());
        logout();
    }

    /*

    @Test
    public void validatePDFGenerateandOpen(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_pdf)).perform(click());
        onView(withId(R.id.from_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2014, 8, 25));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.to_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.genReport)).perform(click());
//        SystemClock.sleep(500);
 //       onView(withText("PDF Viewer")).perform(click());
        Espresso.pressBack();
        //onView(withText("OK")).perform(click());
        logout();
    }
*/
    @Test
    public void validateAddNewLivestock(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        onView(withId(R.id.button_add_livestock)).perform(click());
        onView(withId(R.id.idText)).perform(typeText("bessie2")).check(matches(withText("bessie2")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addCow)).perform(click());
        onView(withText("bessie2")).check(matches(isDisplayed()));
        logout();
    }

    @Test
    public void validateAddNewLivestockEmptyName(){

        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        onView(withId(R.id.button_add_livestock)).perform(click());
        onView(withId(R.id.addCow)).perform(click());
        onView(withText("Please specify an ID.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();

    }

    //JUnit Test: Retrieve a Livestock's History.
    @Test
    public void validateLivestockSearch(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.button_search_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_cow_id)).perform(typeText("bessie"));
        onView(withText("Search")).perform(click());
        logout();
    }

    @Test
    public void validateLivestockHistoryAndSearch(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.button_search_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_cow_id)).perform(typeText("bessie"));
        onView(withText("Search")).perform(click());
        onView(withId(R.id.button_history_livestock)).perform(click());
        logout();
    }

    @Test
    public void validateLivestockUnsuccessfulSearch(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.button_search_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.search_cow_id)).perform(typeText("besawdawda"));
        onView(withText("Search")).perform(click());
        onView(withText("No cow found with that ID")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        logout();
    }

    @Test
    public void validateLivestockHistory(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("12345678")).perform(longClick());
        onView(withText("History")).perform(click());
        logout();
    }

    //JUnit Test: Delete a Livestock from the Database

    @Test
    public void validateLivestockDelete(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("bessie2")).perform(longClick());
        onView(withText("Delete")).perform(click());
        SystemClock.sleep(3000);
        onView(withText("Confirm")).perform(click());
        SystemClock.sleep(500);
        onView(withText("OK")).perform(click());
        logout();
    }



    //Integration Test: Add a Livestock to an Account and then Delete it.

    @Test
    public void validateAddLivestockAndDelete(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        onView(withId(R.id.button_add_livestock)).perform(click());
        onView(withId(R.id.idText)).perform(typeText("bessie2")).check(matches(withText("bessie2")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addCow)).perform(click());
        onView(withText("bessie2")).check(matches(isDisplayed()));
        onView(withText("bessie2")).perform(longClick());
        onView(withText("Delete")).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Confirm")).perform(click());
        SystemClock.sleep(500);
        onView(withText("OK")).perform(click());
        logout();
    }

    //Integration Test: Add a new Livestock to an Account and then search its History.

    @Test
    public void validateAddNewLivestockAndHistory(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_livestock)).perform(click());
        onView(withId(R.id.button_add_livestock)).perform(click());
        onView(withId(R.id.idText)).perform(typeText("bessie2")).check(matches(withText("bessie2")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addCow)).perform(click());
        onView(withText("bessie2")).check(matches(isDisplayed()));
        onView(withText("bessie2")).perform(longClick());
        onView(withText("History")).perform(click());
        logout();
    }



    //JUnit Test: Find a batch.

    @Test
    public void validateBatches(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.button_get_batch)).perform(click());
        logout();
    }

    //Integration Test: Purchase a Medicine.
    @Test
    public void validatePurchaseMedicine(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_pruchase_Medicine)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.spinner_add_Medicine)).atPosition(1).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.expDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 25));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.purchasePlace)).perform(typeText("Windsor")).check(matches(withText("Windsor")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.Batch)).perform(typeText("1")).check(matches(withText("1")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.AmountID)).perform(typeText("1000")).check(matches(withText("1000")));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.receivedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2015, 10, 18));
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addMedicine)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("OK")).perform(click());
        logout();

    }

    //JUnit Test: Delete a medicine from an account.

    @Test
    public void validateDeleteMedicine(){
        login();
        SystemClock.sleep(3000);
        onView(withId(R.id.button_middle_chemicals)).perform(click());
        onView(withId(R.id.button_get_Medicines)).perform(click());
        SystemClock.sleep(1000);
        onData(hasToString(startsWith("Herron")))
                .inAdapterView(withId(R.id.chemicals)).atPosition(0)
                .perform(longClick());
        onView(withText("Delete")).perform(click());
        SystemClock.sleep(3000);
        onView(withText("Confirm")).perform(click());
        SystemClock.sleep(500);
        onView(withText("Herron has been deleted.")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        logout();



    }


    public void login(){
        onView(withId(R.id.username_text)).perform(typeText("jasoncorcoran0306@gmail.com")).check(matches(withText("jasoncorcoran0306@gmail.com")));
        onView(withId(R.id.password_text)).perform(typeText("password")).check(matches(withText("password")));
        onView(withId(R.id.button_login)).perform(click());
    }

    public void logout(){
        onView(withId(R.id.button_profile)).perform(click());
        onView(withId(R.id.button_logout)).perform(click());

    }



}
