package co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel

import android.databinding.ObservableField
import android.view.View


class ItemBlogViewModel {
  var textColor: Int = 0
  var title = ObservableField<String>()
  var name = ObservableField<String>()
  var time = ObservableField<String>()
  var content = ObservableField<String>()
  var url = ObservableField<String>()

  private var mListener: OnItemClickListener? = null

  fun setOnItemClickListener(listener: OnItemClickListener) {
    mListener = listener
  }

  fun onItemClick(view: View) {
    mListener!!.onItemClick(url.get())
  }

  interface OnItemClickListener {
    fun onItemClick(url: String)
  }
}