package com.mukund.futuretip;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Settings extends Fragment {

  EditText problem;
  EditText topConcern;
  EditText subject;
  Button sendShortReadingRequest;
  DatabaseReference databaseReference;
  FirebaseUser firebaseUser;
  FirebaseDatabase firebaseDatabase;
  HashMap<String, Object> readingHashMap;



  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      //returning our layout file
      //change R.layout.yourlayoutfilename for each of your fragments
      return inflater.inflate(R.layout.fragment_settings, container, false);
  }

    public void sendShortReadingRequest(View view){

      String problemInfo = problem.getText().toString();
      String topConcernInfo = topConcern.getText().toString();
      String subjectInfo = subject.getText().toString();

      //Saving reading request
      MainActivity mainActivity = new MainActivity();
      firebaseDatabase = FirebaseDatabase.getInstance();
      firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      databaseReference = firebaseDatabase.getReference().child("reading");
      Reading reading = new Reading(
        subjectInfo,
        topConcernInfo,
        problemInfo,
        null,
        "short",
        firebaseUser.getEmail().replace(".", ","),
        new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
      readingHashMap.clear();
      readingHashMap.put(databaseReference.push().getKey(), reading);
      databaseReference.updateChildren(readingHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          Toast.makeText(getContext(),
            "Reading request submitted successfully",
            Toast.LENGTH_LONG).show();
        }
      });
    }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    problem = (EditText) view.findViewById(R.id.problem);
    topConcern = (EditText) view.findViewById(R.id.topConcern);
    subject = (EditText) view.findViewById(R.id.subject);
    sendShortReadingRequest = (Button) view.findViewById(R.id.sendShortReadingRequest);
    readingHashMap = new HashMap<>();

    sendShortReadingRequest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sendShortReadingRequest(view);
      }
    });
  }
}
