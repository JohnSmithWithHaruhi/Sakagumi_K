package co.johnsmithwithharuhi.sakagumi.Presentation.Adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.johnsmithwithharuhi.sakagumi.Data.Model.BlogModel
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import co.johnsmithwithharuhi.sakagumi.Presentation.Utils.GroupUtil
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.ItemBlogBinding

class BlogListAdapter(
  private val context: Context,
  private val listener: OnItemClickedListener
) : RecyclerView.Adapter<BlogListAdapter.ViewHolder>() {

  private var blogList = mutableListOf<BlogModel>()

  interface OnItemClickedListener {
    fun onItemClick(
      groupType: GroupType,
      url: String
    )
  }

  fun initViewModelList(viewModelList: List<BlogModel>) {
    blogList.clear()
    blogList.addAll(0, viewModelList)
    notifyDataSetChanged()
  }

  fun putViewModelList(viewModelList: List<BlogModel>) {
    blogList.addAll(0, viewModelList)
    notifyItemRangeInserted(0, viewModelList.size)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    return LayoutInflater
        .from(parent.context)
        .inflate(R.layout.item_blog, parent, false)
        .let { ViewHolder(it) }
  }

  override fun getItemCount(): Int {
    return blogList.size
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    blogList[position].let { blogModel ->
      holder.binding.run {
        content.text = blogModel.content
        time.text = blogModel.time
        title.text = blogModel.title
        name.text = blogModel.name
        name.setTextColor(GroupUtil.getGroupColor(context, blogModel.type))
      }
      holder.itemView.setOnClickListener { _ ->
        listener.onItemClick(blogModel.type, blogModel.url)
      }
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = DataBindingUtil.bind<ItemBlogBinding>(itemView)!!
  }
}
