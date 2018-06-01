package com.ptpmcn.orderfood.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.ptpmcn.orderfood.R;
import com.ptpmcn.orderfood.fragment.HomeFragment;
import com.ptpmcn.orderfood.fragment.ListFoodLikedFragment;
import com.ptpmcn.orderfood.fragment.OrderTableFragment;
import com.ptpmcn.orderfood.fragment.ProfileFragment;
import com.ptpmcn.orderfood.utils.AccountUtil;
import com.ptpmcn.orderfood.utils.constant.Constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Stack;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    private Stack<Fragment> backStack;
    private boolean animating;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void addControls() {
        setTitle("Order Food");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ///////////////////////////////////////////////////////
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        CircleImageView img_profile = navigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        img_profile.setImageResource(R.drawable.ic_demo);
        TextView tv_name = navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        tv_name.setText(AccountUtil.fakeCustomer().getCustomer_name());

        if (backStack == null)
            backStack = new Stack<>();
        pushFragment(HomeFragment.newInstance(), -1);

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("tungts","dasdas");
        } catch (NoSuchAlgorithmException e) {
            Log.e("tungts","daaaaaaasdas");

        }
        FacebookSdk.sdkInitialize(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id){
            case R.id.menu_navi_home:
                pushFragment(HomeFragment.newInstance());
                break;
            case R.id.menu_navi_account:
                if (AccountUtil.getInstance(this).isLogin()){
                    pushFragment(ProfileFragment.newInstance());
                    return true;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }, 200);
                break;
            case R.id.menu_navi_delivery:
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this,ListFoodActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }
                },500);
                break;
            case R.id.menu_navi_order_table:
                pushFragment(OrderTableFragment.newInstance());
                break;
            case R.id.menu_navi_favorite:
                pushFragment(ListFoodLikedFragment.newInstance());
                break;
            case R.id.menu_navi_history:
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this,ListOrderOfCustomerActivity.class);
                        intent.setAction(Constant.Action.ACTION_HISTORY_ORDER);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }
                },500);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }

//        if (animating){
//            return;
//        }

        // Check if activity exists.
        // This is to prevent invoking "popFragment[ing]" from the activit.

//        Fragment fragment = backStack.peek();
//        if (fragment instanceof ProfileFragment
//                || fragment instanceof ReservationFragment ) {
//            popFragment(R.anim.slide_out);
//        }
//
//        if(fragment instanceof HomeFragment){
//            finish();
//        }
//        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.action_search:
                intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
//            case R.id.action_notification:
//                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pushFragment(Fragment fragment) {
        pushFragment(fragment, R.anim.fade_in);
    }

    public void pushFragment(Fragment fragment,int animin){
        Fragment currentFragment = backStack.isEmpty() ? null : backStack.peek();
        backStack.push(fragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        animating = true;
        if (animin != -1){
            ft.setCustomAnimations(animin,0);
        }

        if (currentFragment != null){
            ft.remove(currentFragment);
        }
//        hideKeyboard();
        ft.replace(R.id.content_main, fragment, fragment.getClass().getName()).addToBackStack(null).commitAllowingStateLoss();
        animating = false;
    }

//    public void popFragment() {
//        popFragment(R.anim.fade_out);
//    }
//
//    public void popFragment(int animateOut) {
//        popFragment(animateOut, 1);
//    }
//
//    public void popFragment(int animout, int depth){
//        if (this == null){
//            return;
//        }
//
//        if (depth < 1){
//            return;
//        }
//
//        Fragment currentFragment = backStack.isEmpty() ? null : backStack.peek();
//        if (backStack.size() < 2){
//            finish();
//            return;
//        }
////        hideKeyboard();
//
//        while (depth > 0){
//            Fragment next = backStack.pop();
//            depth--;
//            if (backStack.size() < 2){
//                break;
//            }
//        }
//
//        /*
//         * Get the updated fragment. This should be below than currentFragment, if depth >=1
//         */
//
//        Fragment fragment = backStack.peek();
//        animating = true;
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        if (animout != -1){
//            ft.setCustomAnimations(0,animout);
//        }
//
//        if (currentFragment != null){
//            ft.remove(currentFragment);
//        }
//        ft.add(R.id.content_main,fragment, fragment.getClass().getName()).addToBackStack(null).commitNowAllowingStateLoss();
//        if (this == null){
//            return;
//        }
//        getSupportFragmentManager().executePendingTransactions();
//        animating = false;
//    }

}
