package com.example.farmy

import com.google.common.base.Verify.verify;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import junit.framework.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.farmy.MainActivity
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private var MainActivity mainActivity;
    private var EditText editTextName;
    private var Spinner spinnerGenre;
    private var ListView listViewArtists;
    private var Button buttonAddArtist;
    private var List<Artist> artists;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            mainActivity = activity;
            editTextName = activity.findViewById(R.id.editTextName);
            spinnerGenre = activity.findViewById(R.id.spinnerGenres);
            listViewArtists = activity.findViewById(R.id.listViewArtists);
            buttonAddArtist = activity.findViewById(R.id.buttonAddArtist);
        });
    }

    @Test
    public void testMainActivityNotNull() {
        assertNotNull(mainActivity);
    }

    @Test
    public void testAddArtist() {
        String name = "Test Artist";
        String genre = "Test Genre";

        // Mock the DatabaseReference and FirebaseDatabase
        DatabaseReference mockedDatabaseReference = mock (DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = mock (FirebaseDatabase.class);

        // Set the mockedDatabaseReference to return the mockedFirebaseDatabase
        FirebaseDatabase.getInstance().thenReturn(mockedFirebaseDatabase);

        // Set the databaseArtists to the mockedDatabaseReference
        mainActivity.databaseArtists = mockedDatabaseReference;

        // Call the addArtist method with the name and genre
        mainActivity.editTextName.setText(name);
        mainActivity.spinnerGenre.setSelection(0); // assuming first item is "Test Genre"
        mainActivity.addArtist();

        // Verify that the setValue method of the mockedDatabaseReference was called with the expected Artist object
        Artist expectedArtist = new Artist("1", name, genre);
        verify(mockedDatabaseReference).child("1").setValue(expectedArtist);
    }
}
