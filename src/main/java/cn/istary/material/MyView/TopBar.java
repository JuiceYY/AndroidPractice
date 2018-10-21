package cn.istary.material.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;

import cn.istary.material.R;

public class TopBar extends RelativeLayout{

    //attrs
    //颜色类型int， 背景类型Drawable
    private String titleText;
    private float titleTextSize;
    private int titleTextColor;
    private String leftText;
    private Drawable leftBackground;
    private int leftTextColor;
    private String rightText;
    private Drawable rightBackground;
    private int rightTextColor;

    private Button leftButton;
    private Button rightButton;
    private TextView textView;

    private LayoutParams leftButtonParams, rightButtonParams, textViewParams;

    private TopBarClickListener topBarClickListener;

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initWidget(context);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        leftText = typedArray.getString(R.styleable.TopBar_leftText);
        leftTextColor = typedArray.getColor(R.styleable.TopBar_leftTextColor, 0);
        leftBackground = typedArray.getDrawable(R.styleable.TopBar_leftBackground);
        rightText = typedArray.getString(R.styleable.TopBar_rightText);
        rightTextColor = typedArray.getColor(R.styleable.TopBar_rightTextColor, 0);
        rightBackground = typedArray.getDrawable(R.styleable.TopBar_rightBackground);
        titleText = typedArray.getString(R.styleable.TopBar_titleText);
        titleTextColor = typedArray.getColor(R.styleable.TopBar_titleTextColor, 0);
        titleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize, 10);

        typedArray.recycle();
    }

    private void initWidget(Context context){
        leftButton = new Button(context);//构造函数？
        rightButton = new Button(context);
        textView = new TextView(context);
        leftButton.setTextColor(leftTextColor);
        leftButton.setText(leftText);
        leftButton.setBackground(leftBackground);
        rightButton.setTextColor(rightTextColor);
        rightButton.setText(rightText);
        rightButton.setBackground(rightBackground);
        textView.setText(titleText);
        textView.setTextColor(titleTextColor);
        textView.setTextSize(titleTextSize);
        textView.setGravity(Gravity.CENTER);

        leftButtonParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftButtonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton, leftButtonParams);

        rightButtonParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rightButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton, rightButtonParams);

        textViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        textViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(textView, textViewParams);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topBarClickListener.leftClick();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topBarClickListener.rightClick();
            }
        });

    }

    //暴露接口给调用方
    //调用方在这个方法中传入自己重写过点击事件的TopbarClickListener， 来改变TopBar的监听器， 实现不同的逻辑
    public void setOnTopBarClickListener(TopBarClickListener topBarClickListener){
        this.topBarClickListener = topBarClickListener;
    }

    //自定义回调接口， 让调用者实现Button的逻辑
    public interface TopBarClickListener{
        void leftClick();
        void rightClick();
    }
}
