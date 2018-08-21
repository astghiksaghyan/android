package com.instigatemobile.grapes.util;

import com.codekidlabs.storagechooser.StorageChooser;
import com.instigatemobile.grapes.models.FileModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Util {
    public static final String IS_FIRST_RUN = "isFirstRun";
    public static final String NICKNAME = "nickname";
    public static final String NAME = "name";
    public static final String ICON = "icon";
    public static final String SIZE = "size";
    public static final String EXTENSION = "extension";
    public static final String DATE = "date";
    public static final String PATH = "path";
    public static final String FILE_SIZE = "file size";

    public static StorageChooser.Builder mBuilder;
    public static String mFilePath;
    public static List<FileModel> mFileList = new ArrayList<>();
    public static List<FileModel> mBeforeSearchList;

    public static List<FileModel> mSortByName;
    public static List<FileModel> mSortBySize;
    public static List<FileModel> mSortByDate;
    public static List<FileModel> mFilterByBooks;
    public static List<FileModel> mFilterByMusics;
    public static List<FileModel> mFilterByVideos;
    public static List<FileModel> mFilterByImages;

    public static List<FileModel> mCurrentList;
    public static String mNickname;

    public static void filter(String ext) {
        List<FileModel> filterList = new ArrayList<>();
        for(FileModel model : mCurrentList) {
            if(ext.equals(model.getExtension())) {
                filterList.add(model);
            }
        }
        mCurrentList = filterList;
    }

    public static void sortBy(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case NAME:
                Collections.sort(mCurrentList, new Comparator<FileModel>() {
                    @Override
                    public int compare(FileModel o1, FileModel o2) {
                        return (o1.getName()).compareToIgnoreCase(o2.getName());
                    }
                });
                break;
            case DATE:
                Collections.sort(mCurrentList, new Comparator <FileModel>() {
                    @Override
                    public int compare(FileModel o1, FileModel o2) {
                        return (o1.getDate()).compareToIgnoreCase(o2.getDate());
                    }
                });
                break;
            case FILE_SIZE:
                Collections.sort(mCurrentList, new Comparator <FileModel>() {
                    @Override
                    public int compare(FileModel o1, FileModel o2) {
                        return String.valueOf((o1.getSize())).compareToIgnoreCase(String.valueOf(o2.getSize()));
                    }
                });
                break;
        }
        mBeforeSearchList = mCurrentList;
    }

    public static void Search(CharSequence charSequence) {
        String charString = charSequence.toString();
        if (charString.isEmpty()) {
            mCurrentList = mBeforeSearchList;
        } else {
            mCurrentList = new ArrayList<>();
            for (FileModel file : mBeforeSearchList) {
                if (file.getName().toLowerCase().contains(charString.toLowerCase())) {
                    mCurrentList.add(file);
                }
            }
        }
    }
}
