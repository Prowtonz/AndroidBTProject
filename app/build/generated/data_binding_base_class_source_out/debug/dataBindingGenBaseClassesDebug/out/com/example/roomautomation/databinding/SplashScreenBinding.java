// Generated by data binding compiler. Do not edit!
package com.example.roomautomation.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.example.roomautomation.R;
import com.example.roomautomation.SplashScreen;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class SplashScreenBinding extends ViewDataBinding {
  @NonNull
  public final ImageView splashScreenIcon;

  @NonNull
  public final RelativeLayout splashScreenRelativeLayout;

  @Bindable
  protected SplashScreen mSplashScreenLayout;

  protected SplashScreenBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView splashScreenIcon, RelativeLayout splashScreenRelativeLayout) {
    super(_bindingComponent, _root, _localFieldCount);
    this.splashScreenIcon = splashScreenIcon;
    this.splashScreenRelativeLayout = splashScreenRelativeLayout;
  }

  public abstract void setSplashScreenLayout(@Nullable SplashScreen splashScreenLayout);

  @Nullable
  public SplashScreen getSplashScreenLayout() {
    return mSplashScreenLayout;
  }

  @NonNull
  public static SplashScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.splash_screen, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static SplashScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<SplashScreenBinding>inflateInternal(inflater, R.layout.splash_screen, root, attachToRoot, component);
  }

  @NonNull
  public static SplashScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.splash_screen, null, false, component)
   */
  @NonNull
  @Deprecated
  public static SplashScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<SplashScreenBinding>inflateInternal(inflater, R.layout.splash_screen, null, false, component);
  }

  public static SplashScreenBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static SplashScreenBinding bind(@NonNull View view, @Nullable Object component) {
    return (SplashScreenBinding)bind(component, view, R.layout.splash_screen);
  }
}
