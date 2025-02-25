// Generated by view binder compiler. Do not edit!
package com.example.to_do_list.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.to_do_list.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSignUpBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button RegButton;

  @NonNull
  public final TextView authTextView;

  @NonNull
  public final MaterialCardView cardEmail;

  @NonNull
  public final MaterialCardView cardPassword;

  @NonNull
  public final MaterialCardView cardPassword2;

  @NonNull
  public final TextInputEditText emailText;

  @NonNull
  public final TextInputEditText passwrodText;

  @NonNull
  public final TextInputEditText passwrodText2;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView signUpTextView;

  private FragmentSignUpBinding(@NonNull ConstraintLayout rootView, @NonNull Button RegButton,
      @NonNull TextView authTextView, @NonNull MaterialCardView cardEmail,
      @NonNull MaterialCardView cardPassword, @NonNull MaterialCardView cardPassword2,
      @NonNull TextInputEditText emailText, @NonNull TextInputEditText passwrodText,
      @NonNull TextInputEditText passwrodText2, @NonNull ProgressBar progressBar,
      @NonNull TextView signUpTextView) {
    this.rootView = rootView;
    this.RegButton = RegButton;
    this.authTextView = authTextView;
    this.cardEmail = cardEmail;
    this.cardPassword = cardPassword;
    this.cardPassword2 = cardPassword2;
    this.emailText = emailText;
    this.passwrodText = passwrodText;
    this.passwrodText2 = passwrodText2;
    this.progressBar = progressBar;
    this.signUpTextView = signUpTextView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.RegButton;
      Button RegButton = ViewBindings.findChildViewById(rootView, id);
      if (RegButton == null) {
        break missingId;
      }

      id = R.id.authTextView;
      TextView authTextView = ViewBindings.findChildViewById(rootView, id);
      if (authTextView == null) {
        break missingId;
      }

      id = R.id.cardEmail;
      MaterialCardView cardEmail = ViewBindings.findChildViewById(rootView, id);
      if (cardEmail == null) {
        break missingId;
      }

      id = R.id.cardPassword;
      MaterialCardView cardPassword = ViewBindings.findChildViewById(rootView, id);
      if (cardPassword == null) {
        break missingId;
      }

      id = R.id.cardPassword2;
      MaterialCardView cardPassword2 = ViewBindings.findChildViewById(rootView, id);
      if (cardPassword2 == null) {
        break missingId;
      }

      id = R.id.emailText;
      TextInputEditText emailText = ViewBindings.findChildViewById(rootView, id);
      if (emailText == null) {
        break missingId;
      }

      id = R.id.passwrodText;
      TextInputEditText passwrodText = ViewBindings.findChildViewById(rootView, id);
      if (passwrodText == null) {
        break missingId;
      }

      id = R.id.passwrodText2;
      TextInputEditText passwrodText2 = ViewBindings.findChildViewById(rootView, id);
      if (passwrodText2 == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.signUpTextView;
      TextView signUpTextView = ViewBindings.findChildViewById(rootView, id);
      if (signUpTextView == null) {
        break missingId;
      }

      return new FragmentSignUpBinding((ConstraintLayout) rootView, RegButton, authTextView,
          cardEmail, cardPassword, cardPassword2, emailText, passwrodText, passwrodText2,
          progressBar, signUpTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
