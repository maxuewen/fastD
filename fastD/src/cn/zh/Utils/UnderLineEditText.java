package cn.zh.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class UnderLineEditText extends EditText implements TextWatcher{
	
	private Paint linePaint;
	private int paperColor;
	private Drawable dRight;
	private Rect rBounds;
	private Drawable dLeft;

	public UnderLineEditText(Context context,AttributeSet paramAttributeSet) {
		super(context,paramAttributeSet);
		// TODO Auto-generated constructor stub
		this.linePaint = new Paint();
		linePaint.setStrokeWidth(2);
		linePaint.setColor(Color.rgb(213,213,213));//设置下划线颜色
		setEditTextDrawable();
		this.addTextChangedListener(this);
	}
	
	protected void onDraw(Canvas paramCanvas) {
		paramCanvas.drawColor(this.paperColor); //设置背景色
		int i = getLineCount();
		int j = getHeight();
		int k = getLineHeight();
		int m = 1 + j / k;
		if (i < m) i = m;
		int n = getCompoundPaddingTop();
		
		int distance_with_btm=(int) (getLineHeight()-getTextSize())-3;
		//这个关于距离底部的变量当不使用lineSpacingMultiplier和lineSpacingExtra参数时是不起作用的

		for (int i2 = 0;; i2++) {
			if (i2 >= i) {
				super.onDraw(paramCanvas);
				paramCanvas.restore();
				return;
			}
			
			n += k;
			n-=distance_with_btm;//将线划在字体靠下面
			paramCanvas.drawLine(0.0F, j-1, getRight(), j-1, this.linePaint);
			paramCanvas.save();
			n+=distance_with_btm;//还原n
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		// TODO Auto-generated method stub
		
		UnderLineEditText.this.setEditTextDrawable();  
		
	}
	
	
	public void setCompoundDrawables(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4) {  
        if (paramDrawable3 != null)  
            this.dRight = paramDrawable3;  
        	this.dLeft = paramDrawable1;
        super.setCompoundDrawables(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);  
    } 
	
	
	@Override  
    public boolean onTouchEvent(MotionEvent paramMotionEvent) {  
        if ((this.dRight != null) && (paramMotionEvent.getAction() == 1)) {  
            this.rBounds = this.dRight.getBounds();  
            int i = (int) paramMotionEvent.getRawX();// 距离屏幕的距离  
            // int i = (int) paramMotionEvent.getX();//距离边框的距离  
            if (i > getRight() - 3 * this.rBounds.width()) {
                setText("");  
                paramMotionEvent.setAction(MotionEvent.ACTION_CANCEL);  
            }  
        }  
        return super.onTouchEvent(paramMotionEvent);  
    }
	
	public void setEditTextDrawable() {  
        if (getText().toString().length() == 0) {  
            setCompoundDrawables(this.dLeft, null, null, null);  
        } else {  
            setCompoundDrawables(this.dLeft, null, this.dRight, null);  
        }  
    }

}
