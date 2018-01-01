package com.jonnyhsia.composer.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jonnyhsia.composer.page.base.BasePresenter;
import com.jonnyhsia.composer.page.base.BaseView;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

/**
 * @author JonnyHsia on 18/1/1.
 */

public class VPFragmentPagerItem extends FragmentPagerItem {

    private final BasePresenter mPresenter;

    public VPFragmentPagerItem(CharSequence title, float width,
            String className, Bundle args, BasePresenter presenter) {
        super(title, width, className, args);
        mPresenter = presenter;
    }

    @Override
    public Fragment instantiate(Context context, int position) {
        Fragment fragment = super.instantiate(context, position);

        if (fragment instanceof BaseView) {
            ((BaseView) fragment).bindPresenter(mPresenter);
        }

        return fragment;
    }
}
