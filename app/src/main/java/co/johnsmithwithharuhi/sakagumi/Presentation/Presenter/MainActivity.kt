package co.johnsmithwithharuhi.sakagumi.Presentation.Presenter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.johnsmithwithharuhi.sakagumi.R

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)
  }
}
