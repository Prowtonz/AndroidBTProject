package com.example.roomautomation

import android.animation.ObjectAnimator
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.roomautomation.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BluetoothViewModel
    private lateinit var downRotationAnimation: ObjectAnimator
    private lateinit var upRotationAnimation: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        initializeArrowAnimations()

        //Click listeners
        binding.closeConnectionButton.setOnClickListener{ viewModel.closeConnection() }

        //LiveData observers
        viewModel.toastText.observe(this, Observer { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() })
        viewModel.imageGraphic.observe(this, Observer { binding.image.setImageResource(it) })
        viewModel.isConnected.observe(this, Observer {
            if(it == true) {
                binding.textView.visibility = View.INVISIBLE
                binding.closeConnectionButton.visibility = View.VISIBLE
            } else {
                binding.textView.visibility = View.VISIBLE
                binding.closeConnectionButton.visibility = View.INVISIBLE
                binding.image.rotation = 0f
            }
        })
        viewModel.runApp.observe(this, Observer {
            if(it) {
                finish()
                exitProcess(0)
            }
        })
        viewModel.buttonText.observe(this, Observer {
            when(it) {
                ENABLE_BLUETOOTH -> binding.connectButton.setOnClickListener{ viewModel.enableBT() }
                CONNECT -> binding.connectButton.setOnClickListener{ viewModel.connect() }
                OPEN_SKYLIGHT -> binding.connectButton.setOnClickListener {
                    viewModel.toggleLED()
                    if(downRotationAnimation.isRunning) downRotationAnimation.setCurrentFraction(1.0f)
                    upRotationAnimation.start()
                }
                CLOSE_SKYLIGHT -> {
                    binding.connectButton.setOnClickListener {
                        viewModel.toggleLED()
                        if (upRotationAnimation.isRunning) upRotationAnimation.setCurrentFraction(1.0f)
                        downRotationAnimation.start()
                    }
                }
            }
        })

    }

    private fun initializeArrowAnimations() {
        downRotationAnimation = ObjectAnimator.ofFloat(binding.image, "rotation", 0f, 180f).setDuration(1000L)
        upRotationAnimation = ObjectAnimator.ofFloat(binding.image, "rotation", 180f, 360f).setDuration(1000L)
    }

}