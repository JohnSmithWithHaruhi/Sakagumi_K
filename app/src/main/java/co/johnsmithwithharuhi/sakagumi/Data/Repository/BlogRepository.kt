package co.johnsmithwithharuhi.sakagumi.Data.Repository

import co.johnsmithwithharuhi.sakagumi.Data.JSoup.BlogJSoup
import co.johnsmithwithharuhi.sakagumi.Data.Model.BlogModel
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import io.reactivex.Single

class BlogRepository {

  companion object {
    @Volatile private var instance: BlogRepository? = null

    fun getInstance(): BlogRepository {
      return instance ?: synchronized(this) {
        instance ?: BlogRepository().also { instance = it }
      }
    }
  }

  private val mBlogJSoup: BlogJSoup = BlogJSoup()

  fun getBlogList(type: GroupType): Single<List<BlogModel>> {
    return Single.create { e ->
      when (type) {
        GroupType.NOGI -> mBlogJSoup.loadNogBlogList(1)
        GroupType.KEYA -> mBlogJSoup.loadKeyBlogList()
        else -> mBlogJSoup.loadOsuBlogList()
      }.let {
        e.onSuccess(it)
      }
    }
  }
}
