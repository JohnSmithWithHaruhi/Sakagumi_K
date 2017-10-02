package co.johnsmithwithharuhi.sakagumi.Domain.Blog

import co.johnsmithwithharuhi.sakagumi.Domain.UseCase
import io.reactivex.Observable

class ShowBlogList(repository: BlogRepository, type: Int) : UseCase<Observable<List<Blog>>> {

  private val mRepository = repository
  private val mType = type

  override fun execute(): Observable<List<Blog>> =
      Observable.create({ e ->
        e.onNext(mRepository.getBlogList(mType))
        e.onComplete()
      })

}
