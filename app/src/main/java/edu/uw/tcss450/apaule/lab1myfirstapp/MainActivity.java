package edu.uw.tcss450.apaule.lab1myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate debug log");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart info log");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume warning log");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause verbose log");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop error log");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy debug log");
    }
}