package co.johnsmithwithharuhi.sakagumi.Presentation.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v4.content.ContextCompat

class BitmapUtil {

  companion object {
    fun convertBitmapFromVectorDrawable(
      context: Context,
      id: Int
    ): Bitmap {
      return ContextCompat.getDrawable(context, id)
          .let { drawable ->
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
                .also {
                  Canvas(it).let {
                    drawable.setBounds(0, 0, it.width, it.height)
                    drawable.draw(it)
                  }
                }
          }
    }
  }

}
