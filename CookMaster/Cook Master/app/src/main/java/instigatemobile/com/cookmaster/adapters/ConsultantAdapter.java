package instigatemobile.com.cookmaster.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.models.ConsultantModel;

public class ConsultantAdapter extends RecyclerView.Adapter<ConsultantAdapter.ConsultantViewHolde> {

    private List<ConsultantModel> mConsultantModels;
    private Context mContext;

    public ConsultantAdapter(List<ConsultantModel> mConsultantModels, Context mContext) {
        this.mConsultantModels = mConsultantModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ConsultantAdapter.ConsultantViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.consultant_model, parent, false);
        return new ConsultantViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultantAdapter.ConsultantViewHolde holder, int position) {
        holder.bindConsult(position);
    }

    @Override
    public int getItemCount() {
        return mConsultantModels.size();
    }

    public class ConsultantViewHolde extends RecyclerView.ViewHolder {
        private CircleImageView mConsultantImage;
        private TextView mConsultantName;
        private TextView mConsultantEmail;
        private TextView mConsultantPhone;

        public ConsultantViewHolde(@NonNull View itemView) {
            super(itemView);
            mConsultantImage = itemView.findViewById(R.id.consultatnt_image);
            mConsultantName = itemView.findViewById(R.id.consultant_name);
            mConsultantEmail = itemView.findViewById(R.id.consultant_email);
            mConsultantPhone = itemView.findViewById(R.id.consultant_phone);
        }

        public void bindConsult(int position) {
            ConsultantModel consultantModel = mConsultantModels.get(position);
            mConsultantName.setText(consultantModel.getmNameSurname());
            mConsultantEmail.setText(consultantModel.getmEmail());
            mConsultantPhone.setText(consultantModel.getmPhone());
            Picasso.get().load(consultantModel.getmImageUrl()).into(mConsultantImage);
        }

    }
}
