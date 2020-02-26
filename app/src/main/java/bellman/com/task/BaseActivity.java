package bellman.com.task;

import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    public TextView tvErrorMessage;

    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        tvErrorMessage = constraintLayout.findViewById(R.id.tv_error_message);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);
    }

    public void showErrorMessage(int visibility) {
        tvErrorMessage.setVisibility(visibility);
    }

    public void showProgressBar(int visibility) {
        mProgressBar.setVisibility(visibility);
    }
}
