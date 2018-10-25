package com.example.ducvu212.demomvvm.data.source.remote;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import com.example.ducvu212.demomvvm.screen.details.ImageDetailsViewListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by CuD HniM on 18/10/07.
 */
public class Download {

    private static final String IMAGE_PATH_DOWNLOAD = "/Download/ImageEditor";
    private static final String PATH_DOWNLOAD = "/Download/";
    public static final String IMAGE_DIRECTORY = "/ImageEditor/";
    public static final String IMAGE_FILE_EXTENSION = ".png";
    private String mPathFile;
    private String mDirectPath;
    private ImageDetailsViewListener mListener;
    private DownloadManager mDownloadManager;

    public Download(DownloadManager manager, ImageDetailsViewListener listener) {
        mDownloadManager = manager;
        mListener = listener;
    }

    public void download(String url, String name) {
        Uri downloadUri = Uri.parse(url);
        createForderDownload(name);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading " + name);
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(IMAGE_PATH_DOWNLOAD,
                name.concat(IMAGE_FILE_EXTENSION));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        queryStatus(mDownloadManager, request);
    }

    private void queryStatus(DownloadManager downloadManager, DownloadManager.Request request) {
        long id = downloadManager.enqueue(request);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor cursor = downloadManager.query(query);
        if (cursor == null) {
            mListener.downloadStatus("Download not found");
        } else {
            cursor.moveToFirst();
            int bytes_downloaded = cursor.getInt(
                    cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            request.setDescription(bytes_downloaded + "");
            mListener.downloadStatus(statusMessage(cursor));
        }
    }

    private String statusMessage(Cursor c) {
        String msg;
        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = "Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download complete!";
                break;

            default:
                msg = "Download is nowhere in sight";
                break;
        }
        c.close();
        return (msg);
    }

    private void createForderDownload(String title) {
        File cacheDownloadFile = new File(IMAGE_PATH_DOWNLOAD);
        StringBuilder builder = new StringBuilder();
        builder.append(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                .append(IMAGE_DIRECTORY)
                .append(title)
                .append(IMAGE_FILE_EXTENSION);
        mDirectPath = builder.toString();
        File dir = new File(IMAGE_PATH_DOWNLOAD);
        File cacheDir = new File(PATH_DOWNLOAD);
        if (!cacheDir.isDirectory()) {
            cacheDir.mkdirs();
        }
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        File file = new File(mDirectPath);
        if (file.exists()) {
            mPathFile = file.getPath();
        } else {
            mPathFile = mDirectPath;
        }
        if (!cacheDownloadFile.exists()) {
            try {
                cacheDownloadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
