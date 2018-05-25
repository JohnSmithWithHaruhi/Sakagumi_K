package co.johnsmithwithharuhi.sakagumi.Presentation.Adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.Blog
import co.johnsmithwithharuhi.sakagumi.Presentation.Utils.GroupUtil
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.ItemBlogBinding
import java.util.ArrayList

class BlogListAdapter(
  private val context: Context,
  private val listener: OnItemClickedListener
) : RecyclerView.Adapter<BlogListAdapter.ViewHolder>() {

  private var blogList = ArrayList<Blog>()

  interface OnItemClickedListener {
    fun onItemClick(url: String)
  }

  fun initViewModelList(viewModelList: List<Blog>) {
    blogList.clear()
    blogList.addAll(0, ArrayList(viewModelList))
    notifyDataSetChanged()
  }

  fun putViewModelList(viewModelList: List<Blog>) {
    blogList.addAll(0, viewModelList)
    notifyItemRangeInserted(0, viewModelList.size)
  }

  fun getNewestUrl(): String = blogList[0].url

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder = ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
  )

  override fun getItemCount(): Int = blogList.size

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    blogList[position].let { blog ->
      holder.run {
        binding.run {
          content.text = blog.content
          time.text = blog.time
          title.text = blog.title
          name.text = blog.name
          name.setTextColor(GroupUtil.getGroupColor(context, blog.type))
        }
        itemView.setOnClickListener {
          listener.onItemClick(blog.url)
        }
      }
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = DataBindingUtil.bind<ItemBlogBinding>(itemView)!!
  }
}