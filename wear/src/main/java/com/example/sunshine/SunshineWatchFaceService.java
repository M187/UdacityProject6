package com.example.sunshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import android.support.annotation.LayoutRes;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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

        TextView mMaxTemp, mMinTemp, mDate, mTime;
        ImageView mWeatherIcon;

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

            //Bind elements to members
            mMaxTemp = (TextView) myLayout.findViewById(R.id.temperature_max_text);
            mMinTemp = (TextView) myLayout.findViewById(R.id.temperature_min_text);
            mDate = (TextView) myLayout.findViewById(R.id.date_text);
            mTime = (TextView) myLayout.findViewById(R.id.watch_time_text);
            mWeatherIcon = (ImageView) myLayout.findViewById(R.id.weather_icon_image);
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds){
            myLayout.measure(specW, specH);
            myLayout.layout(0, 0, myLayout.getMeasuredWidth(), myLayout.getMeasuredHeight());

            canvas.drawColor(Color.YELLOW);
            myLayout.draw(canvas);
        }

    }

    @Override
    public Engine onCreateEngine(){
        return new SunshineWatchFaceEngine();
    }

}
