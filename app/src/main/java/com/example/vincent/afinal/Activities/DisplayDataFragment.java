package com.example.vincent.afinal.Activities;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.vincent.afinal.Database.UserMethods;
import com.example.vincent.afinal.Objects.User;

import java.util.List;

/**
 * Created by Vincent on 23.11.2016.
 */

//Classe pas utilisée dans la version final

public class DisplayDataFragment extends ListFragment {

    UserMethods userMethods;
    List<User> listUsers;
    ArrayAdapter<User> adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        userMethods = new UserMethods(getActivity());
        listUsers = userMethods.getAllUsers();

        adapter = new ArrayAdapter<User>(getActivity(),android.R.layout.simple_list_item_1,listUsers);

        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
