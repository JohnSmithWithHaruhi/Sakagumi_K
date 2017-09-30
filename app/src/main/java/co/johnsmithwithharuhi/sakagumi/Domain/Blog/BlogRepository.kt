package co.johnsmithwithharuhi.sakagumi.Domain.Blog

interface BlogRepository {

  fun getBlogList(type: Int): List<Blog>

}