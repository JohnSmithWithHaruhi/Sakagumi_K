package co.johnsmithwithharuhi.sakagumi.Domain.Blog

import android.text.TextUtils
import co.johnsmithwithharuhi.sakagumi.Domain.UseCase
import io.reactivex.Observable

class ShowNewestBlogList(repository: BlogRepository, type: Int,
    newestUrl: String) : UseCase<Observable<List<Blog>>> {

  private val mRepository = repository
  private val mType = type
  private val mNewestUrl = newestUrl

  override fun execute(): Observable<List<Blog>> {
    return Observable.create({ e ->
      e.onNext(filterOlder(mRepository.getBlogList(mType), mNewestUrl))
      e.onComplete()
    })
  }

  private fun filterOlder(blogList: List<Blog>,
      newestUrl: String): List<Blog> {
    val tempList = ArrayList<Blog>()
    tempList += blogList.takeWhile { !TextUtils.equals(it.url, newestUrl) }
    return tempList
  }

}