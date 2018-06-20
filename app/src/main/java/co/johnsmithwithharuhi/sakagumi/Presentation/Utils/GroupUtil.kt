package co.johnsmithwithharuhi.sakagumi.Presentation.Utils

import android.content.Context
import android.support.v4.content.ContextCompat
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import co.johnsmithwithharuhi.sakagumi.R

class GroupUtil {

  companion object {
    fun getGroupColor(
      context: Context,
      group: GroupType
    ): Int {
      return when (group) {
        GroupType.NOGI -> R.color.colorPurple700
        GroupType.KEYA -> R.color.colorLightGreen700
        GroupType.OSU -> R.color.colorLightGreen700
      }.let {
        ContextCompat.getColor(context, it)
      }
    }
  }
}
