package com.instigatemobile.grapes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.activities.MainActivity;
import com.instigatemobile.grapes.models.DataTransfer;

import java.util.List;

public class DataTransferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DataTransfer> transferList;

    public DataTransferAdapter(MainActivity mainActivity, List<DataTransfer> transferList) {
        this.transferList = transferList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View downloadView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download, parent, false);
            return new DownloadViewHolder(downloadView);
        } else  {
            View uploadView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload, parent, false);
            return new UploadViewHolder(uploadView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == 1) {
            DownloadViewHolder downloadViewHolder = (DownloadViewHolder) holder;
            downloadViewHolder.progress.setProgress(transferList.get(position).getProgress());
            downloadViewHolder.name.setText(transferList.get(position).getName());
            downloadViewHolder.speed.setText(transferList.get(position).getSpeed());
            downloadViewHolder.progress.setProgress(transferList.get(position).getProgress());
        } else  {
            UploadViewHolder uploadViewHolder = (UploadViewHolder) holder;
            uploadViewHolder.progress.setProgress(transferList.get(position).getProgress());
            uploadViewHolder.name.setText(transferList.get(position).getName());
            uploadViewHolder.speed.setText(transferList.get(position).getSpeed());
            uploadViewHolder.progress.setProgress(transferList.get(position).getProgress());

        }

    }
    @Override
    public int getItemCount() {
        return transferList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (transferList.get(position).isDownload()) {
            return 1;
        }
        return 0;

    }

    public class UploadViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView icon;
        private final ProgressBar progress;
        private final TextView speed;
        public UploadViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.upload_name);
            icon = itemView.findViewById(R.id.upload_icon);
            progress = itemView.findViewById(R.id.upload_progressBar);
            speed = itemView.findViewById(R.id.upload_speed);

        }
    }

    public class DownloadViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView icon;
        private final ProgressBar progress;
        private final TextView speed;
        private final Button cancle;
        public DownloadViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.download_name);
            icon = itemView.findViewById(R.id.download_icon);
            progress = itemView.findViewById(R.id.download_progressBar);
            speed = itemView.findViewById(R.id.download_speed);
            cancle = itemView.findViewById(R.id.cancle_download);
        }
    }
}