package co.johnsmithwithharuhi.sakagumi.Data.JSoup

import co.johnsmithwithharuhi.sakagumi.Domain.Blog.Blog
import org.jsoup.Jsoup
import java.io.IOException
import java.util.ArrayList

class BlogJSoup {
  private val URL_NOG = "http://blog.nogizaka46.com"
  private val URL_KEY = "http://www.keyakizaka46.com"

  @Throws(IOException::class)
  fun createOsuBlogList(): List<Blog> {
    val blogList = ArrayList<Blog>()
    val document = Jsoup.connect(
        URL_KEY + "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member&ct=07"
    )
        .get()
    val elements = document.getElementsByTag("article")
    for (element in elements) {
      val blog = Blog()
      blog.type = Blog.KEY_OSU
      blog.name = element.getElementsByClass("name")
          .text()
      blog.title = element.getElementsByTag("a")
          .first()
          .text()
      blog.url = URL_KEY + element.getElementsByTag("a").first().attr("href")
      blog.content = element.getElementsByClass("box-article")
          .text()
      blog.time =
          element.getElementsByClass("box-bottom")
              .first()
              .getElementsByTag("li")
              .first()
              .text()
      blogList.add(blog)
    }
    return blogList
  }

  @Throws(IOException::class)
  fun createKeyBlogList(): List<Blog> {
    val blogList = ArrayList<Blog>()
    val document = Jsoup.connect(
        URL_KEY + "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member"
    )
        .get()
    val elements = document.getElementsByTag("article")
    for (element in elements) {
      val blog = Blog()
      blog.type = Blog.KEY_KEY
      blog.name = element.getElementsByClass("name")
          .text()
      blog.title = element.getElementsByTag("a")
          .first()
          .text()
      blog.url = URL_KEY + element.getElementsByTag("a").first().attr("href")
      blog.content = element.getElementsByClass("box-article")
          .text()
      blog.time =
          element.getElementsByClass("box-bottom")
              .first()
              .getElementsByTag("li")
              .first()
              .text()
      blogList.add(blog)
    }
    return blogList
  }

  @Throws(IOException::class)
  fun createNogBlogList(): List<Blog> {
    val blogEntities = ArrayList<Blog>()
    for (i in 1..6) {
      blogEntities.addAll(createNogBlogList(i))
    }
    return blogEntities
  }

  @Throws(IOException::class)
  private fun createNogBlogList(page: Int): List<Blog> {
    val document = Jsoup.connect(URL_NOG + "/?p=" + page)
        .get()
    val headElements = document.select(".heading")
    val bodyElements = document.getElementsByClass("entrybody")
    val bottomElements = document.getElementsByClass("entrybottom")
    val blogList = ArrayList<Blog>()
    for (i in 0 until headElements.size) {
      val headElement = headElements[i]
      val blog = Blog()
      blog.type = Blog.KEY_NOG
      blog.name = headElement.getElementsByClass("author")
          .text()
      blog.title = headElement.getElementsByTag("a")
          .first()
          .text()
      blog.url = headElement.getElementsByTag("a")
          .first()
          .attr("href")
      blog.content = bodyElements[i].text()
          .replace(" ", "")
      blog.time = bottomElements[i].text().split("｜")[0]
      blogList.add(blog)
    }
    return blogList
  }

}