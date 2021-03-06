package co.johnsmithwithharuhi.sakagumi.Presentation.View

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binging = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)


    binging.mainBottomNavigation.setOnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.action_blog, R.id.action_rss, R.id.action_event -> true
        else -> false
      }
    }

    supportFragmentManager.beginTransaction()
        .add(R.id.main_content, BlogMainFragment())
        .commit()
  }

}
