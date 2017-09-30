package co.johnsmithwithharuhi.sakagumi.Data.Repository

import co.johnsmithwithharuhi.sakagumi.Data.JSoup.BlogJSoup
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.Blog
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.BlogRepository

class BlogRepository : BlogRepository {

  private val mBlogJSoup: BlogJSoup = BlogJSoup()

  override fun getBlogList(type: Int): List<Blog> {
    return when (type) {
      Blog.NOG_KEY -> mBlogJSoup.createNogBlogList()
      Blog.KEY_KEY -> mBlogJSoup.createKeyBlogList()
      else -> mBlogJSoup.createOsuBlogList()
    }
  }
}