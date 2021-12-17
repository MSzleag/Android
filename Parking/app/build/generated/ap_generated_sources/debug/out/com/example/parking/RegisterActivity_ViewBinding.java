// Generated code from Butter Knife. Do not modify!
package com.example.parking;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target, View source) {
    this.target = target;

    target.firstNameEditText = Utils.findRequiredViewAsType(source, R.id.firstNameEditText, "field 'firstNameEditText'", EditText.class);
    target.emailEditText = Utils.findRequiredViewAsType(source, R.id.emailEditText, "field 'emailEditText'", EditText.class);
    target.passwordEditText = Utils.findRequiredViewAsType(source, R.id.passwordEditText, "field 'passwordEditText'", EditText.class);
    target.lastNameEditText = Utils.findRequiredViewAsType(source, R.id.lastNameEditText, "field 'lastNameEditText'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.firstNameEditText = null;
    target.emailEditText = null;
    target.passwordEditText = null;
    target.lastNameEditText = null;
  }
}
