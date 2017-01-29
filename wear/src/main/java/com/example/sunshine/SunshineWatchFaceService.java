package com.example.sunshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import android.os.Handler;
import android.os.Message;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michal.hornak on 1/28/2017.
 */

public class SunshineWatchFaceService extends CanvasWatchFaceService {

    private class SunshineWatchFaceEngine extends Engine{


        private final int MSG_UPDATE_TIME = 0;

        // handler to update the time once a second in interactive mode
        final Handler mUpdateTimeHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case MSG_UPDATE_TIME:
                        invalidate();
                        if (shouldTimerBeRunning()) {
                            long timeMs = System.currentTimeMillis();
                            long delayMs = 1 - (timeMs % 1000);
                            mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
                        }
                        break;
                }
            }
        };

        private boolean shouldTimerBeRunning(){
            return  (this.isVisible() & !this.isInAmbientMode());
        }


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

        @Override
        public void onVisibilityChanged(boolean visible){
            super.onVisibilityChanged(visible);
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
        }

        View myLayout;
        int specW, specH;

        DateFormat mTimeDateFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat mDateDateFormat = new SimpleDateFormat("dd:MM:yyyy");

        TextView mMaxTempTV, mMinTempTV, mDateTV, mTimeTV;
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
            mMaxTempTV = (TextView) myLayout.findViewById(R.id.temperature_max_text);
            mMinTempTV = (TextView) myLayout.findViewById(R.id.temperature_min_text);
            mDateTV = (TextView) myLayout.findViewById(R.id.date_text);
            mTimeTV = (TextView) myLayout.findViewById(R.id.watch_time_text);
            mWeatherIcon = (ImageView) myLayout.findViewById(R.id.weather_icon_image);
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds){
            myLayout.measure(specW, specH);
            myLayout.layout(0, 0, myLayout.getMeasuredWidth(), myLayout.getMeasuredHeight());

            //set time and date
            Date mDate = new Date();
            mTimeTV.setText(mTimeDateFormat.format(mDate));
            mDateTV.setText(mDateDateFormat.format(mDate));

            canvas.drawColor(Color.YELLOW);
            myLayout.draw(canvas);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

    }

    @Override
    public Engine onCreateEngine(){
        return new SunshineWatchFaceEngine();
    }

}
