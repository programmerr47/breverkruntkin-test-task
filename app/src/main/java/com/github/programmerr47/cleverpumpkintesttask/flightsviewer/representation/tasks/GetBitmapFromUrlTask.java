package com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public class GetBitmapFromUrlTask extends AsyncTaskWithListener<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        if (params.length > 0) {
            String url = params[0];
            return getBitmapFromInternet(url);
        } else {
            return null;
        }
    }

    private Bitmap getBitmapFromInternet(String url) {
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            return decodeStream(is);
        } catch (Throwable ex){
            ex.printStackTrace();
            return null;
        }
    }

    private Bitmap decodeStream(InputStream is) {
        if (is != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            return BitmapFactory.decodeStream(is, null, options);
        } else {
            return null;
        }
    }
}
