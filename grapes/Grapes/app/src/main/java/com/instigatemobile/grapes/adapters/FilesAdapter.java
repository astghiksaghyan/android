package com.instigatemobile.grapes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.models.FileModel;
import com.instigatemobile.grapes.util.ReadWriteJson;
import com.instigatemobile.grapes.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {

    private Context mContext;
    private AlertDialog alertDialog;

    public FilesAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public FilesAdapter.FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        return new FilesAdapter.FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        FileModel file = Util.mCurrentList.get(position);
        holder.fileIcon.setImageResource(Integer.parseInt(file.getIcon()));
        holder.fileName.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return Util.mCurrentList.size();
    }

    private void openCustomDialog(final int position) {
        final LayoutInflater li = LayoutInflater.from(mContext);
        final View fileView = li.inflate(R.layout.file_info_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(fileView);
        final Button btnDelete = fileView.findViewById(R.id.btn_delete);
        final ImageView imageView = fileView.findViewById(R.id.info_file_icon);
        final ListView infoList = fileView.findViewById(R.id.file_data_list);
        final List<String> info = getInfoList(position);
        final ArrayAdapter<String> infoAdapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, info);
        imageView.setImageResource(Integer.parseInt(Util.mCurrentList.get(position).getIcon()));
        infoList.setAdapter(infoAdapter);
        alertDialog = alertDialogBuilder.create();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfirmDialog(position);
            }
        });
        alertDialog.show();
        alertDialogBuilder.setCancelable(true);
    }

    private void openConfirmDialog(final int position) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ReadWriteJson deleteObj = new ReadWriteJson(mContext);
                        deleteObj.deleteObject(position);
                        Util.mCurrentList.remove(position);
                        notifyDataSetChanged();
                        alertDialog.cancel();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(mContext));
        builder.setMessage("Are you sure you want to delete file?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private List<String> getInfoList(int position) {
        final FileModel file = Util.mCurrentList.get(position);
        final String dots = " : ";
        List<String> list = new ArrayList<>();
        list.add(Util.NAME + dots + file.getName());
        list.add(Util.SIZE + dots + file.getSize());
        list.add(Util.DATE + dots + file.getDate());
        list.add(Util.EXTENSION + dots + file.getExtension());
        return list;
    }

    class FileViewHolder extends RecyclerView.ViewHolder {

        private ImageView fileIcon;
        private TextView fileName;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileIcon = itemView.findViewById(R.id.file_icon);
            fileName = itemView.findViewById(R.id.list_file_name);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openCustomDialog(getAdapterPosition());
                    return false;
                }
            });
        }
    }
}