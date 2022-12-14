package com.webskygroup.opencart;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CToast {

    public Context context;
    private static float fontSize;
    private static float iconSize;

    public CToast(Context context){
        this.context=context;
    }

    public static void CToast(Context context, int text){
        LayoutInflater inflater = LayoutInflater.from(context);

        View layout = inflater.inflate(R.layout.layout_ctoast, null,false);
        TextView textToast = layout.findViewById(R.id.txt_ctoast);
        //Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/font.ttf");
        //textToast.setTypeface(typeface);
        textToast.setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 400);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static class Builder {
        private CToast cToast;
        public Builder(Context context){
            cToast = new CToast(context);
        }

        public Builder setFontSize (float fontSize){
            cToast.fontSize =fontSize;
            return this;
        }
        public CToast build(){
            return cToast;
        }

    }

}
