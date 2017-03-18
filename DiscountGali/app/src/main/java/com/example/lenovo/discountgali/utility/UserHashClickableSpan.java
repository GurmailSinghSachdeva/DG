package com.example.lenovo.discountgali.utility;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;


//a version of ClickableSpan without the underline
public class UserHashClickableSpan extends ClickableSpan {
    private final int pos;
    private final String name;
    private final Utils.SpanClickListenerI listenerI;
//    private final Context context;
    private int color = -1;

    UserHashClickableSpan(int color, String name, int pos, Utils.SpanClickListenerI listenerI) {
        super();
        this.color = color;
        this.name = name;
        this.pos = pos;
        this.listenerI = listenerI;

    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void onClick(View widget) {

        Syso.print("clicked" + name);

        listenerI.onClick(name, pos);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
        if (this.color != -1) {
            ds.setColor(this.color);
        }
    }
}