// Generated by view binder compiler. Do not edit!
package com.dicoding.bahasain.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bahasain.ui.view.ButtonSign;
import com.bahasain.ui.view.EditTextEmail;
import com.bahasain.ui.view.EditTextName;
import com.bahasain.ui.view.EditTextPassword;
import com.dicoding.bahasain.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FrameLayout backgroundSign;

  @NonNull
  public final TextView btnLogin;

  @NonNull
  public final ButtonSign btnRegister;

  @NonNull
  public final TextInputLayout edtEmailLayout;

  @NonNull
  public final TextInputLayout edtNameLayout;

  @NonNull
  public final TextInputLayout edtPasswordConfirmLayout;

  @NonNull
  public final TextInputLayout edtPasswordLayout;

  @NonNull
  public final EditTextPassword edtTextConfirmPassword;

  @NonNull
  public final EditTextEmail edtTextEmail;

  @NonNull
  public final EditTextName edtTextName;

  @NonNull
  public final EditTextPassword edtTextPassword;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView titleSignup;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView,
      @NonNull FrameLayout backgroundSign, @NonNull TextView btnLogin,
      @NonNull ButtonSign btnRegister, @NonNull TextInputLayout edtEmailLayout,
      @NonNull TextInputLayout edtNameLayout, @NonNull TextInputLayout edtPasswordConfirmLayout,
      @NonNull TextInputLayout edtPasswordLayout, @NonNull EditTextPassword edtTextConfirmPassword,
      @NonNull EditTextEmail edtTextEmail, @NonNull EditTextName edtTextName,
      @NonNull EditTextPassword edtTextPassword, @NonNull ConstraintLayout main,
      @NonNull ProgressBar progressBar, @NonNull TextView titleSignup) {
    this.rootView = rootView;
    this.backgroundSign = backgroundSign;
    this.btnLogin = btnLogin;
    this.btnRegister = btnRegister;
    this.edtEmailLayout = edtEmailLayout;
    this.edtNameLayout = edtNameLayout;
    this.edtPasswordConfirmLayout = edtPasswordConfirmLayout;
    this.edtPasswordLayout = edtPasswordLayout;
    this.edtTextConfirmPassword = edtTextConfirmPassword;
    this.edtTextEmail = edtTextEmail;
    this.edtTextName = edtTextName;
    this.edtTextPassword = edtTextPassword;
    this.main = main;
    this.progressBar = progressBar;
    this.titleSignup = titleSignup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.background_sign;
      FrameLayout backgroundSign = ViewBindings.findChildViewById(rootView, id);
      if (backgroundSign == null) {
        break missingId;
      }

      id = R.id.btn_login;
      TextView btnLogin = ViewBindings.findChildViewById(rootView, id);
      if (btnLogin == null) {
        break missingId;
      }

      id = R.id.btn_register;
      ButtonSign btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.edt_email_layout;
      TextInputLayout edtEmailLayout = ViewBindings.findChildViewById(rootView, id);
      if (edtEmailLayout == null) {
        break missingId;
      }

      id = R.id.edt_name_layout;
      TextInputLayout edtNameLayout = ViewBindings.findChildViewById(rootView, id);
      if (edtNameLayout == null) {
        break missingId;
      }

      id = R.id.edt_password_confirm_layout;
      TextInputLayout edtPasswordConfirmLayout = ViewBindings.findChildViewById(rootView, id);
      if (edtPasswordConfirmLayout == null) {
        break missingId;
      }

      id = R.id.edt_password_layout;
      TextInputLayout edtPasswordLayout = ViewBindings.findChildViewById(rootView, id);
      if (edtPasswordLayout == null) {
        break missingId;
      }

      id = R.id.edt_text_confirm_password;
      EditTextPassword edtTextConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (edtTextConfirmPassword == null) {
        break missingId;
      }

      id = R.id.edt_text_email;
      EditTextEmail edtTextEmail = ViewBindings.findChildViewById(rootView, id);
      if (edtTextEmail == null) {
        break missingId;
      }

      id = R.id.edt_text_name;
      EditTextName edtTextName = ViewBindings.findChildViewById(rootView, id);
      if (edtTextName == null) {
        break missingId;
      }

      id = R.id.edt_text_password;
      EditTextPassword edtTextPassword = ViewBindings.findChildViewById(rootView, id);
      if (edtTextPassword == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.title_signup;
      TextView titleSignup = ViewBindings.findChildViewById(rootView, id);
      if (titleSignup == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, backgroundSign, btnLogin,
          btnRegister, edtEmailLayout, edtNameLayout, edtPasswordConfirmLayout, edtPasswordLayout,
          edtTextConfirmPassword, edtTextEmail, edtTextName, edtTextPassword, main, progressBar,
          titleSignup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
