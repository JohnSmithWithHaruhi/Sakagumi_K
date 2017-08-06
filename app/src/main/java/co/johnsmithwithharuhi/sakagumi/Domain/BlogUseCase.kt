package co.johnsmithwithharuhi.sakagumi.Domain

import android.text.TextUtils
import co.johnsmithwithharuhi.sakagumi.Data.Entity.BlogEntity
import co.johnsmithwithharuhi.sakagumi.Data.Repository.BlogRepository
import co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel.Item.ItemBlogListViewModel
import co.johnsmithwithharuhi.sakagumi.R
import io.reactivex.Observable


class BlogUseCase {

  val TYPE_OSU = 0
  val TYPE_NOG = 1
  val TYPE_KEY = 2
  val mRepository = BlogRepository()

  fun getViewModelList(type: Int): Observable<List<ItemBlogListViewModel>> {
    val observable: Observable<List<BlogEntity>>
    when (type) {
      TYPE_NOG -> observable = mRepository.nogBlog
      TYPE_KEY -> observable = mRepository.keyBlog
      else -> observable = mRepository.osuBlog
    }
    return observable.flatMap { blogEntities ->
      Observable.fromIterable(blogEntities).map { blogEntity ->
        convertEntityToViewModel(blogEntity)
      }
    }
        .toList()
        .toObservable()
  }

  fun getNewestViewModelList(type: Int,
      newestUrl: String): Observable<List<ItemBlogListViewModel>> {
    val observable: Observable<List<BlogEntity>>
    when (type) {
      TYPE_NOG -> observable = mRepository.nogBlog
      TYPE_KEY -> observable = mRepository.keyBlog
      else -> observable = mRepository.osuBlog
    }
    return observable.flatMap({ blogEntities ->
      Observable.fromIterable(blogEntities).map { blogEntity ->
        convertEntityToViewModel(blogEntity)
      }
    })
        .toList()
        .map({ viewModels -> filterOlder(viewModels, newestUrl) })
        .toObservable()
  }

  private fun convertEntityToViewModel(blogEntity: BlogEntity): ItemBlogListViewModel {
    val viewModel = ItemBlogListViewModel()
    viewModel.title.set(blogEntity.getTitle())
    viewModel.name.set(blogEntity.getName())
    viewModel.content.set(blogEntity.getContent())
    viewModel.url.set(blogEntity.getUrl())
    viewModel.time.set(blogEntity.getTime())
    viewModel.textColor = if (blogEntity.getType()!! == BlogEntity.NOG_KEY)
      R.color.colorPurple700
    else
      R.color.colorLightGreen700
    return viewModel
  }

  private fun filterOlder(itemList: List<ItemBlogListViewModel>,
      newestUrl: String): List<ItemBlogListViewModel> {
    val tempList = ArrayList<ItemBlogListViewModel>()
    tempList += itemList.takeWhile { !TextUtils.equals(it.url.get(), newestUrl) }
    return tempList
  }

}
