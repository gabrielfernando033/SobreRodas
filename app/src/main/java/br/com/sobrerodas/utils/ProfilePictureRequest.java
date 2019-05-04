package br.com.sobrerodas.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ProfilePictureRequest extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public ProfilePictureRequest(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap = null;
        try
        {
            InputStream in = new URL(urls[0]).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        Bitmap mbitmap = result;
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint);
        imageView.setImageBitmap(imageRounded);
    }

}