package com.example.sunshine;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;

import android.support.annotation.LayoutRes;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by michal.hornak on 1/28/2017.
 */

public class SunshineWatchFaceService extends CanvasWatchFaceService {

    private class SunshineWatchFaceEngine extends Engine{

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            setWatchFaceStyle( new WatchFaceStyle.Builder( SunshineWatchFaceService.this )
                    .setBackgroundVisibility( WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE )
                    .setCardPeekMode( WatchFaceStyle.PEEK_MODE_VARIABLE )
                    .setShowSystemUiTime( false )
                    .build()
            );

            buildMyLayoutXml(R.layout.activity_display);
        }

        View myLayout;
        int specW, specH;

        private void buildMyLayoutXml(@LayoutRes int layoutId){
            //Inflate layout
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myLayout = inflater.inflate(layoutId, null);

            //Get display size info
            Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point displaySize = new Point();
            display.getSize(displaySize);
            specW = View.MeasureSpec.makeMeasureSpec(displaySize.x, View.MeasureSpec.EXACTLY);
            specH = View.MeasureSpec.makeMeasureSpec(displaySize.y, View.MeasureSpec.EXACTLY);
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds){
            myLayout.measure(specW, specH);
            myLayout.layout(0, 0, myLayout.getMeasuredWidth(), myLayout.getMeasuredHeight());

            //BitmapFactory.decodeResource(getResources(), R.drawable.bg_demo)
            //myLayout.setBackgroundResource( R.drawable.bg_demo);

            canvas.drawColor(Color.YELLOW);
            myLayout.draw(canvas);
        }

    }

    @Override
    public Engine onCreateEngine(){
        return new SunshineWatchFaceEngine();
    }

}
