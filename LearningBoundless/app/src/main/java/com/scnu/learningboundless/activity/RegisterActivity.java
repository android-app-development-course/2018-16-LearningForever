package com.scnu.learningboundless.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scnu.learningboundless.R;
import com.scnu.learningboundless.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_user_name)
    protected EditText mEtUserName;

    @BindView(R.id.et_password)
    protected EditText mEtPassword;

    @BindView(R.id.et_confirm_password)
    protected EditText mEtConfirmPassword;

    @BindView(R.id.btn_register)
    protected Button mBtnRegister;

    @BindView(R.id.fab_x)
    protected FloatingActionButton mFabX;

    @BindView(R.id.cv_register)
    protected CardView mCvRegister;


    /**
     * 得到当前Activity对应的布局文件的id
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        ShowEnterAnimation();
    }

    @OnClick({R.id.btn_register, R.id.fab_x})
    public void handleAllClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register:
                register();
                break;

            case R.id.fab_x:
                animateRevealClose();
                break;

            default:
                break;
        }
    }

    /**
     * 执行注册操作
     */
    private void register() {

        String userName = mEtUserName.getText().toString();
        String password = mEtPassword.getText().toString();
        String confirmPassword = mEtConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, getResources().getString(R.string.not_empty_user_name_or_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, getResources().getString(R.string.confirm_error), Toast.LENGTH_SHORT).show();
            return;
        }
    }


    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fab_transition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                mCvRegister.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }

        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCvRegister, mCvRegister.getWidth() / 2, 0, mFabX.getWidth() / 2, mCvRegister.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCvRegister.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCvRegister, mCvRegister.getWidth() / 2, 0, mCvRegister.getHeight(), mFabX.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCvRegister.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                mFabX.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }


    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }


    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
