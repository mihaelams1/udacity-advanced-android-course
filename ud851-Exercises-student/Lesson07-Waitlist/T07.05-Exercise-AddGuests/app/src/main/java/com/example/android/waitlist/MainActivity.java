package com.example.android.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.android.waitlist.data.TestUtil;
import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistDbHelper;

import static com.example.android.waitlist.data.WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME;
import static com.example.android.waitlist.data.WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE;
import static com.example.android.waitlist.data.WaitlistContract.WaitlistEntry.TABLE_NAME;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;

    private EditText mNewGuestNameEditText;
    private EditText mNewPartySizeEditText;

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        // Set local attributes to corresponding views
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        mNewGuestNameEditText = (EditText) findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText = (EditText) findViewById(R.id.party_count_edit_text);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Create a DB helper (this will create the DB if run for the first time)
        WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        mDb = dbHelper.getWritableDatabase();

        // Get all guest info from the database and save in a cursor
        Cursor cursor = getAllGuests();

        // Create an adapter for that cursor to display the data
        mAdapter = new GuestListAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);

    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {

        if (mNewGuestNameEditText.getText() == null || mNewGuestNameEditText.getText().length() == 0
                || mNewPartySizeEditText.getText() == null || mNewPartySizeEditText.getText().length() == 0) {
            return;
        }

        int partySize = 1;
        try {
            partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }

        addGuest(mNewGuestNameEditText.getText().toString(), partySize);

        mAdapter.swapCursor(getAllGuests());

        mNewGuestNameEditText.getText().clear();
        mNewPartySizeEditText.getText().clear();
        mNewPartySizeEditText.clearFocus();
    }

    /**
     * Query the mDb and get all guests from the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    private Cursor getAllGuests() {
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    private void addGuest(String name, int size) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GUEST_NAME, name);
        contentValues.put(COLUMN_PARTY_SIZE, size);

        mDb.insert(TABLE_NAME, null, contentValues);
    }
}