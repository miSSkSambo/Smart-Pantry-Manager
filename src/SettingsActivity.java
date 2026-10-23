package com.example.smartpantry;
import android.os.Bundle;import android.widget.Switch;import androidx.appcompat.app.AppCompatActivity;import com.example.smartpantry.data.DatabaseHelper;
public class SettingsActivity extends AppCompatActivity{@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_settings);DatabaseHelper db=new DatabaseHelper(this);Switch s=findViewById(R.id.expirySwitch);s.setChecked(true);s.setOnCheckedChangeListener((button,checked)->{});}}
