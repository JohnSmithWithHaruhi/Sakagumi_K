package co.johnsmithwithharuhi.sakagumi.Data.Repository

import co.johnsmithwithharuhi.sakagumi.Data.Entity.BlogEntity
import co.johnsmithwithharuhi.sakagumi.Data.JSoup.BlogJSoup
import io.reactivex.Observable

class BlogRepository {

  private val mBlogJSoup: BlogJSoup = BlogJSoup()

  val osuBlog: Observable<List<BlogEntity>>
    get() = Observable.create({ e ->
      e.onNext(mBlogJSoup.createOsuBlogList())
      e.onComplete()
    })


  val nogBlog: Observable<List<BlogEntity>>
    get() = Observable.create({ e ->
      e.onNext(mBlogJSoup.createNogBlogList())
      e.onComplete()
    })

  val keyBlog: Observable<List<BlogEntity>>
    get() = Observable.create({ e ->
      e.onNext(mBlogJSoup.createKeyBlogList())
      e.onComplete()
    })
}