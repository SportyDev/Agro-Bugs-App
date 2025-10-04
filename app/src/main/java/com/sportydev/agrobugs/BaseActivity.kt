package com.sportydev.agrobugs
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding

abstract class BaseActivity : AppCompatActivity() {

    /**
     * PlANTILLA DONDE LOS ACTIVITYS HEREDAN EL MODO FULLSCREEN
     */
    protected open fun getPaddingTargetView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Habilita el modo Edge-to-Edge para dibujar detrás de las barras
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 2. Oculta la barra de navegación y de estado
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // 3. Lógica para el padding dinámico
        val targetView = getPaddingTargetView()
        if (targetView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(targetView) { view, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.updatePadding(top = insets.top)
                WindowInsetsCompat.CONSUMED
            }
        }
    }
}