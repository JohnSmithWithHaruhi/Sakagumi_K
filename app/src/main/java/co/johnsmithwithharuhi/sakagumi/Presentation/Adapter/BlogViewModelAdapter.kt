package co.johnsmithwithharuhi.sakagumi.Presentation.Adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel.ItemBlogViewModel
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.ItemBlogBinding
import java.util.ArrayList

class BlogListAdapter(
  context: Context,
  listener: OnItemClickedListener
) : RecyclerView.Adapter<BlogListAdapter.ViewHolder>() {

  private val mListener = listener
  private var mViewModelList = ArrayList<ItemBlogViewModel>()

  interface OnItemClickedListener {
    fun onItemClick(url: String)
  }

  fun initViewModelList(viewModelList: List<ItemBlogViewModel>) {
    mViewModelList = ArrayList(viewModelList)
    notifyDataSetChanged()
  }

  fun putViewModelList(viewModelList: List<ItemBlogViewModel>) {
    mViewModelList.addAll(0, viewModelList)
    notifyItemRangeInserted(0, viewModelList.size)
  }

  fun getNewestUrl(): String = mViewModelList[0].url.get() ?: ""

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder = ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
  )

  override fun getItemCount(): Int = mViewModelList.size

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    holder.getBinding()
        .viewModel = mViewModelList[position]
    holder.itemView.setOnClickListener {
      mListener.onItemClick(mViewModelList[position].url.get() ?: "")
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mBinding = DataBindingUtil.bind<ItemBlogBinding>(itemView)
    fun getBinding(): ItemBlogBinding = mBinding!!
  }

}