package com.instigatemobile.grapes.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codekidlabs.storagechooser.StorageChooser;
import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.adapters.FilesAdapter;
import com.instigatemobile.grapes.models.FileModel;
import com.instigatemobile.grapes.util.ReadWriteJson;
import com.instigatemobile.grapes.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private StorageChooser mChooser;
    @SuppressLint("StaticFieldLeak")
    private static FilesAdapter mFileAdapter;
    private StorageChooser.Builder mBuilder;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        final int numberOfColumns = 3;
        final RecyclerView fileRv = view.findViewById(R.id.files_recycler_view);
        final Spinner categories = view.findViewById(R.id.categories);
        final Spinner sort = view.findViewById(R.id.sort_by);
        fileRv.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        mFileAdapter = new FilesAdapter(getContext());
        fileRv.setAdapter(mFileAdapter);
        setAddButtonListener(view);
        sortListener(sort);
        categoriesListener(categories);
        return view;
    }

    public static void updateList() {
        mFileAdapter.notifyDataSetChanged();
    }

    private void categoriesListener(Spinner sort) {
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Util.mCurrentList = Util.mFileList;
                switch (selectedItem.toLowerCase()) {
                    case "all":
                        Util.mCurrentList = Util.mFileList;
                        break;
                    case "pictures":
                        Util.filter("jpg"); //png
                        break;
                    case "videos":
                        Util.filter("mp4");
                        break;
                    case "musics":
                        Util.filter("mp3");
                        break;
                    case "books":
                        Util.filter("pdf");
                        break;
                    case "other":
                        filterForOther();
                        break;
                }
                mFileAdapter.notifyDataSetChanged();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private static void filterForOther() {
        String[] extArray = {"jpg", "png", "pdf", "mp3", "mp4"};
        List<FileModel> filterList = new ArrayList<>();
        for (FileModel file : Util.mCurrentList) {
            if (!hasExtension(file.getExtension(), extArray)) {
                filterList.add(file);
            }
        }
        Util.mCurrentList = filterList;
    }

    public static boolean hasExtension(String extension, String[] extensions) {
        for (String tmpExtension : extensions) {
            if (extension.contains(tmpExtension)) {
                return true;
            }
        }
        return false;
    }

    private void sortListener(Spinner sort) {
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem.toLowerCase()) {
                    case Util.NAME:
                        Util.sortBy(Util.NAME);
                        break;
                    case Util.DATE:
                        Util.sortBy(Util.DATE);
                        break;
                    case Util.FILE_SIZE:
                        Util.sortBy(Util.SIZE);
                        break;
                }
                mFileAdapter.notifyDataSetChanged();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAddButtonListener(View view) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mBuilder = Util.mBuilder;
        mBuilder.withMemoryBar(true);
        mBuilder.allowAddFolder(true);
        mBuilder.allowCustomPath(true);
        mBuilder.setType(StorageChooser.FILE_PICKER);

        FloatingActionButton fab = view.findViewById(R.id.add_file_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChooser = mBuilder.build();
                mChooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
                    @Override
                    public void onSelect(String path) {
                        ReadWriteJson writeJson = new ReadWriteJson(getContext());
                        writeJson.writeJson(path);
                        mFileAdapter.notifyDataSetChanged();
                    }
                });

                mChooser.setOnCancelListener(new StorageChooser.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(getContext(), R.string.chooser_canceled, Toast.LENGTH_SHORT).show();
                    }
                });

                mChooser.setOnMultipleSelectListener(new StorageChooser.OnMultipleSelectListener() {
                    @Override
                    public void onDone(ArrayList<String> selectedFilePaths) {
                    }
                });
                mChooser.show();
            }
        });
    }
}