package com.instigatemobile.grapes.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.instigatemobile.grapes.models.ActiveNodesModel;
import com.instigatemobile.grapes.adapters.ActiveNodesAdapter;
import com.instigatemobile.grapes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveNodesFragment extends Fragment {

    private List<ActiveNodesModel> mNodes = new LinkedList<>();


    public ActiveNodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_nodes, container, false);
        getActivity().setTitle("Active Nodes");
        LinearLayout background = view.findViewById(R.id.nodeBackground);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            background.setBackgroundResource(R.drawable.backgroundgrapeschange);
        } else {
            background.setBackgroundResource(R.drawable.backgroundgrapes);
        }
        fillList();
        RecyclerView recyclerView = view.findViewById(R.id.nodes);
        ActiveNodesAdapter myAdapter = new ActiveNodesAdapter(mNodes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);


        // Inflate the layout for this fragment
        return view;
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("jsonfile.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<String> jsonToArray() {

        ArrayList<String> listdata = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(loadJSONFromAsset());
            listdata = new ArrayList<String>();
            JSONArray jArray = null;
            jArray = jsonObject.getJSONArray("activNodes");
            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    listdata.add(jArray.getString(i));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listdata;
    }

    private void fillList() {
        ArrayList<String> node = jsonToArray();
        //ActiveNodesModel shablon = new ActiveNodesModel(null, null);
        for (int i = 0; i < node.size(); ++i) {
            ActiveNodesModel shablon = new ActiveNodesModel(null, null);
            String jsonObject = node.get(i);
            try {
                JSONObject json = new JSONObject(jsonObject);
                shablon.setmNickname(json.getString("name"));
                shablon.setmIp(json.getString("ip"));
                mNodes.add(shablon);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
