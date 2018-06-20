package co.johnsmithwithharuhi.sakagumi.Presentation.Utils

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import co.johnsmithwithharuhi.sakagumi.R

class CustomTabUtil {

  companion object {
    fun launchUrl(
      context: Context,
      group: GroupType,
      url: String
    ) {
      CustomTabsIntent.Builder()
          .setShowTitle(true)
          .setToolbarColor(GroupUtil.getGroupColor(context, group))
          .enableUrlBarHiding()
          .addDefaultShareMenuItem()
          .setCloseButtonIcon(
              BitmapUtil.convertBitmapFromVectorDrawable(context, R.drawable.ic_arrow_back_w)
          )
          .setStartAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
          .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
          .build()
          .launchUrl(context, Uri.parse(url))
    }
  }

}