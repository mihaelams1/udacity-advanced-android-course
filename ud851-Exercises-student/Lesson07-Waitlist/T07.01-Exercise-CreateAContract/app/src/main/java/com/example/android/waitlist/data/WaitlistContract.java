package com.example.android.waitlist.data;

import android.provider.BaseColumns;

public class WaitlistContract {

    static final class WaitlistEntry implements BaseColumns {
        static final String TABLE_NAME = "waitlist";
        static final String COLUMN_GUEST_NAME = "guestName";
        static final String COLUMN_PARTY_SIZE = "partySize";
        static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
