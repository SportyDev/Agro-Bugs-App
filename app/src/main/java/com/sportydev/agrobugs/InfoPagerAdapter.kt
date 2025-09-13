package com.sportydev.agrobugs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class InfoPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Lista de contenido para cada pestaña
    private val tabContents = listOf(
        "El gusano del elote es una plaga polífaga que afecta a una amplia gama de cultivos, incluyendo maíz, tomate, algodón y otros. Las larvas se alimentan de las partes reproductivas de las plantas, causando daños significativos.",
        "Los daños principales incluyen la destrucción de granos de maíz, perforaciones en tomates y daños en los capullos de algodón, lo que reduce drásticamente el rendimiento y la calidad de la cosecha.",
        "El control se puede realizar mediante el uso de insecticidas específicos, la liberación de enemigos naturales (control biológico), y prácticas culturales como la rotación de cultivos y la eliminación de rastrojos."
    )

    // Número total de pestañas
    override fun getItemCount(): Int = tabContents.size

    // Crea el fragmento para la posición dada
    override fun createFragment(position: Int): Fragment {
        return InfoTabFragment.newInstance(tabContents[position])
    }
}