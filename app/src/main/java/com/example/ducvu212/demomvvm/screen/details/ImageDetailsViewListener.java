package com.example.ducvu212.demomvvm.screen.details;

/**
 * Created by CuD HniM on 18/10/06.
 */
public interface ImageDetailsViewListener {

    void updateLikeButton(boolean isLike);

    void updateDownloadButton(boolean isDownloaded);

    void downloadStatus(String status);
}
