package co.johnsmithwithharuhi.sakagumi.Data.Model

import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType.OSU

data class BlogModel(
  val id: Long = 0,
  val type: GroupType = OSU,
  val name: String = "",
  val title: String = "",
  val url: String = "",
  val content: String = "",
  val time: String = ""
)