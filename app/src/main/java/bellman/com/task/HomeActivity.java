package bellman.com.task;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import bellman.com.task.adapters.CategoryRecyclerAdapter;
import bellman.com.task.viewmodels.HomeViewModel;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "HomeActivity";


    private boolean fabIsOpen = false;
    private LinearLayout fbMap, fbAttraction, fbEvents, fbHotSpots;
    private ConstraintLayout constAppBarLayout;
    private ConstraintLayout constAppBottomLayout;
    private FloatingActionButton fbBellMan;
    private ScrollView scrollView;
    private HomeViewModel homeViewModel;
    private RecyclerView rvAttractions, rvHotSpot;
    private CategoryRecyclerAdapter hotSpotAdapter, attractionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViewModel();
        initViews();
        subscribeObservers();
    }

    private void initViewModel() {
        homeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(HomeViewModel.class);
    }

    private void initViews() {

        //ScrollView ,Linear layouts, and floating buttons
        fbMap = findViewById(R.id.layout_fb_map);
        fbAttraction = findViewById(R.id.layout_fb_attraction);
        fbEvents = findViewById(R.id.layout_fb_events);
        fbHotSpots = findViewById(R.id.layout_fb_hotspots);

        fbBellMan = findViewById(R.id.fb_main_menu);
        fbBellMan.setOnClickListener(this);

        scrollView = findViewById(R.id.layout_body);

        constAppBarLayout = findViewById(R.id.app_bar_layout);
        constAppBottomLayout = findViewById(R.id.layout_navigation_bottom);

        //Recycler views and adapters
        rvAttractions = findViewById(R.id.rv_attractions);
        rvHotSpot = findViewById(R.id.rv_hotspots);

        hotSpotAdapter = new CategoryRecyclerAdapter(1);
        attractionsAdapter = new CategoryRecyclerAdapter(3);

        tvErrorMessage.setOnClickListener(this);

    }

    private void subscribeObservers() {
        homeViewModel.getAttractions().observe(this, resource -> {
            if (resource != null) {
                if (resource.data != null) {
                    switch (resource.status) {
                        case LOADING:
                            Log.d(TAG, "onChanged: status :Loading ");
                            showProgressBar(View.VISIBLE);
                            showParent(View.GONE);
                            showErrorMessage(View.GONE);
                            break;
                        case SUCCESS:
                            Log.d(TAG, "onChanged: status :Success " + resource.message);
                            showParent(View.VISIBLE);
                            showErrorMessage(View.GONE);
                            showProgressBar(View.GONE);
                            attractionsAdapter.setAttractionList(resource.data);
                            rvAttractions.setAdapter(attractionsAdapter);
                            break;
                        case ERROR:
                            Log.d(TAG, "onChanged: status :ErrorMessage" + resource.message);
                            if (resource.data.size() == 0) {
                                showParent(View.GONE);
                                showProgressBar(View.GONE);
                                showErrorMessage(View.VISIBLE);
                            } else {
                                showErrorMessage(View.GONE);
                                showParent(View.VISIBLE);
                                showProgressBar(View.GONE);
                            }
                            //  Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show();
                            attractionsAdapter.setAttractionList(resource.data);
                            rvAttractions.setAdapter(attractionsAdapter);
                            break;

                    }
                }
            }
        });
        homeViewModel.getHotSpots().observe(this, resource -> {
            if (resource != null) {
                if (resource.data != null) {
                    hotSpotAdapter.setHotSpotList(resource.data);
                    rvHotSpot.setAdapter(hotSpotAdapter);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_main_menu:
                if (fabIsOpen) {
                    fbBellMan.setEnabled(false);
                    closeMenu(fbMap, 100);
                    closeMenu(fbAttraction, 200);
                    closeMenu(fbEvents, 300);
                    closeMenu(fbHotSpots, 400);

                    // Enable button and remove the transparent layer
                    enableBellButton();
                } else {
                    scrollView.setAlpha(0.5f);
                    constAppBarLayout.setAlpha(0.5f);
                    constAppBottomLayout.setAlpha(0.5f);
                    openMenu(fbHotSpots, 100);
                    openMenu(fbEvents, 200);
                    openMenu(fbAttraction, 300);
                    openMenu(fbMap, 400);
                }
                break;
            case R.id.tv_error_message:
                homeViewModel.getAttractions().removeObservers(this);
                homeViewModel.getHotSpots().removeObservers(this);
                subscribeObservers();
                break;

        }
    }

    private void enableBellButton() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            fbBellMan.setEnabled(true);
            constAppBarLayout.setAlpha(1);
            constAppBottomLayout.setAlpha(1);
            scrollView.setAlpha(1);
        }, 400);
    }

    private void closeMenu(LinearLayout fb, int milliSec) {
        fabIsOpen = false;
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // Hide
            fb.animate().alpha(0);
        }, milliSec);
    }

    private void openMenu(LinearLayout fb, int milliSec) {
        fabIsOpen = true;
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // Show
            fb.animate().alpha(1);

        }, milliSec);
    }

    private void showParent(int visibility) {
        scrollView.setVisibility(visibility);
    }
}
