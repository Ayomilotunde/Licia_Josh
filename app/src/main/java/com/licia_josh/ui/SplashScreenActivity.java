package com.licia_josh.ui;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.licia_josh.MainActivity;
import com.licia_josh.R;
import com.licia_josh.auths.SignUpActivity;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView imageView;

    SharedPreferences mSharedPreferences;

    private long ms=0, splashT=750;
    private boolean splashActive = true , paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setStatusBarColor(R.color.white);

        final ConstraintLayout rl = findViewById(R.id.Rl);
        Thread thread = new Thread() {
            public void run() {
                try {
                    while (splashActive&&ms<splashT) {
                        if (!paused)
                            ms=ms+100;
                        sleep(100);
                    }
                } catch (Exception e) {

                } finally {
                    if (!isOnline()) {
                        Snackbar snackbar = Snackbar.make(rl,"No Network", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recreate();
                            }
                        });
                        snackbar.show();
                    } else {
                        goMain();
                    }
                }
            }
        };
        thread.start();

    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void setStatusBarColor(@ColorRes int statusBarColor) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            int color = ContextCompat.getColor(this, statusBarColor);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }



    private void goMain() {
        mSharedPreferences = getSharedPreferences("SliderScreen", MODE_PRIVATE);

        boolean isFirstTimer = mSharedPreferences.getBoolean("firstTime", true);

        if (isFirstTimer){

            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

}