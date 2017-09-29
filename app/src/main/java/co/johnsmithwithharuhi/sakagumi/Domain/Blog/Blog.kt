package co.johnsmithwithharuhi.sakagumi.Domain.Blog

class Blog {

  companion object {
    const val OSU_KEY = 0
    const val NOG_KEY = 1
    const val KEY_KEY = 2
  }

  private var id: Long = 0
  private var type: Int? = null
  private var name: String? = null
  private var title: String? = null
  private var url: String? = null
  private var content: String? = null
  private var time: String? = null

  fun getType(): Int? = type

  fun setType(type: Int) {
    this.type = type
  }

  fun getName(): String? = name

  fun setName(name: String) {
    this.name = name
  }

  fun getTitle(): String? = title

  fun setTitle(title: String) {
    this.title = title
  }

  fun getUrl(): String? = url

  fun setUrl(url: String) {
    this.url = url
  }

  fun getContent(): String? = content

  fun setContent(content: String) {
    this.content = content
  }

  fun getTime(): String? = time

  fun setTime(time: String) {
    this.time = time
  }
}