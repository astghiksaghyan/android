package instigatemobile.com.cookmaster.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.models.TeamMember;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder>{
    private List<TeamMember> members;
    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_member, parent, false);
        return new MembersViewHolder(view);
    }

    public MembersAdapter(List<TeamMember> members) {
        this.members = members;
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder holder, int position) {
        final TeamMember member = members.get(position);
        holder.textView.setText(member.getName());
        holder.imageView.setImageResource(member.getImage());
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class MembersViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imageView;
        private TextView textView;
        public MembersViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.member_image);
            textView = itemView.findViewById(R.id.member_name);
        }
    }
}