package co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import co.johnsmithwithharuhi.sakagumi.Data.Model.BlogModel
import co.johnsmithwithharuhi.sakagumi.Data.Repository.BlogRepository
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import io.reactivex.schedulers.Schedulers

class BlogPageViewModel : ViewModel() {

  var blogList: MutableLiveData<List<BlogModel>> = MutableLiveData()

  private var blogRepository = BlogRepository.getInstance()

  fun getBlogList(groupType: GroupType) {
    blogRepository
        .getBlogList(groupType)
        .subscribeOn(Schedulers.io())
        .subscribe({ list -> blogList.postValue(list) },
            { error -> Log.d("", error.message) })
  }
}
