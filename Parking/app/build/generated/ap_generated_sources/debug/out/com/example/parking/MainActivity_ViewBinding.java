// Generated code from Butter Knife. Do not modify!
package com.example.parking;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.navigation.NavigationView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.dl = Utils.findRequiredViewAsType(source, R.layout.activity_main_menu, "field 'dl'", DrawerLayout.class);
    target.nv = Utils.findRequiredViewAsType(source, R.id.nv, "field 'nv'", NavigationView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.dl = null;
    target.nv = null;
  }
}
