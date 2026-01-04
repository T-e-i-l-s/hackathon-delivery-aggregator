package com.team.uikit.presentation.vibration

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.VibratorManager
import androidx.annotation.RequiresPermission

/**
 * # ProdUiVibrator
 *
 * Provides simple methods for single and double short vibrations using predefined vibration effects.
 *
 * ⚠️ **Note:** Devices with API level below 31 will not vibrate because they lack haptic feedback support in this implementation.
 *
 * @param context The application [Context] used to retrieve the system [VibratorManager].
 *
 * @see android.os.VibrationEffect
 * @see android.os.VibratorManager
 */
class ProdUiVibrator(context: Context) {
    private val vibrator =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            null
        }

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun shortSingleVibration() {
        if (vibrator != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
            vibrator.vibrate(effect)
        }
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun shortDoubleVibration() {
        if (vibrator != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
            vibrator.vibrate(effect)
        }
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun longVibration(duration: Long) {
        if (vibrator != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val effect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        }
    }
}
