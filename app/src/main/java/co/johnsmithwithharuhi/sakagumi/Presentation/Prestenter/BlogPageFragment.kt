package co.johnsmithwithharuhi.sakagumi.Presentation.Prestenter

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.johnsmithwithharuhi.sakagumi.Data.Repository.BlogRepository
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.Blog
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.ShowBlogList
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.ShowNewestBlogList
import co.johnsmithwithharuhi.sakagumi.Presentation.Adapter.BlogListAdapter
import co.johnsmithwithharuhi.sakagumi.Presentation.Utils.CustomTabUtil
import co.johnsmithwithharuhi.sakagumi.Presentation.Utils.GroupUtil
import co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel.ItemBlogViewModel
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.FragmentBlogPageBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BlogPageFragment : Fragment(),
    BlogListAdapter.OnItemClickedListener,
    SwipeRefreshLayout.OnRefreshListener {

  private val BLOG_TYPE = "blog_type"

  private val mCompositeDisposable = CompositeDisposable()
  private val mBlogRepository = BlogRepository()
  private var mType: Int = Blog.KEY_OSU

  private lateinit var mBlogListAdapter: BlogListAdapter
  private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

  fun newInstance(type: Int): BlogPageFragment = BlogPageFragment().apply {
    this.arguments = Bundle().also { bundle -> bundle.putInt(BLOG_TYPE, type) }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBlogListAdapter = BlogListAdapter(context, this)
  }

  override fun onCreateView(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = DataBindingUtil.inflate<FragmentBlogPageBinding>(
        inflater!!,
        R.layout.fragment_blog_page, container, false
    )
    mType = arguments.getInt(BLOG_TYPE)

    initSwipeRefreshLayout(binding)
    initRecyclerView(binding)

    if (mBlogListAdapter.itemCount == 0) {
      mSwipeRefreshLayout.isRefreshing = true
      initBlogList()
    }

    return binding.root
  }

  private fun initSwipeRefreshLayout(binding: FragmentBlogPageBinding) {
    mSwipeRefreshLayout = binding.blogSwipeRefreshLayout
    mSwipeRefreshLayout.setColorSchemeResources(
        android.R.color.holo_purple,
        R.color.colorLightGreen500
    )
    mSwipeRefreshLayout.setOnRefreshListener(this)
  }

  private fun initRecyclerView(binding: FragmentBlogPageBinding) {
    val recyclerView = binding.blogRecyclerView
    recyclerView.setHasFixedSize(true)
    recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = mBlogListAdapter
  }

  private fun initBlogList() {
    mCompositeDisposable.add(
        ShowBlogList(mBlogRepository, mType).execute()
            .subscribeOn(Schedulers.io())
            .flatMap { blogList ->
              Observable.fromIterable(blogList)
                  .map { blog -> convertEntityToViewModel(blog) }
            }.toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ viewModels ->
              mBlogListAdapter.initViewModelList(viewModels)
              mSwipeRefreshLayout.isRefreshing = false
            }, { mSwipeRefreshLayout.isRefreshing = false })
    )
  }

  private fun loadNewBlogList() {
    mCompositeDisposable.add(
        ShowNewestBlogList(mBlogRepository, mType, mBlogListAdapter.getNewestUrl()).execute()
            .subscribeOn(Schedulers.io())
            .flatMap { blogList ->
              Observable.fromIterable(blogList)
                  .map { blog -> convertEntityToViewModel(blog) }
            }.toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ viewModels ->
              mBlogListAdapter.putViewModelList(viewModels)
              mSwipeRefreshLayout.isRefreshing = false
            }, { mSwipeRefreshLayout.isRefreshing = false })
    )
  }

  private fun convertEntityToViewModel(blog: Blog): ItemBlogViewModel =
    ItemBlogViewModel().apply {
      title.set(blog.title)
      name.set(blog.name)
      content.set(blog.content)
      url.set(blog.url)
      time.set(blog.time)
      textColor.set((GroupUtil.getGroupColor(context, blog.type)))
    }

  override fun onItemClick(url: String) {
    CustomTabUtil.launchUrl(context, mType, url)
  }

  override fun onRefresh() {
    mCompositeDisposable.clear()
    mSwipeRefreshLayout.isRefreshing = true
    if (mBlogListAdapter.itemCount == 0) {
      initBlogList()
    } else {
      loadNewBlogList()
    }
  }

}