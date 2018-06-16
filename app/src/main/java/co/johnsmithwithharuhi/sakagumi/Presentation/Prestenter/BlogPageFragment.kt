package co.johnsmithwithharuhi.sakagumi.Presentation.Prestenter

import android.arch.lifecycle.ViewModelProviders
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
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.ViewModel.BlogPageViewModel
import co.johnsmithwithharuhi.sakagumi.databinding.FragmentBlogPageBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

private const val BLOG_TYPE = "blog_type"

class BlogPageFragment : Fragment(),
    BlogListAdapter.OnItemClickedListener,
    SwipeRefreshLayout.OnRefreshListener {

  private val disposable = CompositeDisposable()
  private val blogRepository = BlogRepository()
  private var type: Int = Blog.KEY_OSU

  private lateinit var blogListAdapter: BlogListAdapter
  private lateinit var swipeRefreshLayout: SwipeRefreshLayout
  private lateinit var viewModel: BlogPageViewModel

  fun newInstance(type: Int): BlogPageFragment = BlogPageFragment().apply {
    arguments = Bundle().apply { putInt(BLOG_TYPE, type) }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    blogListAdapter = BlogListAdapter(context!!, this)
    viewModel = ViewModelProviders.of(this)
        .get(BlogPageViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = DataBindingUtil.inflate<FragmentBlogPageBinding>(
        inflater, R.layout.fragment_blog_page, container, false
    )
    type = arguments!!.getInt(BLOG_TYPE)

    initSwipeRefreshLayout(binding)
    initRecyclerView(binding)

    swipeRefreshLayout.isRefreshing = true
    initBlogList()

    return binding.root
  }

  private fun initSwipeRefreshLayout(binding: FragmentBlogPageBinding) {
    swipeRefreshLayout = binding.blogSwipeRefreshLayout.apply {
      setColorSchemeResources(
          android.R.color.holo_purple,
          R.color.colorLightGreen500
      )
      setOnRefreshListener(this@BlogPageFragment)
    }
  }

  private fun initRecyclerView(binding: FragmentBlogPageBinding) {
    binding.blogRecyclerView.run {
      setHasFixedSize(true)
      addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
      layoutManager = LinearLayoutManager(context)
      adapter = blogListAdapter
    }
  }

  private fun initBlogList() {
    disposable.add(
        ShowBlogList(blogRepository, type).execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
              blogListAdapter.initViewModelList(it)
              swipeRefreshLayout.isRefreshing = false
            }, { swipeRefreshLayout.isRefreshing = false })
    )
  }

  private fun loadNewBlogList() {
    disposable.add(
        ShowNewestBlogList(blogRepository, type, blogListAdapter.getNewestUrl()).execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
              blogListAdapter.putViewModelList(it)
              swipeRefreshLayout.isRefreshing = false
            }, { swipeRefreshLayout.isRefreshing = false })
    )
  }

  override fun onItemClick(url: String) {
    CustomTabUtil.launchUrl(context!!, type, url)
  }

  override fun onRefresh() {
    disposable.clear()
    swipeRefreshLayout.isRefreshing = true
    if (blogListAdapter.itemCount == 0) {
      initBlogList()
    } else {
      loadNewBlogList()
    }
  }

}