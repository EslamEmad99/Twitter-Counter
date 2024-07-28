package com.halan.twitter_counter.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.halan.twitter_counter.R
import es.dmoral.toasty.Toasty

fun Context.showToast(
    message: String?,
    toastType: ToastType = ToastType.ERROR,
    withIcon: Boolean = true
) {
    if (message.isNullOrEmpty()) return
    when (toastType) {
        ToastType.SUCCESS -> {
            Toasty.success(this, message, Toast.LENGTH_SHORT, withIcon).show()
        }

        ToastType.ERROR -> {
            Toasty.error(this, message, Toast.LENGTH_SHORT, withIcon).show()
        }

        ToastType.INFO -> {
            Toasty.info(this, message, Toast.LENGTH_SHORT, withIcon).show()
        }

        ToastType.WARNING -> {
            Toasty.warning(this, message, Toast.LENGTH_SHORT, withIcon).show()
        }
    }
}

fun Fragment.toasty(
    message: String,
    toastType: ToastType = ToastType.ERROR,
    withIcon: Boolean = true
) {
    when (toastType) {
        ToastType.SUCCESS -> {
            Toasty.success(requireContext(), message, Toast.LENGTH_SHORT, withIcon).show()
        }

        ToastType.ERROR -> {
            Toasty.error(requireContext(), message, Toast.LENGTH_SHORT, withIcon).show()
        }

        ToastType.INFO -> {
            Toasty.info(requireContext(), message, Toast.LENGTH_SHORT, withIcon).show()
        }

        ToastType.WARNING -> {
            Toasty.warning(requireContext(), message, Toast.LENGTH_SHORT, withIcon).show()
        }
    }
}

fun Fragment.toasty(
    @StringRes message: Int,
    toastType: ToastType = ToastType.ERROR,
    withIcon: Boolean = true
) {
    when (toastType) {
        ToastType.SUCCESS -> {
            Toasty.success(requireContext(), getString(message), Toast.LENGTH_SHORT, withIcon)
                .show()
        }

        ToastType.ERROR -> {
            Toasty.error(requireContext(), getString(message), Toast.LENGTH_SHORT, withIcon).show()
        }

        ToastType.INFO -> {
            Toasty.info(requireContext(), getString(message), Toast.LENGTH_SHORT, withIcon).show()
        }

        ToastType.WARNING -> {
            Toasty.warning(requireContext(), getString(message), Toast.LENGTH_SHORT, withIcon)
                .show()
        }
    }
}

fun Context.toastNoInternetConnection() {
    Toasty.error(
        this,
        getString(R.string.no_internet_connection),
        Toast.LENGTH_SHORT,
        true
    ).show()
}

enum class ToastType {
    SUCCESS, ERROR, WARNING, INFO
}
