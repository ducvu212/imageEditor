package com.example.ducvu212.demomvvm.data.source.remote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CuD HniM on 18/09/06.
 */
public class ConvertBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

    public static Bitmap mBitmap;

    @Override
    protected Bitmap doInBackground(String... strings) {
        return convert(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mBitmap = bitmap;
    }

    private Bitmap convert(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }
}
