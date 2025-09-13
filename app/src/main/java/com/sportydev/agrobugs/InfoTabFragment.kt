package com.sportydev.agrobugs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class InfoTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.fragment_info_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtiene el texto de los argumentos y lo pone en el TextView
        val content = arguments?.getString(ARG_CONTENT)
        view.findViewById<TextView>(R.id.tvContent).text = content
    }

    companion object {
        private const val ARG_CONTENT = "content"

        // Función para crear una nueva instancia del fragmento con el texto deseado
        fun newInstance(content: String): InfoTabFragment {
            val fragment = InfoTabFragment()
            val args = Bundle()
            args.putString(ARG_CONTENT, content)
            fragment.arguments = args
            return fragment
        }
    }
}