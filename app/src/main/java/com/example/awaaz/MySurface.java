package com.example.awaaz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class MySurface extends View {
    private Paint paint = new Paint();

    public MySurface(Context context) {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) { // Override the onDraw() Method
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(01);

        //center
        int x0 = canvas.getWidth() / 2;
        int y0 = canvas.getHeight() / 2;
        int dx = canvas.getHeight() / 3;
        int dy = canvas.getHeight() / 3;
        //draw guide box
        Log.e("Tag", "Width" + canvas.getWidth());
        Log.e("Tag", "Height" + canvas.getHeight());
        canvas.drawRect(x0 - dx, y0 - dy, x0 + dx, y0 + dy, paint);
    }
}
