
package custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * @类名: NumberSeekBar
 * @描述: TODO(带有数字的水平拖动条)
 * @作者: wang.fb
 * @日期: 2014-8-11 下午2:01:14
 * @修改人:
 * @修改时间: 2014-8-11 下午2:01:14
 * @修改内容:
 * @版本: V1.0
 */
public class NumberSeekBar extends SeekBar {
    
    private int oldPaddingTop;
    
    private int oldPaddingLeft;
    
    private int oldPaddingRight;
    
    private int oldPaddingBottom;
    
    private boolean isMysetPadding = true;
    
    private String mText;
    
    private float mTextWidth;
    
    private float mImgWidth;
    
    private float mImgHei;
    
    private Paint mPaint;
    
    private Resources res;
    
    private Bitmap bm;
    
    private int textsize = dp2px(18);

    private int Czsize = dp2px(15);
    
    private int textpaddingleft;
    
    private int textpaddingtop;
    
    private int imagepaddingleft;
    
    private int imagepaddingtop;

    private int Dvalue = 0;
    
    public NumberSeekBar(Context context) {
        super(context);
        init();
    }
    
    public NumberSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public NumberSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    // 屏蔽滑动
    // @Override
    // public boolean onTouchEvent(MotionEvent event) {
    // return false;
    // }
    /**
     * (非 Javadoc)
     * 
     * @方法名: onTouchEvent
     * @描述: 不屏蔽屏蔽滑动
     * @日期: 2014-8-11 下午2:03:15
     * @param event
     * @return
     * @see android.widget.AbsSeekBar#onTouchEvent(MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    
    // 修改setpadding 使其在外部调用的时候无效
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (isMysetPadding) {
            super.setPadding(left, top, right, bottom);
        }
    }
    
    // 初始化
    private void init() {
        res = getResources();
//        initBitmap();
        initDraw();
//        setPadding();
    }
    
    private void initDraw() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(textsize);
        mPaint.setColor(Color.BLACK);
    }
    

    
    protected synchronized void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
            mText = (getProgress() +Dvalue) + "";
            mTextWidth = mPaint.measureText(mText);
            Rect bounds = this.getThumb().getBounds();
            float xText =
                    bounds.centerX()-mTextWidth/2;
//                bounds.width() * getProgress() / getMax();

            float yText =getHeight()-Czsize;
            canvas.drawText(mText, xText, yText, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 初始化padding 使其左右上 留下位置用于展示进度图片
    private void setPadding() {
        int top = getBitmapHeigh() + oldPaddingTop;
        int left = getBitmapWidth() / 2 + oldPaddingLeft;
        int right = getBitmapWidth() / 2 + oldPaddingRight;
        int bottom = oldPaddingBottom;
        isMysetPadding = true;
        setPadding(left, top, right, bottom);
        isMysetPadding = false;
    }
    

    
    /**
     * 替代setpadding
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setMyPadding(int left, int top, int right, int bottom) {
        oldPaddingTop = top;
        oldPaddingLeft = left;
        oldPaddingRight = right;
        oldPaddingBottom = bottom;
        isMysetPadding = true;
        setPadding(left + getBitmapWidth() / 2, top + getBitmapHeigh(), right
            + getBitmapWidth() / 2, bottom);
        isMysetPadding = false;
    }
    
    /**
     * 设置进度字体大小
     * 
     * @param textsize
     */
    public void setTextSize(float textsize) {
        this.textsize = dp2px(textsize);
        mPaint.setTextSize(this.textsize);
    }
    
    /**
     * 设置进度字体颜色
     * 
     * @param color
     */
    public void setTextColor(int color) {
        mPaint.setColor(color);
    }
    
    /**
     * 调整进度字体的位置 初始位置为图片的正中央
     * 
     * @param top
     * @param left
     */
    public void setTextPadding(int top, int left) {
        this.textpaddingleft = left;
        this.textpaddingtop = top;
    }
    

    
    private int getBitmapWidth() {
        return (int)Math.ceil(mImgWidth);
    }
    
    private int getBitmapHeigh() {
        return (int)Math.ceil(mImgHei);
    }
    
    private float getTextHei() {
        FontMetrics fm = mPaint.getFontMetrics();
        return (float)Math.ceil(fm.descent - fm.top) + 2;
    }
    
    public int getTextpaddingleft() {
        return textpaddingleft;
    }
    
    public int getTextpaddingtop() {
        return textpaddingtop;
    }
    
    public int getImagepaddingleft() {
        return imagepaddingleft;
    }
    
    public int getImagepaddingtop() {
        return imagepaddingtop;
    }
    
    public int getTextsize() {
        return textsize;
    }

    public  int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int getDvalue() {
        return Dvalue;
    }

    public void setDvalue(int dvalue) {
        Dvalue = dvalue;
    }

    public int getCzsize() {
        return Czsize;
    }

    public void setCzsize(float czsize) {
        Czsize = dp2px(czsize);
    }
}
