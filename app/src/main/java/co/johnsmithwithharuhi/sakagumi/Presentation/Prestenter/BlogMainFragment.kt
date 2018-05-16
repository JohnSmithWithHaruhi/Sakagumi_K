package co.johnsmithwithharuhi.sakagumi.Presentation.Prestenter

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.johnsmithwithharuhi.sakagumi.Domain.Blog.Blog
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.FragmentBlogMainBinding

class BlogMainFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val mBinding = DataBindingUtil.inflate<FragmentBlogMainBinding>(
        inflater,
        R.layout.fragment_blog_main, container, false
    )
    val tabLayout = mBinding.blogMainTabLayout
    val viewPager = mBinding.blogMainViewPager
    val fragmentPagerAdapter = object : FragmentPagerAdapter(fragmentManager) {
      override fun getPageTitle(position: Int): CharSequence =
        when (convertPagePositionToType(position)) {
          Blog.KEY_OSU -> "推しメン"
          Blog.KEY_NOG -> "乃木坂"
          Blog.KEY_KEY -> "欅坂"
          else -> ""
        }

      override fun getItem(position: Int): Fragment =
        BlogPageFragment().newInstance(convertPagePositionToType(position))

      override fun getCount(): Int = 3
    }

    viewPager.adapter = fragmentPagerAdapter
    tabLayout.setupWithViewPager(viewPager)
    tabLayout.setSelectedTabIndicatorHeight(3)

    return mBinding.root
  }

  private fun convertPagePositionToType(position: Int): Int = when (position) {
    1 -> Blog.KEY_NOG
    2 -> Blog.KEY_KEY
    else -> Blog.KEY_OSU
  }

}