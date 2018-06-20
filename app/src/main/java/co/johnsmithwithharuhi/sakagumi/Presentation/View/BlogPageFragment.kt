package co.johnsmithwithharuhi.sakagumi.Presentation.View

import android.arch.lifecycle.Observer
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
import co.johnsmithwithharuhi.sakagumi.Data.Model.BlogModel
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import co.johnsmithwithharuhi.sakagumi.Presentation.Adapter.BlogListAdapter
import co.johnsmithwithharuhi.sakagumi.Presentation.Utils.CustomTabUtil
import co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel.BlogPageViewModel
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.FragmentBlogPageBinding

private const val BLOG_TYPE = "blog_type"

class BlogPageFragment : Fragment(),
    BlogListAdapter.OnItemClickedListener,
    SwipeRefreshLayout.OnRefreshListener {

  private var viewType: GroupType = GroupType.OSU

  private lateinit var blogListAdapter: BlogListAdapter
  private lateinit var swipeRefreshLayout: SwipeRefreshLayout
  private lateinit var viewModel: BlogPageViewModel

  fun newInstance(type: GroupType): BlogPageFragment {
    return BlogPageFragment().apply {
      arguments = Bundle().apply {
        putSerializable(BLOG_TYPE, type)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    blogListAdapter = BlogListAdapter(context!!, this)
    viewModel = ViewModelProviders.of(this)
        .get(BlogPageViewModel::class.java)
    viewType = arguments!!.getSerializable(BLOG_TYPE) as GroupType
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = DataBindingUtil.inflate<FragmentBlogPageBinding>(
        inflater, R.layout.fragment_blog_page, container, false
    )

    initSwipeRefreshLayout(binding)
    initRecyclerView(binding)
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
    swipeRefreshLayout.isRefreshing = true
    viewModel.getBlogList(viewType)

    viewModel.blogList.observe(this, Observer<List<BlogModel>> {
      it?.let { blogListAdapter.initViewModelList(it) }
          .also { swipeRefreshLayout.isRefreshing = false }
    })
  }

  override fun onItemClick(
    groupType: GroupType,
    url: String
  ) {
    CustomTabUtil.launchUrl(context!!, groupType, url)
  }

  override fun onRefresh() {
    swipeRefreshLayout.isRefreshing = true
  }
}
