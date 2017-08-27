package com.mukund.futuretip;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class History extends Fragment {

  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //returning our layout file
    //change R.layout.yourlayoutfilename for each of your fragments
    return inflater.inflate(R.layout.fragment_history, container, false);
  }

}
