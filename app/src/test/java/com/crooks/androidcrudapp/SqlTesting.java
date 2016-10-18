package com.crooks.androidcrudapp;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import org.junit.Test;
import org.junit.Assert.*;

/**
 * Created by johncrooks on 10/18/16.
 */

public class SqlTesting extends AndroidTestCase {


    Context mContext = new MockContext();

    @Test
    public void testInitialAlwaysPasses(){
        assert true;
    }

    @Test
    public void testdbDrop (){
        // TODO: 10/18/16 Write some unit tests to help determine SQL issue editing/updating db.

        assertTrue(mContext.deleteDatabase(DbHandler.DATABASE_NAME));

//        boolean createSuccessful = new TableControllerFillUp(context).createRecord(newTank);
    }
}
