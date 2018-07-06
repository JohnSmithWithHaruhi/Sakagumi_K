package co.johnsmithwithharuhi.sakagumi.Data.JSoup

import co.johnsmithwithharuhi.sakagumi.Data.Model.BlogModel
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import org.jsoup.Jsoup
import java.io.IOException

class BlogJSoup {

  private val URL_NOG = "http://blog.nogizaka46.com"
  private val URL_KEY = "http://www.keyakizaka46.com"
  private val URL_KEY_OPTION = "/s/k46o/diary/member/list?ima=0000&page=0&rw=30&cd=member"

  @Throws(IOException::class)
  fun loadOsuBlogList(): List<BlogModel> {
    val blogList = mutableListOf<BlogModel>()
    val document = Jsoup.connect("$URL_KEY$URL_KEY_OPTION&ct=07")
        .get()
    val elements = document.getElementsByTag("article")
    for (element in elements) {
      BlogModel(
          type = GroupType.OSU,
          name = element.getElementsByClass("name").text(),
          title = element.getElementsByTag("a").first().text(),
          url = URL_KEY + element.getElementsByTag("a").first().attr("href"),
          content = element.getElementsByClass("box-article").text(),
          time = element.getElementsByClass("box-bottom").first()
              .getElementsByTag("li").first().text()
      ).let {
        blogList.add(it)
      }
    }
    return blogList
  }

  @Throws(IOException::class)
  fun loadKeyBlogList(): List<BlogModel> {
    val blogList = mutableListOf<BlogModel>()
    val document = Jsoup.connect(URL_KEY + URL_KEY_OPTION)
        .get()
    val elements = document.getElementsByTag("article")
    for (element in elements) {
      BlogModel(
          type = GroupType.KEYA,
          name = element.getElementsByClass("name").text(),
          title = element.getElementsByTag("a").first().text(),
          url = URL_KEY + element.getElementsByTag("a").first().attr("href"),
          content = element.getElementsByClass("box-article").text(),
          time = element.getElementsByClass("box-bottom").first()
              .getElementsByTag("li").first().text()
      ).let {
        blogList.add(it)
      }
    }
    return blogList
  }

  @Throws(IOException::class)
  fun loadNogBlogList(page: Int): List<BlogModel> {
    val document = Jsoup.connect("$URL_NOG/?p=$page")
        .get()
    val headElements = document.select(".heading")
    val bodyElements = document.getElementsByClass("entrybody")
    val bottomElements = document.getElementsByClass("entrybottom")
    val blogList = mutableListOf<BlogModel>()
    for (i in 0 until headElements.size) {
      BlogModel(
          type = GroupType.NOGI,
          name = headElements[i].getElementsByClass("author").text(),
          title = headElements[i].getElementsByTag("a").first().text(),
          url = headElements[i].getElementsByTag("a").first().attr("href"),
          content = bodyElements[i].text().replace(Regex("\\s+"), ""),
          time = bottomElements[i].text().split("｜")[0]
      ).let {
        blogList.add(it)
      }
    }
    return blogList
  }

}