package com.jk.dayu.jkapp.BaseModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jk.dayu.jkapp.untils.StatusBarUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Note here that the call to setRootViewFitsSystemWindows inside winContent.getChildCount()=0 prevents the code from continuing.
        //是因为需要在setContentView之后才可以调用 setRootViewFitsSystemWindows
        //This is because setRootViewFitsSystemWindows can only be called after setContentView
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        //When FitsSystemWindows is set to true, the padding at the top of the screen will be reserved for the height of the status bar
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明Set the status bar to be transparent
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //The text and icons in the status bar of a normal phone are white, but if your application is also pure white, or the text in the status bar is not visible
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        //So if you are in this situation, please use the following code to set the status to use the dark text icon style, otherwise you can optionally comment out the if content
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //If you don't support setting a dark color style, you can't let the status bar be white for compatibility, so set a status bar color to translucent,
            //这样半透明+白=灰, 状态栏的文字能看得清
            //So that translucent + white = gray, the text in the status bar can be seen
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
    }
}
