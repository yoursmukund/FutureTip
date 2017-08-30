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
import android.widget.EditText;
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
    Switch setDailySwitch;
    Switch setWeeklySwitch;
    ChildEventListener childEventListener;
    private String username;
    public static final String ANONYMOUS = "anonymous";
    FirebaseUser currentUser;
    Map<String, Object> userHashMap;
    Map<String, Map<String, String>>  subscriptionHashMap;
    Map<String, String> keyAndUserHashMap;
    String subscriptionKey;
    User user;
    String zodiacToBeSearched;
    Boolean dailySubscriptionPresent;

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
        setDailySwitch = (Switch) findViewById(R.id.setBirthday);
//        setWeeklySwitch = (Switch) findViewById(R.id.setWeeklyReading);


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
                    subscriptionHashMap = new HashMap<>();
                    keyAndUserHashMap = new HashMap<>();

                    //Adding user to users object
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      if(!dataSnapshot.hasChild(currentUser.getEmail().replace(".", ","))) {
                        userHashMap.clear();
                        userHashMap.put(currentUser.getEmail().replace(".", ","), user);
                        databaseReference.updateChildren(userHashMap);
                      }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                  });

                    //Setting daily subscription state
                    databaseReference = firebaseDatabase.getReference().child("users/"+currentUser.getEmail().replace(".", ","));
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("zodiac")){
                          setDailySwitch.setChecked(true);
                        } else{
                          setDailySwitch.setChecked(false);
                        }
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                    });

                  //Sign up flow
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
          if(!zodiac.equalsIgnoreCase("NA")&&setDailySwitch.isChecked()){
            setZodiac(zodiac);
          } else if (!setDailySwitch.isChecked()){

          }
        }
      };

      datePickerDialog = new DatePickerDialog(this, R.style.MyDialogTheme, dateListener,
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    //Get birth date for zodiac
    public void getBirthday(View view) {
      if(setDailySwitch.isChecked()) {
        datePickerDialog.show();
      } else{
        databaseReference = firebaseDatabase.getReference().child("users/"+currentUser.getEmail().replace(".", ","));
        databaseReference.child("zodiac").addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

            //Setting zodiac to null
            zodiacToBeSearched = dataSnapshot.getValue().toString();
            databaseReference.child("zodiac").setValue(null);

            //Setting subscription to null
            databaseReference = firebaseDatabase.getReference().child("subscriptions/"+zodiacToBeSearched);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot subscriptionKey: dataSnapshot.getChildren()){
                  if(subscriptionKey.getValue().toString().equalsIgnoreCase(currentUser.getEmail().replace(".", ","))){
                    databaseReference.child(subscriptionKey.getKey()).setValue(null);
                  }
                }
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
            });
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
        });

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

    //Get zodiac for particular birth date
    public String getZodiac(int month, int day){
      String zodiac = "";
      if((month == 12 && day >= 22 && day <= 31) || (month ==  1 && day >= 1 && day <= 19))
        zodiac = "capricorn";
      else if ((month ==  1 && day >= 20 && day <= 31) || (month ==  2 && day >= 1 && day <= 17))
        zodiac = "aquarius";
      else if ((month ==  2 && day >= 18 && day <= 29) || (month ==  3 && day >= 1 && day <= 19))
        zodiac = "pisces";
      else if ((month ==  3 && day >= 20 && day <= 31) || (month ==  4 && day >= 1 && day <= 19))
        zodiac = "aries";
      else if ((month ==  4 && day >= 20 && day <= 30) || (month ==  5 && day >= 1 && day <= 20))
        zodiac = "taurus";
      else if ((month ==  5 && day >= 21 && day <= 31) || (month ==  6 && day >= 1 && day <= 20))
        zodiac = "gemini";
      else if ((month ==  6 && day >= 21 && day <= 30) || (month ==  7 && day >= 1 && day <= 22))
        zodiac = "cancer";
      else if ((month ==  7 && day >= 23 && day <= 31) || (month ==  8 && day >= 1 && day <= 22))
        zodiac = "leo";
      else if ((month ==  8 && day >= 23 && day <= 31) || (month ==  9 && day >= 1 && day <= 22))
        zodiac = "virgo";
      else if ((month ==  9 && day >= 23 && day <= 30) || (month == 10 && day >= 1 && day <= 22))
        zodiac = "libra";
      else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 1 && day <= 21))
        zodiac = "scorpio";
      else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day >= 1 && day <= 21))
        zodiac = "sagittarius";
      else
        zodiac = "NA";
      return zodiac;
    }


    //Set zodiac inside users object
    public void setZodiac(final String zodiac){
      databaseReference = firebaseDatabase.getReference().child("users");
      user.setZodiac(zodiac);
      userHashMap.clear();
      userHashMap.put(currentUser.getEmail().replace(".", ","), user);
      databaseReference.updateChildren(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          Toast.makeText(getApplicationContext(),
            "Hello: "+zodiac+"\n You'll now recieve daily personalized tarot readings",
            Toast.LENGTH_LONG).show();
        }
      });

      //Saving daily subscription
      databaseReference = firebaseDatabase.getReference().child("subscriptions/"+zodiac);
      subscriptionKey = databaseReference.push().getKey();
      dailySubscriptionPresent = false;
      databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for(DataSnapshot data: dataSnapshot.getChildren()){
            if(dataSnapshot.getValue()==currentUser.getEmail().replace(".", ",")){
              dailySubscriptionPresent = true;
            }
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });

      if(!dailySubscriptionPresent){
        keyAndUserHashMap.clear();
        databaseReference.child(subscriptionKey).setValue(currentUser.getEmail().replace(".", ","));
      }
    }

    //Removing all listeners before signup
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

  public void addShortReading(View view){

    //Navigating to short reading fragment
    getSupportFragmentManager().beginTransaction()
      .replace(R.id.frameLayout, new Settings()).commit();
    progressBar.setVisibility(View.INVISIBLE);
  }

  public FirebaseDatabase getFirebaseDatabase() {
    return firebaseDatabase;
  }

  public DatabaseReference getDatabaseReference() {
    return databaseReference;
  }

  public FirebaseAuth getFirebaseAuth() {
    return firebaseAuth;
  }
}
