// Generated code from Butter Knife. Do not modify!
package com.example.parking;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f0800f8;

  private View view7f08015e;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.emailEditText = Utils.findRequiredViewAsType(source, R.id.emailEditText, "field 'emailEditText'", EditText.class);
    target.passwordEditText = Utils.findRequiredViewAsType(source, R.id.passwordEditText, "field 'passwordEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.loginButton, "field 'loginButton' and method 'onLoginButtonClicked'");
    target.loginButton = Utils.castView(view, R.id.loginButton, "field 'loginButton'", Button.class);
    view7f0800f8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLoginButtonClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.registerButton, "field 'registerButton' and method 'onRegisterButtonClicked'");
    target.registerButton = Utils.castView(view, R.id.registerButton, "field 'registerButton'", Button.class);
    view7f08015e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRegisterButtonClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.emailEditText = null;
    target.passwordEditText = null;
    target.loginButton = null;
    target.registerButton = null;

    view7f0800f8.setOnClickListener(null);
    view7f0800f8 = null;
    view7f08015e.setOnClickListener(null);
    view7f08015e = null;
  }
}
