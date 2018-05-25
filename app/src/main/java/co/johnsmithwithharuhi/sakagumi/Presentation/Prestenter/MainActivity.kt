package co.johnsmithwithharuhi.sakagumi.Presentation.Prestenter

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        .run {
          mainBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
              R.id.action_blog, R.id.action_rss, R.id.action_event -> {
              }
            }
            true
          }
        }

    supportFragmentManager.beginTransaction()
        .add(R.id.main_content, BlogMainFragment())
        .commit()
  }
}
