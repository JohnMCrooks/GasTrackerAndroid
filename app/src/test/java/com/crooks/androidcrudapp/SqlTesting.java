package com.crooks.androidcrudapp;

import android.content.Context;
import android.test.RenamingDelegatingContext;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.model.TestClass;

/**
 * Created by johncrooks on 10/18/16.
 */
@RunWith(AndroidJUnit4.class)
public class SqlTesting extends ActivityInstrumentationTestCase2<TableControllerFillUp>{
    private DbHandler mDB;

    public SqlTesting(Class<TableControllerFillUp> activityClass) {
        super(activityClass);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        mDB = new DbHandler(context);
    }

    @After
    public void tearDown() throws Exception {
        mDB.close();
        super.tearDown();
    }

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
