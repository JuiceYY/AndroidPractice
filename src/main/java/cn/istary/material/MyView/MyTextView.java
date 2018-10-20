package cn.istary.material.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    /**
     * 四个构造函数都是干什么的？
     * xml调用了那个构造函数？
     * 参考https://blog.csdn.net/zhao123h/article/details/52210732
     * **必须在构造函数中初始化画笔
     * 1. public View(Context context) 用途并不大，主要是在java代码中声明一个View时所用，不过如果只用第一个构造函数，声明的View并没有任何的参数，基本是个空的View对象。
     * 2.  public View(Context context, @Nullable AttributeSet attrs) 当我们自定义一个View，且在布局文件中引用时，在系统初始化该View时，调用的是第二个构造函数，而且其中的参数attrs是我们在xml中配置的参数
     * 3. public View(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
     * 4.public View(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
     */


    private Paint mPaint1;
    private Paint mPaint2;

    public MyTextView(Context context) {
        //在构造方法中初始化画笔等工具
        super(context);
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);//外层矩形
        canvas.drawRect(10, 10, getMeasuredWidth()-10, getMeasuredHeight()-10, mPaint2);//内层矩形
        canvas.save();
        canvas.translate(10,0);//绘制文字前平移10像素

        super.onDraw(canvas);//父类显示文字

        canvas.restore();
    }
}
