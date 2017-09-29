package co.johnsmithwithharuhi.sakagumi.Data.Repository

import co.johnsmithwithharuhi.sakagumi.Data.Entity.BlogEntity
import co.johnsmithwithharuhi.sakagumi.Data.JSoup.BlogJSoup
import io.reactivex.Observable

class BlogRepository {

  private val mBlogJSoup: BlogJSoup = BlogJSoup()

  fun getOsuBlog(): Observable<List<BlogEntity>> {
    return Observable.create({ e ->
      e.onNext(mBlogJSoup.createOsuBlogList())
      e.onComplete()
    })
  }

  fun getNogBlog(): Observable<List<BlogEntity>> {
    return Observable.create({ e ->
      e.onNext(mBlogJSoup.createNogBlogList())
      e.onComplete()
    })
  }

  fun getKeyBlog(): Observable<List<BlogEntity>> {
    return Observable.create({ e ->
      e.onNext(mBlogJSoup.createKeyBlogList())
      e.onComplete()
    })
  }

}