package com.example.localarm;

import androidx.annotation.NonNull;
import android.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // initialize
        intent = this.getIntent();
        bundle = intent.getExtras();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.toolbar_map_view);
        setActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_map);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd = new Intent(MapsActivity.this, AddActivity.class);
                intentAdd.putExtras(bundle);
                startActivity(intentAdd);
                finish(); // end
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng champaign = new LatLng(40.1097743, -88.2308296);
        mMap.addMarker(new MarkerOptions().position(champaign).title("Marker in Champaign"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(champaign));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    /**
     * Menu creation.
     * @param menu nav
     * @return bool
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_map, menu);
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
            case R.id.menu_list:
                Intent intentMap = new Intent(MapsActivity.this, ListActivity.class);
                intentMap.putExtras(bundle);
                startActivity(intentMap);
                finish(); // end
                return true;
            case R.id.menu_settings:
                Intent intentSet = new Intent(MapsActivity.this, SettingsActivity.class);
                intentSet.putExtras(bundle);
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
}
