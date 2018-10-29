package com.example.ducvu212.demomvvm.data.source.local;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import com.example.ducvu212.demomvvm.data.model.ItemFilter;
import com.example.ducvu212.demomvvm.screen.editor.EditActivity;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetItemFilter {

    public static List<ItemFilter> getAllItemFilter(Context context) {
        List<ItemFilter> filters = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String name;
            if (i == 0) {
                name = "None";
            } else {
                name = "Filter " + i;
            }
            String pathGradient = "gradientfilter/gradient" + i + ".png";
            ItemFilter itemFilter = new ItemFilter(EditActivity.sBitmap, name, pathGradient);
            filters.add(itemFilter);
        }
        return getBitmapFilter(context, filters, 100, EditActivity.sBitmap);
    }

    public static Bitmap getBitmapFromGallary(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    public static Bitmap getBitmapAsset(Context context, String path) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap;
        try {
            istr = assetManager.open(path);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            return null;
        }
        return bitmap;
    }

    public static Bitmap mergeBitmap(Bitmap originBitmap, Bitmap gradientBitmap, int alpha) {
        int width = originBitmap.getWidth();
        int height = originBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap resized = Bitmap.createScaledBitmap(gradientBitmap, width, height, true);
        Canvas canvas = new Canvas(updatedBitmap);

        canvas.drawBitmap(originBitmap, 0f, 0f, null);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setAlpha(alpha);
        BitmapShader bitmapShader =
                new BitmapShader(resized, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawRect(0f, 0f, width, height, paint);
        resized.recycle();
        return updatedBitmap;
    }

    public static List<ItemFilter> getBitmapFilter(Context context, List<ItemFilter> filters, int alpha,
            Bitmap path) {
        List<ItemFilter> itemFilters = filters;
        if (path != null) {
            for (int i = 0; i < filters.size(); i++) {
                Bitmap gradient = getBitmapAsset(context, filters.get(i).getPathGradient());
                Bitmap filter = mergeBitmap(path, gradient, alpha);
                itemFilters.get(i).setFilter(filter);
            }
        } else {
        }
        return itemFilters;
    }
}
