package co.johnsmithwithharuhi.sakagumi.ViewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.johnsmithwithharuhi.sakagumi.Data.Repository.BlogRepository
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.Blog
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.ShowBlogList
import io.reactivex.schedulers.Schedulers

class BlogPageViewModel : ViewModel() {

  var blogRepository = BlogRepository()

  private var blogList: MutableLiveData<List<Blog>>? = null

  fun getBlogList(): MutableLiveData<List<Blog>> {
    return blogList ?: MutableLiveData<List<Blog>>().also {
      blogList = it
      loadBlogList()
    }
  }

  private fun loadBlogList() {
    ShowBlogList(blogRepository, Blog.KEY_KEY).execute()
        .subscribeOn(Schedulers.io())
        .subscribe({ blogList!!.postValue(it) })
  }
}
