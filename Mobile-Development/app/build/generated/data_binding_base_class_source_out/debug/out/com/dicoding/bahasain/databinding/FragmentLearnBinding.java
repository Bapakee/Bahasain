// Generated by view binder compiler. Do not edit!
package com.dicoding.bahasain.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.dicoding.bahasain.R;
import java.lang.Override;

public final class FragmentLearnBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout-v28/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   */
  @Nullable
  public final ImageButton btnNotification;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout-v28/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   */
  @Nullable
  public final LinearLayout nav;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout-v28/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   */
  @Nullable
  public final LinearLayout point;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout-v28/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   */
  @Nullable
  public final LinearLayout streak;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout-v28/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   */
  @Nullable
  public final Button test;

  private FragmentLearnBinding(@NonNull ConstraintLayout rootView,
      @Nullable ImageButton btnNotification, @Nullable LinearLayout nav,
      @Nullable LinearLayout point, @Nullable LinearLayout streak, @Nullable Button test) {
    this.rootView = rootView;
    this.btnNotification = btnNotification;
    this.nav = nav;
    this.point = point;
    this.streak = streak;
    this.test = test;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLearnBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLearnBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_learn, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLearnBinding bind(@NonNull View rootView) {
    ImageButton btnNotification = ViewBindings.findChildViewById(rootView, R.id.btn_notification);

    LinearLayout nav = ViewBindings.findChildViewById(rootView, R.id.nav);

    LinearLayout point = ViewBindings.findChildViewById(rootView, R.id.point);

    LinearLayout streak = ViewBindings.findChildViewById(rootView, R.id.streak);

    Button test = ViewBindings.findChildViewById(rootView, R.id.test);

    return new FragmentLearnBinding((ConstraintLayout) rootView, btnNotification, nav, point,
        streak, test);
  }
}
