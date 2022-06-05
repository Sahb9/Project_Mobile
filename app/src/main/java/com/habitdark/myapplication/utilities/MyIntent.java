package com.habitdark.myapplication.utilities;

import android.content.Context;
import android.content.Intent;

public class MyIntent {
    private static Intent intent;

    private MyIntent() {}

    public static Intent getMyIntent(Context context, Class<?> cls) {
        if (intent == null) {
            intent = new Intent(context, cls);
        }

        return intent;
    }

    public static Intent getMyIntent() {
        return intent;
    }
}
