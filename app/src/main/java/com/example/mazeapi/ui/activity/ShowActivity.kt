package com.example.mazeapi.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.mazeapi.R
import com.example.mazeapi.databinding.ActivityShowBinding
import com.example.mazeapi.ui.fragment.FirstFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowActivity : AppCompatActivity() {

    private var _binding: ActivityShowBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (savedInstanceState == null) {
            val itemId = intent.getIntExtra("itemId", -1)

            supportFragmentManager.commit {
                val fragment = FirstFragment()
                val bundle = Bundle()
                bundle.putInt("itemId", itemId) // Put the itemId into the Bundle
                fragment.arguments = bundle
                replace(R.id.fragment_container, fragment)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}