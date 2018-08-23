package instigatemobile.com.cookmaster.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.adapters.AdapterOfTodayMenu;
import instigatemobile.com.cookmaster.adapters.ConsultantAdapter;
import instigatemobile.com.cookmaster.models.ConsultantModel;
import instigatemobile.com.cookmaster.models.TodayMenuItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentConsultantsFragment extends Fragment {
    private List<ConsultantModel> mConsultantsList;
    private Context mContext;
    private DatabaseReference refConsultants = FirebaseDatabase.getInstance().getReference();

    public CurrentConsultantsFragment() {
    }

    @SuppressLint("ValidFragment")
    public CurrentConsultantsFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.current_consultants_page_title));
        View view = inflater.inflate(R.layout.fragment_current_consultants, container, false);
//        addConsultants();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mConsultantsList = new ArrayList<>();
        final RecyclerView recyclerView = view.findViewById(R.id.consultants_rv);
        recyclerView.setLayoutManager(layoutManager);
        refConsultants.child("consultants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    ConsultantModel consultantModel = i.getValue(ConsultantModel.class);
                    mConsultantsList.add(consultantModel);
                }
                ConsultantAdapter adapter = new ConsultantAdapter(mConsultantsList, mContext);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return view;
    }

    private void addConsultants() {
        refConsultants.child("consultants").child("first").setValue(new
                ConsultantModel("Name Surname", "first@gmail.com", "7777777777",
                "https://firebasestorage.googleapis.com/v0/b/cookmaster-13adb.appspot.com/o/36013d34a96136133cfc6fe4dd3cb269.png?alt=media&token=78ebb1ff-01de-45aa-a310-93431f90f91d"));
        refConsultants.child("consultants").child("second").setValue(new
                ConsultantModel("Surname Name", "second@gmail.com", "1111111111",
                "https://firebasestorage.googleapis.com/v0/b/cookmaster-13adb.appspot.com/o/36013d34a96136133cfc6fe4dd3cb269.png?alt=media&token=78ebb1ff-01de-45aa-a310-93431f90f91d"));
    }
}