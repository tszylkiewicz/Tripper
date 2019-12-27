package com.example.tripper.fragment.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tripper.R;
import com.example.tripper.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    private UserViewModel userViewModel;

    private TextInputLayout username;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout confirmPassword;
    private Button signUp;

    private TextWatcher textWatcher;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);

        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        signUp = view.findViewById(R.id.signUp);
        signUp.setEnabled(false);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFields();
            }

        };

        username.getEditText().addTextChangedListener(textWatcher);
        email.getEditText().addTextChangedListener(textWatcher);
        password.getEditText().addTextChangedListener(textWatcher);
        confirmPassword.getEditText().addTextChangedListener(textWatcher);

        final NavController navController = Navigation.findNavController(view);

        signUp.setOnClickListener(view1 -> {
            userViewModel.signUp(email.getEditText().getText().toString(), username.getEditText().getText().toString(), password.getEditText().getText().toString());
            Toast.makeText(this.getContext(), "Signed up successfully", Toast.LENGTH_LONG).show();
            navController.navigate(R.id.nav_map, null);
        });
    }

    private void checkFields() {
        boolean usernameValid = false;
        boolean emailValid = false;
        boolean passwordValid = false;
        boolean confirmPasswordValid = false;

        if (!isValidUsername(username.getEditText().getText().toString())) {
            username.setError("Enter a valid username");
        } else {
            username.setError(null);
            usernameValid = true;
        }
        if (!isValidEmail(email.getEditText().getText().toString())) {
            email.setError("Enter a valid address");
        } else {
            email.setError(null);
            emailValid = true;
        }
        if (!isValidPassword(password.getEditText().getText().toString())) {
            password.setError("Enter a valid password");
        } else {
            password.setError(null);
            passwordValid = true;
        }
        if (!password.getEditText().getText().toString().equals(confirmPassword.getEditText().getText().toString())) {
            confirmPassword.setError("Enter a valid password");
        } else {
            confirmPassword.setError(null);
            confirmPasswordValid = true;
        }
        if (emailValid && passwordValid && usernameValid && confirmPasswordValid) {
            signUp.setEnabled(true);
        } else {
            signUp.setEnabled(false);
        }
    }

    private boolean isValidUsername(String target) {
        return target != null && !target.equals("");
    }

    private boolean isValidPassword(String target) {
        if (target == null || target.equals("")) {
            return false;
        } else {
            Pattern pattern;
            Matcher matcher;

            final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%.,]).{6,20})";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(target);
            System.out.println(matcher.matches());
            return matcher.matches();
        }
    }

    private boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}
