package co.johnsmithwithharuhi.sakagumi.Presentation.Utils

import android.content.Context
import android.support.v4.content.ContextCompat
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.Blog
import co.johnsmithwithharuhi.sakagumi.R

class GroupUtil {

  companion object {
    fun getGroupColor(
      context: Context,
      type: Int?
    ): Int = when (type) {
      Blog.KEY_NOG -> R.color.colorPurple700
      Blog.KEY_KEY -> R.color.colorLightGreen700
      else -> R.color.colorLightGreen700
    }.run {
      ContextCompat.getColor(context, this)
    }
  }
}
