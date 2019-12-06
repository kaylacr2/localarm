package com.example.localarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.localarm.data.Task;

import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private Task task;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // initialize
        intent = this.getIntent();
        bundle = intent.getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);

        Button saveButton = findViewById(R.id.save_task);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, ListActivity.class);
                sendTaskInfo(v);
                bundleTask(intent);
                startActivity(intent);
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
        inflater.inflate(R.menu.menu_back, menu);
        return true;
    }

    /**
     * Menu navigation.
     * @param item selected
     * @return bool
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_goback) {
            Intent intent = new Intent(AddActivity.this, ListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Retrieve task information from add task.
     */
    public void sendTaskInfo(View button) {
        final EditText taskTitle = (EditText) findViewById(R.id.task_title);
        String title = taskTitle.getText().toString();

        final EditText taskLocation = (EditText) findViewById(R.id.task_location);
        String location = taskLocation.getText().toString();

        final DatePicker taskDate = (DatePicker) findViewById(R.id.task_date);
        String date = taskDate.getMonth() + 1 + "/" + taskDate.getDayOfMonth() + "/" + taskDate.getYear();

        final EditText taskDescription = (EditText) findViewById(R.id.task_description);
        String description = taskDescription.getText().toString();

        task = new Task(title, location, date, description);
    }

    /**
     * Bundles task information.
     * @param intent intent
     */
    private void bundleTask(Intent intent) {
        bundle.putSerializable("TASK", task);
        intent.putExtras(bundle);
    }
}
