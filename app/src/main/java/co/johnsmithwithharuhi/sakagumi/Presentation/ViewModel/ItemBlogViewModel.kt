package co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel

import android.databinding.ObservableField
import android.databinding.ObservableInt

class ItemBlogViewModel {
  var textColor = ObservableInt()
  var title = ObservableField<String>()
  var name = ObservableField<String>()
  var time = ObservableField<String>()
  var content = ObservableField<String>()
  var url = ObservableField<String>()
}