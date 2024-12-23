// Generated by view binder compiler. Do not edit!
package com.dicoding.bahasain.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.dicoding.bahasain.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentCulturalBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final MaterialButton btnFolkloreShowAll;

  @NonNull
  public final MaterialButton btnHistoricalShowAll;

  @NonNull
  public final MaterialButton btnRecipeShowAll;

  @NonNull
  public final RecyclerView rvFolklore;

  @NonNull
  public final RecyclerView rvHistorical;

  @NonNull
  public final RecyclerView rvRecipe;

  @NonNull
  public final TextView titleFolklore;

  @NonNull
  public final TextView titleHistorical;

  @NonNull
  public final TextView titlePage;

  @NonNull
  public final TextView titleRecipe;

  private FragmentCulturalBinding(@NonNull NestedScrollView rootView,
      @NonNull MaterialButton btnFolkloreShowAll, @NonNull MaterialButton btnHistoricalShowAll,
      @NonNull MaterialButton btnRecipeShowAll, @NonNull RecyclerView rvFolklore,
      @NonNull RecyclerView rvHistorical, @NonNull RecyclerView rvRecipe,
      @NonNull TextView titleFolklore, @NonNull TextView titleHistorical,
      @NonNull TextView titlePage, @NonNull TextView titleRecipe) {
    this.rootView = rootView;
    this.btnFolkloreShowAll = btnFolkloreShowAll;
    this.btnHistoricalShowAll = btnHistoricalShowAll;
    this.btnRecipeShowAll = btnRecipeShowAll;
    this.rvFolklore = rvFolklore;
    this.rvHistorical = rvHistorical;
    this.rvRecipe = rvRecipe;
    this.titleFolklore = titleFolklore;
    this.titleHistorical = titleHistorical;
    this.titlePage = titlePage;
    this.titleRecipe = titleRecipe;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentCulturalBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentCulturalBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_cultural, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentCulturalBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_folklore_show_all;
      MaterialButton btnFolkloreShowAll = ViewBindings.findChildViewById(rootView, id);
      if (btnFolkloreShowAll == null) {
        break missingId;
      }

      id = R.id.btn_historical_show_all;
      MaterialButton btnHistoricalShowAll = ViewBindings.findChildViewById(rootView, id);
      if (btnHistoricalShowAll == null) {
        break missingId;
      }

      id = R.id.btn_recipe_show_all;
      MaterialButton btnRecipeShowAll = ViewBindings.findChildViewById(rootView, id);
      if (btnRecipeShowAll == null) {
        break missingId;
      }

      id = R.id.rv_folklore;
      RecyclerView rvFolklore = ViewBindings.findChildViewById(rootView, id);
      if (rvFolklore == null) {
        break missingId;
      }

      id = R.id.rv_historical;
      RecyclerView rvHistorical = ViewBindings.findChildViewById(rootView, id);
      if (rvHistorical == null) {
        break missingId;
      }

      id = R.id.rv_recipe;
      RecyclerView rvRecipe = ViewBindings.findChildViewById(rootView, id);
      if (rvRecipe == null) {
        break missingId;
      }

      id = R.id.title_folklore;
      TextView titleFolklore = ViewBindings.findChildViewById(rootView, id);
      if (titleFolklore == null) {
        break missingId;
      }

      id = R.id.title_historical;
      TextView titleHistorical = ViewBindings.findChildViewById(rootView, id);
      if (titleHistorical == null) {
        break missingId;
      }

      id = R.id.title_page;
      TextView titlePage = ViewBindings.findChildViewById(rootView, id);
      if (titlePage == null) {
        break missingId;
      }

      id = R.id.title_recipe;
      TextView titleRecipe = ViewBindings.findChildViewById(rootView, id);
      if (titleRecipe == null) {
        break missingId;
      }

      return new FragmentCulturalBinding((NestedScrollView) rootView, btnFolkloreShowAll,
          btnHistoricalShowAll, btnRecipeShowAll, rvFolklore, rvHistorical, rvRecipe, titleFolklore,
          titleHistorical, titlePage, titleRecipe);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
