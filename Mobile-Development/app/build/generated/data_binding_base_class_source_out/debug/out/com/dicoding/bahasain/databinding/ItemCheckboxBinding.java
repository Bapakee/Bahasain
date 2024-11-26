// Generated by view binder compiler. Do not edit!
package com.dicoding.bahasain.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.dicoding.bahasain.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class ItemCheckboxBinding implements ViewBinding {
  @NonNull
  private final CheckBox rootView;

  private ItemCheckboxBinding(@NonNull CheckBox rootView) {
    this.rootView = rootView;
  }

  @Override
  @NonNull
  public CheckBox getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemCheckboxBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemCheckboxBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_checkbox, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemCheckboxBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    return new ItemCheckboxBinding((CheckBox) rootView);
  }
}
