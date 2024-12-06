// Generated by view binder compiler. Do not edit!
package com.dicoding.bahasain.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.dicoding.bahasain.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityOnBoardingBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnNext;

  @NonNull
  public final Guideline guidelineHorizontal1;

  @NonNull
  public final Guideline guidelineVertical1;

  @NonNull
  public final Guideline guidelineVertical2;

  @NonNull
  public final TabLayout indicator;

  @NonNull
  public final ViewPager2 viewPager;

  private ActivityOnBoardingBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnNext,
      @NonNull Guideline guidelineHorizontal1, @NonNull Guideline guidelineVertical1,
      @NonNull Guideline guidelineVertical2, @NonNull TabLayout indicator,
      @NonNull ViewPager2 viewPager) {
    this.rootView = rootView;
    this.btnNext = btnNext;
    this.guidelineHorizontal1 = guidelineHorizontal1;
    this.guidelineVertical1 = guidelineVertical1;
    this.guidelineVertical2 = guidelineVertical2;
    this.indicator = indicator;
    this.viewPager = viewPager;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOnBoardingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOnBoardingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_on_boarding, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOnBoardingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_next;
      Button btnNext = ViewBindings.findChildViewById(rootView, id);
      if (btnNext == null) {
        break missingId;
      }

      id = R.id.guideline_horizontal1;
      Guideline guidelineHorizontal1 = ViewBindings.findChildViewById(rootView, id);
      if (guidelineHorizontal1 == null) {
        break missingId;
      }

      id = R.id.guideline_vertical1;
      Guideline guidelineVertical1 = ViewBindings.findChildViewById(rootView, id);
      if (guidelineVertical1 == null) {
        break missingId;
      }

      id = R.id.guideline_vertical2;
      Guideline guidelineVertical2 = ViewBindings.findChildViewById(rootView, id);
      if (guidelineVertical2 == null) {
        break missingId;
      }

      id = R.id.indicator;
      TabLayout indicator = ViewBindings.findChildViewById(rootView, id);
      if (indicator == null) {
        break missingId;
      }

      id = R.id.viewPager;
      ViewPager2 viewPager = ViewBindings.findChildViewById(rootView, id);
      if (viewPager == null) {
        break missingId;
      }

      return new ActivityOnBoardingBinding((ConstraintLayout) rootView, btnNext,
          guidelineHorizontal1, guidelineVertical1, guidelineVertical2, indicator, viewPager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
