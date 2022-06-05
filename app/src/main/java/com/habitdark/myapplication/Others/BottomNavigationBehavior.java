package com.habitdark.myapplication.Others;

/**
 * Created by kapil on 05/10/18.
 */

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        boolean dependsOn = dependency instanceof FrameLayout;
        return dependsOn;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       BottomNavigationView child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  BottomNavigationView child, View target, int dx, int dy, int[] consumed) {
        if (dy < 0) {
            showBottomNavigationView(child);
        } else if (dy > 0) {
            hideBottomNavigationView(child);
        }
    }

    private void hideBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(view.getHeight());
    }

    private void showBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(0);
    }

    public static class Database {
        public static SQLiteDatabase initDatabase(Activity activity, String databaseName) {
            try {
                String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + databaseName;
                File f = new File(outFileName);
                if(!f.exists()) {
                    InputStream e = activity.getAssets().open(databaseName);
                    File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    FileOutputStream myOutput = new FileOutputStream(outFileName);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = e.read(buffer)) > 0) {
                        myOutput.write(buffer, 0, length);
                    }
                    myOutput.flush();
                    myOutput.close();
                    e.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
        }
    }
}