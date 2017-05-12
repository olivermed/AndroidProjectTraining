package com.example.raphifou.find;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.raphifou.find.Fragment.ContactFragment;
import com.example.raphifou.find.GPS.GPSTracker;
import com.example.raphifou.find.Retrofit.ApiBackend;
import com.example.raphifou.find.Retrofit.BackEndApiService;
import com.example.raphifou.find.Retrofit.mainResponseObject;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 0;
    NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);

        String token = sharedPref.getString(getString(R.string.token), null);
        if (token == null) {
            Intent intent = new Intent(this, Loginactivity.class);
            startActivity(intent);
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_LOCATION);
        }

        if(savedInstanceState == null) {
            Fragment home = Home.newInstance(null, null);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, home, home.getTag())
                    .addToBackStack(home.getTag())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        TextView txtUserName = (TextView) headerLayout.findViewById(R.id.txtUserName);
        txtUserName.setText(sharedPref.getString(getString(R.string.login), null));

        if (FirebaseInstanceId.getInstance().getToken() != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.idFcm), FirebaseInstanceId.getInstance().getToken());
            editor.commit();

            Log.w(getPackageName(), "SharedPref added idFcm :: " + sharedPref.getString(getString(R.string.idFcm), null));

            Log.w(this.getPackageName(), FirebaseInstanceId.getInstance().getToken());
            BackEndApiService service = ApiBackend.getClient().create(BackEndApiService.class);
            Call<mainResponseObject> call = service.addIdFcm(token, sharedPref.getString(getString(R.string.id), null), FirebaseInstanceId.getInstance().getToken());
            call.enqueue(new Callback<mainResponseObject>() {
                @Override
                public void onResponse(Call<mainResponseObject> call, Response<mainResponseObject> response) {
                    Log.w(getPackageName(), response.toString());
                }

                @Override
                public void onFailure(Call<mainResponseObject> call, Throwable t) {
                    Log.e(getPackageName(), t.toString());
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    GPSTracker gps = new GPSTracker(this);
                    if (gps.canGetLocation()){
                        gps.getLatitude(); // returns latitude
                        gps.getLongitude();
                    }
                    gps.stopUsingGPS();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contact) {
            setFragment(new ContactFragment(), ContactFragment.Tag);
        } else if (id == R.id.nav_home) {
            setFragment(new Home(), Home.Tag);
        } else if (id == R.id.nav_disconnect) {
            Intent intent = new Intent(this, Loginactivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(Fragment fragment, String Tag) {
        int entry = getSupportFragmentManager().getBackStackEntryCount();
        String fragmentName = null;

        if (entry > 0) {
            fragmentName = getSupportFragmentManager().getBackStackEntryAt(entry - 1).getName();
            if (fragmentName != Tag) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, Tag)
                        .addToBackStack(Tag)
                        .commit();
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, Tag)
                    .addToBackStack(Tag)
                    .commit();
        }
        System.out.println("Fragment name :: " + fragmentName);
        Log.w("Number of entry::", "" + entry);
    }

    public void setAppBarMenu(int id) {
        navigationView.setCheckedItem(id);
    }
}
