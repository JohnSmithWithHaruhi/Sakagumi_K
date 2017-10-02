package co.johnsmithwithharuhi.sakagumi.Presentation.Prestenter

import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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
import co.johnsmithwithharuhi.sakagumi.Presentation.Utils.BitmapUtil
import co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel.ItemBlogViewModel
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.FragmentBlogPageBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class BlogPageFragment : Fragment(), ItemBlogViewModel.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

  private val PAGE_POSITION = "blog_page_position"

  private val mCompositeDisposable = CompositeDisposable()
  private val mBlogRepository = BlogRepository()
  private var mType: Int = Blog.OSU_KEY

  private lateinit var mBlogListAdapter: BlogListAdapter
  private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

  fun newInstance(pagePosition: Int): BlogPageFragment {
    val fragment = BlogPageFragment()
    val args = Bundle()
    args.putInt(PAGE_POSITION, pagePosition)
    fragment.arguments = args
    return fragment
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBlogListAdapter = BlogListAdapter(context, this)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val binding = DataBindingUtil.inflate<FragmentBlogPageBinding>(inflater,
        R.layout.fragment_blog_page, container, false)
    mType = covertPagePositionToType(arguments.getInt(PAGE_POSITION))

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
    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple,
        R.color.colorLightGreen500)
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
              Observable.fromIterable(blogList).map { blog -> convertEntityToViewModel(blog) }
            }.toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ viewModels ->
              mBlogListAdapter.initViewModelList(viewModels)
              mSwipeRefreshLayout.isRefreshing = false
            }, { mSwipeRefreshLayout.isRefreshing = false }))
  }

  private fun loadNewBlogList() {
    mCompositeDisposable.add(
        ShowNewestBlogList(mBlogRepository, mType, mBlogListAdapter.getNewestUrl()).execute()
            .subscribeOn(Schedulers.io())
            .flatMap { blogList ->
              Observable.fromIterable(blogList).map { blog -> convertEntityToViewModel(blog) }
            }.toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ viewModels ->
              mBlogListAdapter.putViewModelList(viewModels)
              mSwipeRefreshLayout.isRefreshing = false
            }, { mSwipeRefreshLayout.isRefreshing = false }))
  }

  private fun covertPagePositionToType(position: Int): Int = when (position) {
    1 -> Blog.NOG_KEY
    2 -> Blog.KEY_KEY
    else -> Blog.OSU_KEY
  }

  private fun convertEntityToViewModel(blog: Blog): ItemBlogViewModel {
    val viewModel = ItemBlogViewModel()
    viewModel.title.set(blog.title)
    viewModel.name.set(blog.name)
    viewModel.content.set(blog.content)
    viewModel.url.set(blog.url)
    viewModel.time.set(blog.time)
    viewModel.textColor.set(getTextColor(blog.type))
    return viewModel
  }

  private fun getTextColor(type: Int?): Int {
    return ContextCompat.getColor(context, when (type) {
      Blog.NOG_KEY -> R.color.colorPurple700
      Blog.KEY_KEY -> R.color.colorLightGreen700
      else -> R.color.colorGrey700
    })
  }

  override fun onItemClick(url: String) {
    val toolBarColor = when (mType) {
      Blog.NOG_KEY -> R.color.colorPurple700
      Blog.KEY_KEY -> R.color.colorLightGreen700
      else -> R.color.colorGrey700
    }
    CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setToolbarColor(ContextCompat.getColor(context, toolBarColor))
        .enableUrlBarHiding().addDefaultShareMenuItem()
        .setCloseButtonIcon(
            BitmapUtil().convertBitmapFromVectorDrawable(context, R.drawable.ic_arrow_back_w))
        .setStartAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        .build().launchUrl(context, Uri.parse(url))
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