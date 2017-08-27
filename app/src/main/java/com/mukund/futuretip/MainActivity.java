package com.mukund.futuretip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final int RC_SIGN_IN = 1;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ProgressBar progressBar;
    DatePickerDialog.OnDateSetListener dateListener;
    DatePickerDialog datePickerDialog;
    Switch setBirthdaySwitch;
    ChildEventListener childEventListener;
    private String username;
    public static final String ANONYMOUS = "anonymous";
    FirebaseUser currentUser;
    Map<String, Object> userHashMap;
    User user;


  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = ANONYMOUS;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        setBirthdaySwitch = (Switch) findViewById(R.id.setBirthday);


      //Listening to auth events
      authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    onSignedInInitialize(currentUser);
                    Toast.makeText(getApplicationContext(), "User is signed in", Toast.LENGTH_LONG).show();
                    databaseReference = firebaseDatabase.getReference().child("users");
                    user = new User(null, 0, new DailyReading(null), new WeeklyReading(null), new ShortReading(null), new LongReading(null));
                    userHashMap = new HashMap<>();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      if(!dataSnapshot.hasChild(currentUser.getEmail().replace(".", ","))) {
                        userHashMap.put(currentUser.getEmail().replace(".", ","), user);
                        databaseReference.updateChildren(userHashMap);
                      }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                  });

                    databaseReference = firebaseDatabase.getReference().child("users/"+currentUser.getEmail().replace(".", ","));
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("zodiac")){
                          setBirthdaySwitch.setChecked(true);
                        } else{
                          setBirthdaySwitch.setChecked(false);
                        }
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                    });
                  } else {
                  removeAllListeners();
                  startActivityForResult(
                          AuthUI.getInstance()
                                  .createSignInIntentBuilder()
                                  .setAvailableProviders(
                                          Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                  new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                  .setTheme(R.style.LoginTheme)
                                  .setLogo(R.drawable.futuretiplogo)
                                  .build(),
                          RC_SIGN_IN);
              }
            }
        };

      progressBar.setVisibility(View.INVISIBLE);

      //Setting Datepicker
      dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
          final String zodiac = getZodiac(i1+1, i2);
          if(!zodiac.equalsIgnoreCase("NA")&&setBirthdaySwitch.isChecked()){
            setZodiac(zodiac);
          } else{
            datePickerDialog.show();
          }
        }
      };

      datePickerDialog = new DatePickerDialog(this, R.style.MyDialogTheme, dateListener,
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getBirthday(View view) {
      if(setBirthdaySwitch.isChecked()) {
        datePickerDialog.show();
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
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeAllListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if(id==R.id.action_logout){
            Toast.makeText(getApplicationContext(), "User is being logged out", Toast.LENGTH_LONG);
            AuthUI.getInstance().signOut(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
          getSupportFragmentManager().beginTransaction()
            .replace(R.id.frameLayout, new History()).commit();
          progressBar.setVisibility(View.INVISIBLE);


        } else if (id == R.id.nav_manage) {
          getSupportFragmentManager().beginTransaction()
            .replace(R.id.frameLayout, new Settings()).commit();
          progressBar.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "The user is signed in", Toast.LENGTH_LONG);
        } else if (requestCode == RC_SIGN_IN && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "The user is not signed in", Toast.LENGTH_LONG);
            finish();
        }
    }

    public String getZodiac(int month, int day){
      String zodiac = "";
      if((month == 12 && day >= 22 && day <= 31) || (month ==  1 && day >= 1 && day <= 19))
        zodiac = "Capricorn";
      else if ((month ==  1 && day >= 20 && day <= 31) || (month ==  2 && day >= 1 && day <= 17))
        zodiac = "Aquarius";
      else if ((month ==  2 && day >= 18 && day <= 29) || (month ==  3 && day >= 1 && day <= 19))
        zodiac = "Pisces";
      else if ((month ==  3 && day >= 20 && day <= 31) || (month ==  4 && day >= 1 && day <= 19))
        zodiac = "Aries";
      else if ((month ==  4 && day >= 20 && day <= 30) || (month ==  5 && day >= 1 && day <= 20))
        zodiac = "Taurus";
      else if ((month ==  5 && day >= 21 && day <= 31) || (month ==  6 && day >= 1 && day <= 20))
        zodiac = "Gemini";
      else if ((month ==  6 && day >= 21 && day <= 30) || (month ==  7 && day >= 1 && day <= 22))
        zodiac = "Cancer";
      else if ((month ==  7 && day >= 23 && day <= 31) || (month ==  8 && day >= 1 && day <= 22))
        zodiac = "Leo";
      else if ((month ==  8 && day >= 23 && day <= 31) || (month ==  9 && day >= 1 && day <= 22))
        zodiac = "Virgo";
      else if ((month ==  9 && day >= 23 && day <= 30) || (month == 10 && day >= 1 && day <= 22))
        zodiac = "Libra";
      else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 1 && day <= 21))
        zodiac = "Scorpio";
      else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day >= 1 && day <= 21))
        zodiac = "Sagittarius";
      else
        zodiac = "NA";
      return zodiac;
    }

    public void setZodiac(final String zodiac){
      databaseReference = firebaseDatabase.getReference().child("users");
      user.setZodiac(zodiac);
      userHashMap.put(currentUser.getEmail().replace(".", ","), user);
      databaseReference.updateChildren(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          Toast.makeText(getApplicationContext(),
            "Hello: "+zodiac+"\n You'll now recieve daily personalized tarot readings",
            Toast.LENGTH_LONG).show();
        }
      });
    }

    public void removeAllListeners(){
      firebaseAuth.removeAuthStateListener(authStateListener);
      if(childEventListener!=null){
        databaseReference.removeEventListener(childEventListener);
      }
      childEventListener =null;
      username = ANONYMOUS;
    }


  public void onSignedInInitialize(FirebaseUser user){
    username = user.getDisplayName();
    }

}
