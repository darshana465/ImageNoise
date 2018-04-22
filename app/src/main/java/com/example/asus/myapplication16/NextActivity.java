package com.example.asus.myapplication16;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.asus.myapplication16.MainActivity;
import java.util.Arrays;

public class NextActivity extends Activity {
    ImageButton output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        Intent intent=new Intent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        output.setImageBitmap(bitmap);
    }

}
