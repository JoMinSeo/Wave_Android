package com.narsha.wave_android.view.fragment.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.narsha.wave_android.R;
import com.narsha.wave_android.data.Result;
import com.narsha.wave_android.data.User;
import com.narsha.wave_android.data.request.signup.UserSignUp;
import com.narsha.wave_android.network.Server;
import com.narsha.wave_android.view.activity.main.MainActivity;
import com.narsha.wave_android.view.activity.signup.SignUpActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    Call<Result> request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputEditText edit_id = view.findViewById(R.id.id_regi);
        TextInputEditText edit_pwd = view.findViewById(R.id.pwd_regi);
        TextInputEditText edit_pwd_check = view.findViewById(R.id.pwd_check);
        TextInputEditText edit_name = view.findViewById(R.id.name_regi);
        TextInputEditText edit_email = view.findViewById(R.id.email_regi);

        Button next = view.findViewById(R.id.next);

        next.setOnClickListener(view1 -> {


            if (edit_pwd.getText().toString().equals(edit_pwd_check.getText().toString())) {

                UserSignUp user = new UserSignUp(edit_id.getText().toString(), edit_pwd.getText().toString(), edit_email.getText().toString(), edit_name.getText().toString());

                request = Server.getInstance().getApi().signUp(user);
                request.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.i("test", response.code() + "");
                        if (response.code() == 200) {
                            Result result = response.body();
                            if (result.getResult().equals("ok")) {
                                Log.i("test", result.getResult());
                                NavController controller = Navigation.findNavController(view);
                                controller.navigate(R.id.action_signUpFragment_to_songSelectFragment1);
                            } else {
                                Toast.makeText(getContext(), "이미 사용중인 아이디입니다.", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        t.printStackTrace();
                        Log.i("test", "failed");
                    }
                });
            }

        });
    }
}
