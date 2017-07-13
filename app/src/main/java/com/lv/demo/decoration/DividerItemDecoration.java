package com.lv.demo.decoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Date: 2017-07-13
 * Time: 11:20
 * Description:
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    // 轴点画笔(红色
    private Paint mPaint;
    // 写左边日期字的画笔( 时间 + 日期)
    private Paint mPaint1;
    private Paint mPaint2;

    // 左 上偏移长度
    private int itemView_leftinterval;
    private int itemView_topinterval;

    private int circle_radius;

    private Bitmap mIcon;

    public DividerItemDecoration(Context context) {
        // 轴点画笔(红色)
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        // 左边时间文本画笔(蓝色)
        // 此处设置了两只分别设置 时分 & 年月
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setTextSize(30);
        mPaint1.setColor(Color.BLUE);

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(Color.BLUE);

        // 赋值ItemView的左偏移长度为200
        itemView_leftinterval = 200;

        // 赋值ItemView的上偏移长度为50
        itemView_topinterval = 50;

        // 赋值轴点圆的半径为10
        circle_radius = 20;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemView_leftinterval, itemView_topinterval, 0, 0);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            /*
             * 绘制轴点
             */
            // 轴点 = 圆 = 圆心(x,y)
            int centerx = child.getLeft() - itemView_leftinterval / 3;
            int centery = child.getTop() - itemView_topinterval + (itemView_topinterval + child.getHeight()) / 2;
            c.drawCircle(centerx, centery, circle_radius, mPaint);
            /*
             * 绘制上半轴线
             */
            // 上端点坐标(x,y)
            int upline_up_y = child.getTop() - itemView_topinterval;

            // 下端点坐标(x,y)
            int upline_bottom_y = centery - circle_radius;
            c.drawLine(centerx, upline_up_y, centerx, upline_bottom_y, mPaint);

            /*
             * 绘制下半轴线
             */
            // 上端点坐标(x,y)
            int bottom_up_y = centery + circle_radius;
            // 下端点坐标(x,y)
            int bottomLine_bottom_y = child.getBottom();
            c.drawLine(centerx, bottom_up_y, centerx, bottomLine_bottom_y, mPaint);

            /*
             * 绘制左边时间文本
             */
            // 设置文本起始坐标
            int text_x = child.getLeft() - itemView_leftinterval * 5 / 6;
            c.drawText("13:40", text_x, upline_bottom_y, mPaint1);
            c.drawText("2017.4.03", text_x + 5, upline_bottom_y + 20, mPaint2);
        }
    }
}
