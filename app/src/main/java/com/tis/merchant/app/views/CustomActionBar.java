package com.tis.merchant.app.views;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tis.merchant.app.R;


/**
 * Created by Nanthakorn on 25/11/2558.
 */
public class CustomActionBar {

    private TextView tvTitleBar;
    private ImageView imgButtonRight;
    private View layoutBar;
    private Context _context;
    private ActionBar _supportActionBar;

    public CustomActionBar(Context context, ActionBar supportActionBar) {
        View customBar = LayoutInflater.from(context).inflate(R.layout.custom_actionbar, null);
        layoutBar = customBar.findViewById(R.id.layoutBar);
        tvTitleBar = (TextView) customBar.findViewById(R.id.tvTitle);

        supportActionBar.setCustomView(customBar, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        ));
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        this._supportActionBar = supportActionBar;
        this._context = context;
    }

    public void setTitle(String title){
        tvTitleBar.setText(title);
    }

    public void setTitle(int title){
        tvTitleBar.setText(title);
    }

    public void setColorBar(int color)
    {
        tvTitleBar.setBackgroundColor(color);
        layoutBar.setBackgroundColor(color);
    }

    public void setColorTitle(int color)
    {
        tvTitleBar.setTextColor(color);
    }



    public void setDisplayHomeAsUpEnabled(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            _supportActionBar.setHomeButtonEnabled(true);
        }else {
            _supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
