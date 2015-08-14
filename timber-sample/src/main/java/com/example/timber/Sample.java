package com.example.timber;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import timber.log.Timber;

public class Sample extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Timber.plant(new Timber.DebugTree());
    Log.d("Tag", "Message");
  }
}
