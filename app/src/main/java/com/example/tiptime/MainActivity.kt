package com.example.tiptime

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiptime.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        total = savedInstanceState?.getDouble("total") ?: 0.0
        binding.tipTx.text = "$total L.E"

        binding.calculateBtn.setOnClickListener {
            val tip = binding.costEdTx.text.toString().toDouble()
            
            val cost = when (binding.groupBtn.checkedRadioButtonId) {
                R.id.amazing_btn -> 0.20
                R.id.good_btn -> 0.18
                else -> 0.15
            }
            total = tip * cost
            if (binding.roundSwitch.isChecked)
                total = round(total)

            binding.tipTx.text = "$total L.E"

            Snackbar.make(binding.root, "reset all", Snackbar.LENGTH_LONG)
                .setTextColor(getColor(R.color.white))
                .setActionTextColor(getColor(R.color.red))
                .setAction("reset") {
                    binding.groupBtn.check(R.id.amazing_btn)
                    binding.roundSwitch.isChecked = true
                    binding.tipTx.text = "Tip Amount"
                    binding.costEdTx.text?.clear()
                }
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("total", total)
    }
}