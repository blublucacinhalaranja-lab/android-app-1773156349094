package com.example.bancalculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bancalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var result: Double = 0.0
    private lateinit var sharedPreferences: SharedPreferences
    private var isBanned: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("BanCalculator", Context.MODE_PRIVATE)
        isBanned = sharedPreferences.getBoolean("isBanned", false)

        if (isBanned) {
            binding.banMessageTextView.visibility = View.VISIBLE
            binding.numberEditText.isEnabled = false
            binding.addButton.isEnabled = false
            binding.equalsButton.isEnabled = false
        } else {
            binding.banMessageTextView.visibility = View.GONE
            binding.numberEditText.isEnabled = true
            binding.addButton.isEnabled = true
            binding.equalsButton.isEnabled = true
        }

        binding.addButton.setOnClickListener {
            val number = binding.numberEditText.text.toString().toDoubleOrNull() ?: 0.0
            result += number
            binding.resultTextView.text = result.toString()
            binding.numberEditText.text.clear()
        }

        binding.equalsButton.setOnClickListener {
            val number = binding.numberEditText.text.toString().toDoubleOrNull() ?: 0.0
            result += number

            if (result == 2.0) {
                banUser()
            }

            binding.resultTextView.text = result.toString()
            binding.numberEditText.text.clear()
        }
    }

    private fun banUser() {
        isBanned = true
        val editor = sharedPreferences.edit()
        editor.putBoolean("isBanned", true)
        editor.apply()

        binding.banMessageTextView.visibility = View.VISIBLE
        binding.numberEditText.isEnabled = false
        binding.addButton.isEnabled = false
        binding.equalsButton.isEnabled = false
    }
}