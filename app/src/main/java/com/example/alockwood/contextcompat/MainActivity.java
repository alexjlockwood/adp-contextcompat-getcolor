package com.example.alockwood.contextcompat;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.StateSet;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportActionBar().setSubtitle(
        getString(R.string.api_version_format, Build.VERSION.SDK_INT));

    final Resources res = getResources();

    // (1)
    int deprecatedTextColor = res.getColor(R.color.button_text_csl);
    initButtons(R.id.example1, deprecatedTextColor);

    // (2)
    ColorStateList deprecatedTextCsl = res.getColorStateList(R.color.button_text_csl);
    initButtons(R.id.example2, deprecatedTextCsl);

    // (3)
    int textColorXml = ContextCompat.getColor(this, R.color.button_text_csl);
    initButtons(R.id.example3, textColorXml);

    // (4)
    ColorStateList textCslXml = ContextCompat.getColorStateList(this, R.color.button_text_csl);
    initButtons(R.id.example4, textCslXml);

    // (5)
    ViewGroup container5 = (ViewGroup) findViewById(R.id.example5);
    ColorStateList textCslXmlWithCustomTheme =
        ContextCompat.getColorStateList(container5.getContext(), R.color.button_text_csl);
    initButtons(R.id.example5, textCslXmlWithCustomTheme);

    // (6)
    int textColorJava = getThemeAttrColor(this, R.attr.colorPrimary);
    initButtons(R.id.example6, textColorJava);

    // (7)
    ColorStateList textCslJava = createColorStateList(this);
    initButtons(R.id.example7, textCslJava);

    // (8)
    ViewGroup container8 = (ViewGroup) findViewById(R.id.example8);
    ColorStateList textCslJavaWithCustomTheme = createColorStateList(container8.getContext());
    initButtons(R.id.example8, textCslJavaWithCustomTheme);
  }

  private void initButtons(@IdRes int containerResId, @ColorInt int textColor) {
    final ViewGroup container = (ViewGroup) findViewById(containerResId);
    for (int i = 0, count = container.getChildCount(); i < count; i++) {
      ((Button) container.getChildAt(i)).setTextColor(textColor);
    }
  }

  private void initButtons(@IdRes int containerResId, ColorStateList textColorStateList) {
    initButtons((ViewGroup) findViewById(containerResId), textColorStateList);
  }

  private void initButtons(ViewGroup container, ColorStateList textColorStateList) {
    for (int i = 0, count = container.getChildCount(); i < count; i++) {
      ((Button) container.getChildAt(i)).setTextColor(textColorStateList);
    }
  }

  @ColorInt
  private static int getThemeAttrColor(Context context, @AttrRes int colorAttr) {
    final TypedArray array = context.obtainStyledAttributes(null, new int[]{colorAttr});
    try {
      return array.getColor(0, 0);
    } finally {
      array.recycle();
    }
  }

  private static ColorStateList createColorStateList(Context context) {
    return new ColorStateList(
        new int[][]{
            new int[]{-android.R.attr.state_enabled}, // Disabled state.
            StateSet.WILD_CARD,                       // Enabled state.
        },
        new int[]{
            getThemeAttrColor(context, R.attr.colorAccent),  // Disabled state.
            getThemeAttrColor(context, R.attr.colorPrimary), // Enabled state.
        });
  }
}
