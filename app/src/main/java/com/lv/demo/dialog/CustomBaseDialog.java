package com.lv.demo.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.lv.demo.R;
import com.lv.demo.base.BaseDialog;
import com.lv.demo.dialog.animation.Swing;
import com.lv.demo.utils.CornerUtils;


public class CustomBaseDialog extends BaseDialog<CustomBaseDialog> {
    TextView mTvCancel;
    TextView mTvExit;
    private View inflate;

    public CustomBaseDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new Swing());
        inflate = View.inflate(mContext, R.layout.dialog_custom_base, null);
        mTvCancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        mTvExit = (TextView) inflate.findViewById(R.id.tv_exit);
        inflate.setBackgroundDrawable(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));
        return inflate;
    }


    @Override
    public void setUiBeforShow() {
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
