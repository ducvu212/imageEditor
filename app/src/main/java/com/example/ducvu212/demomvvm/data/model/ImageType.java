package com.example.ducvu212.demomvvm.data.model;

import android.support.annotation.StringDef;

import static com.example.ducvu212.demomvvm.data.model.ImageType.LOCAL;
import static com.example.ducvu212.demomvvm.data.model.ImageType.REMOTE;

@StringDef({LOCAL, REMOTE})
public @interface ImageType {
    String LOCAL = "local";
    String REMOTE = "remote";
}
