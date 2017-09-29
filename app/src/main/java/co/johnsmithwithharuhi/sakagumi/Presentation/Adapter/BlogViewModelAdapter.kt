package co.johnsmithwithharuhi.sakagumi.Presentation.Adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.johnsmithwithharuhi.sakagumi.Presentation.ViewModel.Item.ItemBlogViewModel
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.ItemBlogBinding
import java.util.*

class BlogListAdapter(context: Context,
    listener: ItemBlogViewModel.OnItemClickListener) : RecyclerView.Adapter<BlogListAdapter.ViewHolder>() {

  private val mContext = context
  private val mListener = listener
  private var mViewModelList = ArrayList<ItemBlogViewModel>()

  fun initViewModelList(viewModelList: List<ItemBlogViewModel>) {
    mViewModelList = ArrayList(viewModelList)
    notifyDataSetChanged()
  }

  fun putViewModelList(viewModelList: List<ItemBlogViewModel>) {
    mViewModelList.addAll(0, viewModelList)
    notifyItemRangeInserted(0, viewModelList.size)
  }

  fun getNewestUrl(): String = mViewModelList[0].url.get()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false))

  override fun getItemCount(): Int = mViewModelList.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val binding = holder.getBinding()
    val viewModel = mViewModelList[position]
    viewModel.setOnItemClickListener(mListener)
    binding.viewModel = viewModel
    binding.name.setTextColor(ContextCompat.getColor(mContext, viewModel.textColor))
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mBinding = DataBindingUtil.bind<ItemBlogBinding>(itemView)
    fun getBinding(): ItemBlogBinding = mBinding
  }

}