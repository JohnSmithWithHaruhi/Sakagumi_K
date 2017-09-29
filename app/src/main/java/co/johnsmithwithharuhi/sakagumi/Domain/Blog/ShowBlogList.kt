package co.johnsmithwithharuhi.sakagumi.Domain.Blog

import android.text.TextUtils
import co.johnsmithwithharuhi.sakagumi.Data.Repository.BlogRepository
import co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel.ItemBlogViewModel
import co.johnsmithwithharuhi.sakagumi.R
import io.reactivex.Observable


class ShowBlogList {

  val TYPE_OSU = 0
  val TYPE_NOG = 1
  val TYPE_KEY = 2
  private val mRepository = BlogRepository()

  fun getViewModelList(type: Int): Observable<List<ItemBlogViewModel>> {
    val observable: Observable<List<Blog>> = when (type) {
      TYPE_NOG -> mRepository.getNogBlog()
      TYPE_KEY -> mRepository.getKeyBlog()
      else -> mRepository.getOsuBlog()
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
      newestUrl: String): Observable<List<ItemBlogViewModel>> {
    val observable: Observable<List<Blog>> = when (type) {
      TYPE_NOG -> mRepository.getNogBlog()
      TYPE_KEY -> mRepository.getKeyBlog()
      else -> mRepository.getOsuBlog()
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

  private fun convertEntityToViewModel(blog: Blog): ItemBlogViewModel {
    val viewModel = ItemBlogViewModel()
    viewModel.title.set(blog.getTitle())
    viewModel.name.set(blog.getName())
    viewModel.content.set(blog.getContent())
    viewModel.url.set(blog.getUrl())
    viewModel.time.set(blog.getTime())
    viewModel.textColor = if (blog.getType()!! == Blog.NOG_KEY)
      R.color.colorPurple700
    else
      R.color.colorLightGreen700
    return viewModel
  }

  private fun filterOlder(modelList: List<ItemBlogViewModel>,
      newestUrl: String): List<ItemBlogViewModel> {
    val tempList = ArrayList<ItemBlogViewModel>()
    tempList += modelList.takeWhile { !TextUtils.equals(it.url.get(), newestUrl) }
    return tempList
  }

}
