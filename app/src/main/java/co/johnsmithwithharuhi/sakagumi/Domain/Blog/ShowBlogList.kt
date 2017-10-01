package co.johnsmithwithharuhi.sakagumi.Domain.Blog

import co.johnsmithwithharuhi.sakagumi.Domain.UseCase
import io.reactivex.Observable

class ShowBlogList(repository: BlogRepository, type: Int) : UseCase<Observable<List<Blog>>> {

  private val mRepository = repository
  private val mType = type

  override fun execute(): Observable<List<Blog>> {
    return Observable.create({ e ->
      e.onNext(mRepository.getBlogList(mType))
      e.onComplete()
    })
  }

  private fun filterOlder(blogList: List<Blog>,
      newestId: Long): List<Blog> {
    val tempList = ArrayList<Blog>()
    tempList += blogList.takeWhile { it.id != newestId }
    return tempList
  }
}
