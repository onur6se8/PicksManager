package com.jeupouradulte.monpoker;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.jeupouradulte.monpoker.base.BaseActivity;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 123;

    @BindView(R.id.containerParent)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.main_activity_button_login)
    public Button buttonLogin;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);  // Réponse de connexion

        if (this.isCurrentUserLogged()){
            this.startProfileActivity();
        }
    }


    @OnClick(R.id.main_activity_button_login)
    public void onClickLoginButton() {

        this.startSignInActivity();
    }


    private void startSignInActivity(){                                                                  // Lance une activité de connexion/inscription
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList( new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(), //EMAIL
                                               new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))  // Google
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_menu_camera)
                        .build(),
                RC_SIGN_IN);
    }

    private void startProfileActivity(){                                          // lancemement de Home
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }



    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message){                  // Snackbar de réponse de connexion
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){            // Réponse de connexion

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {       // Réussi
                showSnackBar(this.coordinatorLayout, getString(R.string.connection_succeed));
            }
            else {                               // Erreur
                if (response == null) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_authentication_canceled));
                } else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_no_internet));
                } else if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_unknown_error));
                }
            }
        }
    }
}

