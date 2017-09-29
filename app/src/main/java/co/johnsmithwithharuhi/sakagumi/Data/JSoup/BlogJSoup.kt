package co.johnsmithwithharuhi.sakagumi.Data.JSoup

import co.johnsmithwithharuhi.sakagumi.Domain.Blog.Blog
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*


class BlogJSoup {
  private val NOG_URL = "http://blog.nogizaka46.com"
  private val KEY_URL = "http://www.keyakizaka46.com"

  @Throws(IOException::class)
  fun createOsuBlogList(): List<Blog> {
    val blogEntities = ArrayList<Blog>()
    val document = Jsoup.connect(
        KEY_URL + "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member&ct=11")
        .get()
    val elements = document.getElementsByTag("article")
    for (element in elements) {
      val blogEntity = Blog()
      blogEntity.setType(Blog.OSU_KEY)
      blogEntity.setName(element.getElementsByClass("name").text())
      blogEntity.setTitle(element.getElementsByTag("a").first().text())
      blogEntity.setUrl(KEY_URL + element.getElementsByTag("a").first().attr("href"))
      blogEntity.setContent(element.getElementsByClass("box-article").text())
      blogEntity.setTime(
          element.getElementsByClass("box-bottom").first().getElementsByTag("li").first().text())
      blogEntities.add(blogEntity)
    }
    return blogEntities
  }

  @Throws(IOException::class)
  fun createKeyBlogList(): List<Blog> {
    val blogEntities = ArrayList<Blog>()
    val document = Jsoup.connect(
        KEY_URL + "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member").get()
    val elements = document.getElementsByTag("article")
    for (element in elements) {
      val blogEntity = Blog()
      blogEntity.setType(Blog.KEY_KEY)
      blogEntity.setName(element.getElementsByClass("name").text())
      blogEntity.setTitle(element.getElementsByTag("a").first().text())
      blogEntity.setUrl(KEY_URL + element.getElementsByTag("a").first().attr("href"))
      blogEntity.setContent(element.getElementsByClass("box-article").text())
      blogEntity.setTime(
          element.getElementsByClass("box-bottom").first().getElementsByTag("li").first().text())
      blogEntities.add(blogEntity)
    }
    return blogEntities
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
    val document = Jsoup.connect(NOG_URL + "/?p=" + page).get()
    val headElements = document.select(".heading")
    val bodyElements = document.getElementsByClass("entrybody")
    val bottomElements = document.getElementsByClass("entrybottom")
    val blogEntities = ArrayList<Blog>()
    for (i in 0 until headElements.size) {
      val headElement = headElements[i]
      val blogEntity = Blog()
      blogEntity.setType(Blog.NOG_KEY)
      blogEntity.setName(headElement.getElementsByClass("author").text())
      blogEntity.setTitle(headElement.getElementsByTag("a").first().text())
      blogEntity.setUrl(headElement.getElementsByTag("a").first().attr("href"))
      blogEntity.setContent(bodyElements[i].text().replace(" ", ""))
      blogEntity.setTime(bottomElements[i].text().split("｜")[0])
      blogEntities.add(blogEntity)
    }
    return blogEntities
  }

}