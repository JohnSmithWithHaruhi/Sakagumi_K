package co.johnsmithwithharuhi.sakagumi.Domain.Blog

class Blog {

  companion object {
    const val KEY_OSU = 0
    const val KEY_NOG = 1
    const val KEY_KEY = 2
  }

  var id: Long = 0
  var type: Int? = null
  var name: String? = null
  var title: String? = null
  var url: String? = null
  var content: String? = null
  var time: String? = null

}