package com.iranian.cards.android.batmanproject.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.iranian.cards.android.batmanproject.R;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public static int styleCode;
    public static Activity myActivity;
    private DialogClickListener positiveButtonClickListener;
    private DialogClickListener negativeButtonClickListener;
    private String title;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;
    private boolean setPositiveButton;
    private boolean setNegativeButton;

    private CustomDialog(Activity activity) {
        super(activity, R.style.LightDialogTheme);
            setContentView(R.layout.dialog_custom);
    }

    public static CustomDialog with(Activity activity, int style) {
        styleCode = style;
        myActivity = activity;
        return new CustomDialog(activity);
    }

    public CustomDialog title(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog message(String message) {
        this.message = message;
        return this;
    }

    public CustomDialog positiveButton(String buttonText, DialogClickListener dialogClickListener) {

        this.setPositiveButton = true;
        this.positiveButtonText = buttonText;
        this.positiveButtonClickListener = dialogClickListener;
        return this;
    }

    public CustomDialog negativeButton(String buttonText, DialogClickListener dialogClickListener) {
        this.setNegativeButton = true;
        this.negativeButtonText = buttonText;
        this.negativeButtonClickListener = dialogClickListener;
        return this;
    }

    @SuppressLint("ResourceType")
    @Override
    public void show() {

//         set title
        if (title != null && title.length() > 0) {
            AppCompatTextView txtTitle = findViewById(R.id.dialog_txt_title);
            txtTitle.setText(title);
            txtTitle.setVisibility(View.VISIBLE);
        }

        // set message
        if (message != null && message.length() > 0) {
            AppCompatTextView txtMessage = findViewById(R.id.dialog_txt_message);
            txtMessage.setText(message);
            txtMessage.setVisibility(View.VISIBLE);
        }

        // set positive button
        if (setPositiveButton) {
            AppCompatButton btnPositive = findViewById(R.id.dialog_btn_positive);
            btnPositive.setText(positiveButtonText);
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setOnClickListener(this);
        }

        // set negative button
        if (setNegativeButton) {
            AppCompatButton btnNegative = findViewById(R.id.dialog_btn_negative);
            btnNegative.setText(negativeButtonText);
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setOnClickListener(this);
        }
        if (!myActivity.isFinishing())
            super.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.dialog_btn_positive:
                if (positiveButtonClickListener != null)
                    positiveButtonClickListener.onClick(this);
                break;

            case R.id.dialog_btn_negative:
                if (negativeButtonClickListener != null)
                    negativeButtonClickListener.onClick(this);
                break;
        }
    }

    public interface DialogClickListener {
        void onClick(CustomDialog dialog);
    }
}