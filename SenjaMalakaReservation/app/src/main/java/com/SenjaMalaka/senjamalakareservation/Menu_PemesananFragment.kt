package com.SenjaMalaka.senjamalakareservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Menu_PemesananFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var buttonTotalHarga: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menupemesanan, container, false)

        // Sample data
        val makananList = listOf(
            Triple("Nasi Goreng", "Rp. 15.000", "Nasi goreng dengan telur"),
            Triple("Mie Goreng", "Rp. 12.000", "Mie goreng dengan sayuran"),
            Triple("Ayam Goreng", "Rp. 20.000", "Ayam goreng tepung")
        )

        val minumanList = listOf(
            Triple("Es Teh", "Rp. 5.000", "Teh manis dingin"),
            Triple("Kopi", "Rp. 10.000", "Kopi hitam hangat"),
            Triple("Jus Jeruk", "Rp. 8.000", "Jus jeruk segar")
        )

        buttonTotalHarga = view.findViewById(R.id.button_total_harga)
        buttonTotalHarga.setOnClickListener {
            val fragment = Menu_PemesananPaymentFragment.newInstance(getOrderSummary(), getTotalPrice())
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.Menu_Fragment, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        // Populate makanan
        makananList.forEachIndexed { index, makanan ->
            val makananView = view.findViewById<View>(resources.getIdentifier("makanan_$index", "id", context?.packageName))
            makananView?.findViewById<TextView>(R.id.Pesanan_NamaMakanan)?.text = makanan.first
            makananView?.findViewById<TextView>(R.id.Pesanan_HargaMakanan)?.text = makanan.second
            makananView?.findViewById<TextView>(R.id.Pesanan_DeskripsiMakanan)?.text = makanan.third

            val jumlahEditText = makananView?.findViewById<EditText>(R.id.EditText_JumlahMakanan)
            val tambahButton = makananView?.findViewById<ImageButton>(R.id.Button_TambahMakanan)
            val kurangiButton = makananView?.findViewById<ImageButton>(R.id.Button_KurangiMakanan)

            tambahButton?.setOnClickListener {
                val currentValue = jumlahEditText?.text.toString().toIntOrNull() ?: 0
                jumlahEditText?.setText((currentValue + 1).toString())
                updateTotalPrice()
            }

            kurangiButton?.setOnClickListener {
                val currentValue = jumlahEditText?.text.toString().toIntOrNull() ?: 0
                if (currentValue > 0) {
                    jumlahEditText?.setText((currentValue - 1).toString())
                    updateTotalPrice()
                }
            }
        }

        // Populate minuman
        minumanList.forEachIndexed { index, minuman ->
            val minumanView = view.findViewById<View>(resources.getIdentifier("minuman_$index", "id", context?.packageName))
            minumanView?.findViewById<TextView>(R.id.Pesanan_NamaMinuman)?.text = minuman.first
            minumanView?.findViewById<TextView>(R.id.Pesanan_HargaMinuman)?.text = minuman.second
            minumanView?.findViewById<TextView>(R.id.Pesanan_DeskripsiMinuman)?.text = minuman.third

            val jumlahEditText = minumanView?.findViewById<EditText>(R.id.EditText_JumlahMinuman)
            val tambahButton = minumanView?.findViewById<ImageButton>(R.id.Button_TambahMinuman)
            val kurangiButton = minumanView?.findViewById<ImageButton>(R.id.Button_KurangiMinuman)

            tambahButton?.setOnClickListener {
                val currentValue = jumlahEditText?.text.toString().toIntOrNull() ?: 0
                jumlahEditText?.setText((currentValue + 1).toString())
                updateTotalPrice()
            }

            kurangiButton?.setOnClickListener {
                val currentValue = jumlahEditText?.text.toString().toIntOrNull() ?: 0
                if (currentValue > 0) {
                    jumlahEditText?.setText((currentValue - 1).toString())
                    updateTotalPrice()
                }
            }
        }

        return view
    }

    private fun updateTotalPrice() {
        var totalPrice = 0
        val makananPrices = listOf(15000, 12000, 20000)
        val minumanPrices = listOf(5000, 10000, 8000)

        for (i in makananPrices.indices) {
            val makananView = view?.findViewById<View>(resources.getIdentifier("makanan_$i", "id", context?.packageName))
            val jumlahEditText = makananView?.findViewById<EditText>(R.id.EditText_JumlahMakanan)
            val quantity = jumlahEditText?.text.toString().toIntOrNull() ?: 0
            totalPrice += quantity * makananPrices[i]
        }

        for (i in minumanPrices.indices) {
            val minumanView = view?.findViewById<View>(resources.getIdentifier("minuman_$i", "id", context?.packageName))
            val jumlahEditText = minumanView?.findViewById<EditText>(R.id.EditText_JumlahMinuman)
            val quantity = jumlahEditText?.text.toString().toIntOrNull() ?: 0
            totalPrice += quantity * minumanPrices[i]
        }

        buttonTotalHarga.text = "Total Harga: Rp. $totalPrice"
    }

    private fun getOrderSummary(): String {
        val makananList = listOf("Nasi Goreng", "Mie Goreng", "Ayam Goreng")
        val makananPrices = listOf(15000, 12000, 20000)
        val minumanList = listOf("Es Teh", "Kopi", "Jus Jeruk")
        val minumanPrices = listOf(5000, 10000, 8000)

        val stringBuilder = StringBuilder()

        for (i in makananList.indices) {
            val makananView = view?.findViewById<View>(resources.getIdentifier("makanan_$i", "id", context?.packageName))
            val jumlahEditText = makananView?.findViewById<EditText>(R.id.EditText_JumlahMakanan)
            val quantity = jumlahEditText?.text.toString().toIntOrNull() ?: 0
            if (quantity > 0) {
                stringBuilder.append("${makananList[i]} x $quantity @ ${makananPrices[i]}\n")
            }
        }

        for (i in minumanList.indices) {
            val minumanView = view?.findViewById<View>(resources.getIdentifier("minuman_$i", "id", context?.packageName))
            val jumlahEditText = minumanView?.findViewById<EditText>(R.id.EditText_JumlahMinuman)
            val quantity = jumlahEditText?.text.toString().toIntOrNull() ?: 0
            if (quantity > 0) {
                stringBuilder.append("${minumanList[i]} x $quantity @ ${minumanPrices[i]}\n")
            }
        }

        return stringBuilder.toString()
    }

    private fun getTotalPrice(): Int {
        var totalPrice = 0
        val makananPrices = listOf(15000, 12000, 20000)
        val minumanPrices = listOf(5000, 10000, 8000)

        for (i in makananPrices.indices) {
            val makananView = view?.findViewById<View>(resources.getIdentifier("makanan_$i", "id", context?.packageName))
            val jumlahEditText = makananView?.findViewById<EditText>(R.id.EditText_JumlahMakanan)
            val quantity = jumlahEditText?.text.toString().toIntOrNull() ?: 0
            totalPrice += quantity * makananPrices[i]
        }

        for (i in minumanPrices.indices) {
            val minumanView = view?.findViewById<View>(resources.getIdentifier("minuman_$i", "id", context?.packageName))
            val jumlahEditText = minumanView?.findViewById<EditText>(R.id.EditText_JumlahMinuman)
            val quantity = jumlahEditText?.text.toString().toIntOrNull() ?: 0
            totalPrice += quantity * minumanPrices[i]
        }

        return totalPrice
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Menu_PemesananFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
