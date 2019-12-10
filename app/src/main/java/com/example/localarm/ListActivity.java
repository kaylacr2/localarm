package com.example.localarm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.localarm.data.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private int SortType;
    private boolean details;

    ArrayList<ArrayList<String>> items = new ArrayList<>();                                         // newly created items should be stored into here

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // initialize
        intent = this.getIntent();
        bundle = intent.getExtras();

        // Add new task if applicable
        addTask();
        // Load previous tasks
        loadTasks();

        SortType = 0;
        details = false;

        // Load info if applicable
        loadInfo();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, AddActivity.class);
                bundleTasks(intent);
                startActivity(intent);
                finish(); // end
            }
        });

        // Richard's stuff [redacted]

        final LinearLayout list = findViewById(R.id.list);

        Spinner mySpinner = (Spinner) findViewById(R.id.SortSpinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(ListActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.SortStyles));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);

        // rebuild when changed, prevent from duplicating
        mySpinner.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                rebuild();
            }
        });

        // rebuild when show or hide details
        Switch deets = (Switch) findViewById(R.id.show_details);
        deets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                details = isChecked;
                rebuild();
            }
        });

        //CreateList(list);
        rebuild();
    }

    /**
     * Load sort and display info from previous state.
     */
    protected void loadInfo() {
        if (bundle != null && bundle.containsKey("SORT")) {
            this.SortType = (Integer) bundle.getSerializable("SORT");
        }

        if (bundle != null && bundle.containsKey("DEETS")) {
            this.details = (Boolean) bundle.getSerializable("DEETS");
        }
    }

    /**
     * Load tasks from previous state.
     */
    protected void loadTasks() {
        if (bundle != null && bundle.containsKey("TASK0")) {
            int itemct = 0;
            String itemName = "TASK" + itemct;

            while (bundle.containsKey(itemName)) {
                ArrayList<String> item = (ArrayList<String>) bundle.getSerializable(itemName);
                items.add(item);

                itemct++;
                itemName = "TASK" + itemct;
            }
        }
    }

    /**
     * Add task item to list.
     */
    protected void addTask() {
        if (bundle != null && bundle.containsKey("TASK")) {
            Task task = (Task) bundle.getSerializable("TASK");
            items.add(task.getInformation());
        }
    }

    /**
     * Bundles task and organization information.
     * @param intent intent
     */
    private void bundleTasks(Intent intent) {
        Bundle b = new Bundle();
        int itemct = 0;
        for (ArrayList<String> item : items) {
            String itemName = "TASK" + itemct;
            b.putStringArrayList(itemName, item);
            itemct++;
        }
        b.putSerializable("SORT", SortType);
        b.putSerializable("DEETS", details);
        intent.putExtras(b);
    }

    /**
     * Menu creation.
     * @param menu nav
     * @return bool
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_list, menu);
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
                Intent intentMap = new Intent(ListActivity.this, MapsActivity.class);
                bundleTasks(intentMap);
                startActivity(intentMap);
                finish(); // end
                return true;
            case R.id.menu_settings:
                Intent intentSet = new Intent(ListActivity.this, SettingsActivity.class);
                bundleTasks(intentSet);
                startActivity(intentSet);
                finish(); // end
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sortOption = parent.getItemAtPosition(position).toString();

        if (sortOption.charAt(0) == 'T') { // time
            SortType = 0;
            rebuild();
        } else if (sortOption.charAt(0) == 'L') { // location
            SortType = 1;
            rebuild();
        } else { // name
            SortType = 2;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void rebuild() {
        final LinearLayout list = findViewById(R.id.list);
        list.removeAllViews();
        SortTasks();
        CreateList(list);
    }

    /**
     * List view function.
     * @param list layout
     */
    protected void CreateList(LinearLayout list) {                                               // fetching the linear layout list
        ArrayList<LinearLayout> tasks = new ArrayList<>();                                          // creating an arraylist of linear layouts to hold each all the tasks

        for (int i = 0; i < items.size(); i++) {                                                    // iterate over items to turn each into a task
            LinearLayout task = new LinearLayout(this);                                      // creating a new linear layout for each task
            task.setOrientation(LinearLayout.VERTICAL);

            task.setId(task.hashCode()); // unique ID

            LinearLayout.LayoutParams taskparams = new LinearLayout.LayoutParams(                   // making it the width of the screen, and wrap the text inside
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            taskparams.setMargins(5,10,5,0);                                  // making the tasks not squashed together and against the walls

            task.setLayoutParams(taskparams);                                                       // applying params
            task.setBackgroundResource(R.drawable.ic_border);                                    // adding a border to the different tasks

            int end = items.get(i).size();

            if (!details) {
                end -= 1;
            }

            for (int j = 0; j < end; j++) {                                         // move through each part of the task adding it to the layout
                TextView textView = new TextView(this);
                if (j < items.get(i).size() - 1) {
                    textView.setText(items.get(i).get(j));
                } else {
                    if (items.get(i).get(j).length() > 30) {
                        textView.setText(items.get(i).get(j).substring(0,27) + "...");
                    } else {
                        textView.setText(items.get(i).get(j));
                    }
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                if (j == 0) {
                    textView.setTextSize((float)18.5);
                    textView.setTextColor(Color.BLACK);
                } else {
                    textView.setTextSize((float)13.5);
                }
                textView.setLayoutParams(params);
                task.addView(textView);                                                             // add the text to the view
            }

            tasks.add(task);                                                                        // add the task to the layout
        }

        for (int i = 0; i < tasks.size(); i ++) {
            list.addView(tasks.get(i));
        }
    }

    /**
     * Task sorting function.
     */
    protected void SortTasks(){
        if (SortType == 0) {
            // filling unsorted map
            HashMap<ArrayList<String>, Long> unsorted = new HashMap<>();

            // today's date as starting point
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();
            
            for (ArrayList<String> item : items) {
                String[] parts = item.get(2).split("/");
                calendar.set(Integer.parseInt(parts[2]), Integer.parseInt(parts[0]) - 1, Integer.parseInt(parts[1]), 0, 0);

                // date related to item
                Date itemday = calendar.getTime();

                // map item to days away from current date
                long diff = itemday.getTime() - today.getTime();
                unsorted.put(item, TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            }

            // custom comparator
            Comparator<Map.Entry<ArrayList<String>, Long>> comparator = new Comparator<Map.Entry<ArrayList<String>, Long>>() {
                @Override
                public int compare(Map.Entry<ArrayList<String>, Long> e1, Map.Entry<ArrayList<String>, Long> e2) {
                    long v1 = e1.getValue();
                    long v2 = e2.getValue();
                    return (int) (v1 - v2);
                }
            };

            // convert
            Set<Map.Entry<ArrayList<String>, Long>> entries = unsorted.entrySet();
            ArrayList<Map.Entry<ArrayList<String>, Long>> sorted = new ArrayList<Map.Entry<ArrayList<String>, Long>>(entries);

            // sorting HashMap by values using comparator
            Collections.sort(sorted, comparator);

            // clear and put back sorted
            items.clear();
            for (Map.Entry<ArrayList<String>, Long> item : sorted) {
                items.add(item.getKey());
            }
        } else if (SortType == 1) {
            int n = items.size();

            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    String A = items.get(j).get(SortType);
                    String B = items.get(j + 1).get(SortType);

                    int k = 0;
                    while (A.charAt(k) == B.charAt(k)) {
                        k++;
                    }

                    if (k >= A.length() || k >= B.length()) {
                        k = 0;
                    }

                    if ((int) A.charAt(k) > (int) B.charAt(k)) {
                        Collections.swap(items, j, j + 1);
                    }
                }
            }
        }
    }

}
