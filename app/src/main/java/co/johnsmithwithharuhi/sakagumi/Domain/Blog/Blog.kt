package co.johnsmithwithharuhi.sakagumi.Domain.Blog

data class Blog(
  var id: Long = 0,
  var type: Int = KEY_OSU,
  var name: String = "",
  var title: String = "",
  var url: String = "",
  var content: String = "",
  var time: String = ""
) {
  companion object {
    const val KEY_OSU = 0
    const val KEY_NOG = 1
    const val KEY_KEY = 2
  }
}