package com.lv.demo.decoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lv.demo.R;

/**
 * Date: 2017-07-13
 * Time: 10:23
 * Description:
 */

public class TestDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private int mDivider=10;
    private Bitmap mIcon;
    //设置ItemView的内嵌便宜长度

    public TestDividerItemDecoration(Context context) {
        mPaint=new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        // 获取图片资源
        mIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 参数说明：
        // 1. outRect：全为 0 的 Rect（包括着Item）
        // 2. view：RecyclerView 中的 视图Item
        // 3. parent：RecyclerView 本身
        // 4. state：状态
        super.getItemOffsets(outRect, view, parent, state);
        //最后一个不绘制
        int position = parent.getChildAdapterPosition(view);
        if(position==parent.getAdapter().getItemCount()-1)
            return;
        outRect.set(0,0,0,mDivider);
        // 4个参数分别对应左（Left）、上（Top）、右（Right）、下（Bottom）
        // 上述语句代表：左&下偏移长度=50px，右 & 上 偏移长度 = 0

    }

    // 作用：在子视图上设置绘制范围，并绘制内容
    // 类似平时自定义View时写onDraw()一样
    // 绘制图层在ItemView以下，所以如果绘制区域与ItemView区域相重叠，会被遮挡
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // 获取RecyclerView的Child view的个数
        int childCount = parent.getChildCount();
        RecyclerView.LayoutParams params;
        View child;
        for (int i = 0; i < childCount; i++) {
            child=parent.getChildAt(i);
            params=(RecyclerView.LayoutParams) child.getLayoutParams();
            int position = parent.getChildAdapterPosition(child);
            if(position==parent.getAdapter().getItemCount()-1)
                return;
            //RecyclerView 的左边界加上paddingLeft距离后的坐标位置
            int left = parent.getPaddingLeft();

            // 即左右边界就是 RecyclerView 的 ItemView区域
            int top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            //RecyclerView 的右边界减去 paddingRight 后的坐标位置
            int right = parent.getWidth() - parent.getPaddingRight();
            int bottom = top + mDivider;
            // 通过Canvas绘制矩形（分割线）
            c.drawRect(left,top,right,bottom,mPaint);
        }


    }

    // 作用：同样是绘制内容，但与onDraw（）的区别是：绘制在图层的最上层
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
// 获取Item的总数
        int childCount = parent.getChildCount();
        // 遍历Item
        for (int i = 0; i < childCount; i++) {
            // 获取每个Item的位置
            View view = parent.getChildAt(i);

            // 设置绘制内容的坐标(ItemView的左边界,ItemView的上边界)
            // ItemView的左边界 = RecyclerView 的左边界 = paddingLeft距离 后的位置
            final int left = parent.getWidth() / 2;
            // ItemView的上边界
            float top = view.getTop();

            // 第1个ItemView不绘制
            int position = parent.getChildAdapterPosition(view);
            if (position == parent.getAdapter().getItemCount() - 1)
                return;
            // 通过Canvas绘制角标
            c.drawBitmap(mIcon, left, top, mPaint);

        }
    }
}
