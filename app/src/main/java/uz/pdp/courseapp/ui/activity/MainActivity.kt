package uz.pdp.courseapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.pdp.courseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMainBinding.inflate(layoutInflater)
        setContentView(inflate.root)
    }
}