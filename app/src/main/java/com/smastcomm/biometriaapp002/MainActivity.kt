package com.smastcomm.biometriaapp002

import androidx.biometric.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.smastcomm.biometriaapp002.databinding.ActivityMainBinding
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init biometric
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(
            this@MainActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    val text = "Ошибка авторизации $errString"
                    binding.authStatus.text = text
                    showToast(text)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val text = "Успешная авторизация"
                    binding.authStatus.text = text
                    showToast(text)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    val text = "Сбой авторизации"
                    binding.authStatus.text = text
                    showToast(text)
                }
            }
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Биометрическая авторизвция")
            .setSubtitle("Вход с помощью отпечатка пальца")
            .setNegativeButtonText("Использовать пароль")
            .build()

        binding.btnAuth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }
}