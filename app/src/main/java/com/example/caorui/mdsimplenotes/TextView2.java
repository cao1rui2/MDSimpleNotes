package com.example.caorui.mdsimplenotes;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by caorui on 2015/7/24.
 * 复写onTouchEvent，解决autolink点击事件与textview点击事件相冲突。
 */
public class TextView2 extends TextView {

    public TextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
// TODO Auto-generated constructor stub
    }

    public TextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
    }

    public TextView2(Context context) {
        super(context);
// TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
// TODO Auto-generated method stub
        super.onTouchEvent(event);
        ClickableSpan[] links = (new SpannableString(this.getText())).getSpans(getSelectionStart(), getSelectionEnd(), ClickableSpan.class);
        if (links.length != 0) {
            links[0].onClick(this);
            return true;
        }else{
            return false;
        }
    }
}
