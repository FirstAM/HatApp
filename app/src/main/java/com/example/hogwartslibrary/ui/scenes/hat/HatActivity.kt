package com.example.hogwartslibrary.ui.scenes.hat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.hogwartslibrary.R
import com.example.hogwartslibrary.ui.scenes.main.MainActivity
import kotlinx.android.synthetic.main.activity_hat.*

class HatActivity : AppCompatActivity() {
    private lateinit var hatViewModel: HatViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hat)
        hatViewModel = ViewModelProviders.of(this).get(HatViewModel::class.java)

        texWelcomeUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                hatViewModel.applyUserName(name = p0.toString())
            }
        })

        btnWelcomeSelect.setOnClickListener {
            if (btnWelcomeSelect.text == getString(R.string.welcome_next)) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                hatViewModel.getFacultyName()
            }
        }

        setupFaculty(viewModel = hatViewModel)
        setupLoading(viewModel = hatViewModel)
    }

    private fun setupFaculty(viewModel: HatViewModel) {
        viewModel.facultyName.observe(this, Observer { facultyName ->
            if (facultyName.isNotEmpty()) {
                texWelcomeSelected.text =
                        getString(R.string.welcome_selected).replace("[faculty_name]", facultyName)
                texWelcomeSelected.visibility = View.VISIBLE
                texWelcomeUserName.isEnabled = false
                btnWelcomeSelect.text = getString(R.string.welcome_next)
            }
        })
    }

    private fun setupLoading(viewModel: HatViewModel) {
        viewModel.isLoading.observe(this, Observer { isLoading ->
            texWelcomeUserName.isEnabled = !isLoading
            btnWelcomeSelect.isEnabled = !isLoading
            if (isLoading) {
                btnWelcomeSelect.text = getString(R.string.welcome_seleting)
            }
        })
    }
}