package com.github.tvbox.osc.ui.dialog;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.tvbox.osc.R;
import com.github.tvbox.osc.util.DefaultConfig;

import org.jetbrains.annotations.NotNull;

public class AboutDialog extends BaseDialog {

    public AboutDialog(@NonNull @NotNull Context context) {
        super(context);
        setContentView(R.layout.dialog_about);
        String version = DefaultConfig.getAppVersionName(context);
        TextView textView = findViewById(R.id.about);
        String text = textView.getText().toString().trim();
        text = String.format(text,version);
        textView.setText(text);

    }
}