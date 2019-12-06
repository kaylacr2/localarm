package com.example.localarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    private ImageButton notificationQuestion;
    private ImageButton reminderQuestion;
    private LinearLayout notificationSetting;
    private LinearLayout reminderSetting;
    private LinearLayout measurement;
    private LinearLayout alarm;
    private LinearLayout alarmSound;
    private TextView notificationString;
    private TextView reminderString;
    private TextView unitsString;
    private TextView alarmText;
    private TextView alarmSoundText;

    public boolean pushNotifications;
    public String notificationDistance = "100";
    public String reminderDistance = "200";
    public String units = "feet";
    public String alarmType = "Vibrate";
    public String alarmSoundString = "Marimba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        notificationQuestion = findViewById(R.id.questionNotification);
        reminderQuestion = findViewById(R.id.questionReminder);
        notificationSetting = findViewById(R.id.layoutNotificationRange);
        reminderSetting = findViewById(R.id.layoutReminderRange);
        measurement = findViewById(R.id.measurement);
        alarm = findViewById(R.id.alarmIDLayout);
        alarmSound = findViewById(R.id.alarmSoundLayout);
        notificationString = findViewById(R.id.textNotificationRange);
        notificationString.setText(notificationDistance + " " + units);
        reminderString = findViewById(R.id.textReminderRange);
        reminderString.setText(reminderDistance + " " + units);
        unitsString = findViewById(R.id.measurementUnits);
        if (units.equals("feet")) {
            unitsString.setText("Imperial (Miles/Feet)");
        } else if (units.equals("meters")) {
            unitsString.setText("Metric (Meters)");
        }
        alarmText = findViewById(R.id.alarmID);
        alarmText.setText(alarmType);
        alarmSoundText = findViewById(R.id.alarmSound);
        alarmSoundText.setText(alarmSoundString);

        notificationQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setTitle(getResources().getString(R.string.notification_question));
                alertDialogBuilder.setMessage(getResources().getString(R.string.notification_explanation));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        reminderQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setTitle(getResources().getString(R.string.notification_question));
                alertDialogBuilder.setMessage(getResources().getString(R.string.notification_explanation));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        notificationSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setTitle("Set Notification Range");
                final EditText input = new EditText(SettingsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("SET RANGE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String range = input.getText().toString();
                                if (range.isEmpty()) {
                                    Toast.makeText(SettingsActivity.this, "Enter a range or Cancel", Toast.LENGTH_LONG).show();
                                } else {
                                    notificationDistance = range;
                                    notificationString.setText(notificationDistance + " " + units);
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        reminderSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setTitle("Set Reminder Range");
                final EditText input = new EditText(SettingsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("SET RANGE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String range = input.getText().toString();
                                if (range.isEmpty()) {
                                    Toast.makeText(SettingsActivity.this, "Enter a range or Cancel", Toast.LENGTH_LONG).show();
                                } else {
                                    reminderDistance = range;
                                    reminderString.setText(reminderDistance + " " + units);
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setTitle("Change Units");
                final RadioGroup input = new RadioGroup(SettingsActivity.this);
                input.setOrientation(RadioGroup.VERTICAL);

                final RadioButton imperial = new RadioButton(SettingsActivity.this);
                imperial.setText("Imperial (Miles/Feet)");
                imperial.setId(0);
                input.addView(imperial);

                final RadioButton metric = new RadioButton(SettingsActivity.this);
                metric.setText("Metric (Meters)");
                metric.setId(0+1);
                input.addView(metric);

                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("SELECT",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                int selectedId = input.getCheckedRadioButtonId();
                                if (selectedId == 0) {
                                    units = "feet";
                                    unitsString.setText("Imperial (Miles/Feet)");
                                    notificationString.setText(notificationDistance + " " + units);
                                    reminderString.setText(reminderDistance + " " + units);
                                } else if (selectedId == 1) {
                                    units = "meters";
                                    unitsString.setText("Metric (Meters)");
                                    notificationString.setText(notificationDistance + " " + units);
                                    reminderString.setText(reminderDistance + " " + units);
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setTitle("Change Alarm");
                final RadioGroup input = new RadioGroup(SettingsActivity.this);
                input.setOrientation(RadioGroup.VERTICAL);

                final RadioButton silent = new RadioButton(SettingsActivity.this);
                silent.setText("Silent");
                silent.setId(0);
                input.addView(silent);

                final RadioButton vibrate = new RadioButton(SettingsActivity.this);
                vibrate.setText("Vibrate");
                vibrate.setId(0+1);
                input.addView(vibrate);

                final RadioButton sound = new RadioButton(SettingsActivity.this);
                sound.setText("Alarm On");
                sound.setId(0+1+1);
                input.addView(sound);

                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("SELECT",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                int selectedId = input.getCheckedRadioButtonId();
                                if (selectedId == 0) {
                                    alarmType = "Silent";
                                    alarmText.setText(alarmType);
                                } else if (selectedId == 1) {
                                    alarmType = "Vibrate";
                                    alarmText.setText(alarmType);
                                } else if (selectedId == 2) {
                                    alarmType = "Alarm On";
                                    alarmText.setText(alarmType);
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    /**
     * Menu creation.
     * @param menu nav
     * @return bool
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_settings, menu);
        return true;
    }

    /**
     * Menu navigation.
     * @param item selected
     * @return bool
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_map:
                Intent intentMap = new Intent(SettingsActivity.this, MapsActivity.class);
                startActivity(intentMap);
                return true;
            case R.id.menu_list:
                Intent intentList = new Intent(SettingsActivity.this, ListActivity.class);
                startActivity(intentList);
                return true;
            case R.id.menu_account:
                return true;
            case R.id.menu_acct_connct:
                Toast.makeText(this, "Account connected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_acct_disconnct:
                Toast.makeText(this, "Account disconnected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

