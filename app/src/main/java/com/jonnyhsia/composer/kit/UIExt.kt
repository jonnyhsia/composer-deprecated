package com.jonnyhsia.composer.kit

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView.ViewHolder
import android.text.Editable
import android.text.TextWatcher
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jonnyhsia.composer.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

typealias SimpleClick = (View) -> Unit
typealias ItemClick = (pos: Int) -> Unit

fun Activity.navigate(pageUriString: String) {
    Router.navigate(this, pageUriString)
}

fun Activity.setWindowBackground(drawable: Drawable?) {
    window.setBackgroundDrawable(drawable)
}

fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Fragment.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    activity?.toast(text, duration)
}

@ColorInt
fun Fragment.getColor(@ColorRes colorRes: Int): Int {
    return resources.getColor(colorRes)
}

fun AppCompatActivity.replaceFragment(@IdRes containerId: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        replace(containerId, fragment, tag)
    }
}

fun FragmentTransaction.addOrShowFragment(@IdRes containerId: Int, fragment: Fragment, tag: String) {
    if (!fragment.isAdded) {
        // 添加 Fragment
        add(containerId, fragment, tag)
    } else if (fragment.isHidden) {
        show(fragment)
    } else {
        Debug.w("既不添加也不显示 Fragment")
    }
}

inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun EditText.takeText() = text.toString()

/**
 * 通过 [RxView] 实现的 View 的多次点击事件
 *
 * @param multiClicked 成功回调
 * @param clickTimes   点击的次数 (默认 3 次)
 * @param timeLimit    时间限制(默认 1000ms)
 */
fun View.multiClick(multiClicked: (View) -> Unit, clickTimes: Int = 3, timeLimit: Long = 1000) {
    RxView.clicks(this)
            .buffer(timeLimit, TimeUnit.MILLISECONDS, clickTimes)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.size >= clickTimes) {
                    multiClicked(this)
                }
            }
}

/**
 * 通过 [GestureDetector] 实现的双击监听
 *
 * @param doubleTap 双击回调
 */
fun View.doubleTap(doubleTap: (View) -> Unit) {
    val detector = GestureDetector(context, GestureDetector.SimpleOnGestureListener())

    detector.setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            doubleTap(this@doubleTap)
            return false
        }

        override fun onDoubleTapEvent(e: MotionEvent?) = false

        override fun onSingleTapConfirmed(e: MotionEvent?) = false
    })

    setOnTouchListener { _, event ->
        detector.onTouchEvent(event)
        true
    }
}

fun <T : View> ViewHolder.find(@IdRes id: Int): T? = itemView.findViewById(id)

fun ViewHolder.setText(@IdRes id: Int, text: CharSequence): ViewHolder {
    val textView = find<TextView>(id)
    textView ?: Debug.e("没有找到 ID 对应的 TextView")
    textView?.text = text

    return this
}

fun ViewHolder.setOnClickListener(@IdRes id: Int, onClick: SimpleClick) {
    find<View>(id)?.setOnClickListener(onClick)
}

val ViewHolder.context: Context
    get() = itemView.context

/**
 * 给 EditText 设置文字监听
 *
 * @param watcher  回调
 * @param debounce 回调的最短时间间隔, 单位毫秒, 默认100ms
 */
inline fun EditText.addTextWatcher(crossinline watcher: (TextView, Editable?) -> Unit, debounce: Long = 100L) {
    RxTextView.afterTextChangeEvents(this)
            .throttleFirst(debounce, TimeUnit.MILLISECONDS)
            .subscribe {
                watcher(it.view(), it.editable())
            }
}

inline fun EditText.addTextChangedListener(crossinline callback: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            this@addTextChangedListener.context.toast("Hello?")
            callback(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}