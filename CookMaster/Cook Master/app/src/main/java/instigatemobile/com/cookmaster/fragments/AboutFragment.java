package instigatemobile.com.cookmaster.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.adapters.MembersAdapter;
import instigatemobile.com.cookmaster.models.TeamMember;

public class AboutFragment extends Fragment {
    private List<TeamMember> memberList = new LinkedList<>();

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.about_page_title));
        fillMemberList();
        final View view = inflater.inflate(R.layout.fragment_about, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.about_page_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        final MembersAdapter adapter = new MembersAdapter(memberList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void fillMemberList() {
        TeamMember member;
        member = new TeamMember(R.drawable.stepan, R.string.stepan);
        memberList.add(member);
        member = new TeamMember(R.drawable.vahan, R.string.vahan);
        memberList.add(member);
        member = new TeamMember(R.drawable.samvel, R.string.samvel);
        memberList.add(member);
        member = new TeamMember(R.drawable.mikayel, R.string.mikayel);
        memberList.add(member);
        member = new TeamMember(R.drawable.elya, R.string.elya);
        memberList.add(member);
        member = new TeamMember(R.drawable.smbat, R.string.smbat);
        memberList.add(member);
    }
}
