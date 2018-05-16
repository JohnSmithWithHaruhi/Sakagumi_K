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
      val vectorDrawable = ContextCompat.getDrawable(context, id)
      val bitmap = Bitmap.createBitmap(
          vectorDrawable.intrinsicWidth,
          vectorDrawable.intrinsicHeight,
          Bitmap.Config.ARGB_8888
      )
      val canvas = Canvas(bitmap)
      vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
      vectorDrawable.draw(canvas)
      return bitmap
    }
  }

}
