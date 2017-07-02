package co.johnsmithwithharuhi.sakagumi.Presentation.Presenter

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.FragmentBlogMainBinding


class BlogMainFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    val mBinding = DataBindingUtil.inflate<FragmentBlogMainBinding>(inflater,
        R.layout.fragment_blog_main,
        container, false)

    val tabLayout = mBinding.blogMainTabLayout
    val viewPager = mBinding.blogMainViewPager


    val fragmentPagerAdapter = object : FragmentPagerAdapter(fragmentManager) {
      override fun getPageTitle(position: Int): CharSequence {
        when (position) {
          0 -> return "推しメン"
          1 -> return "乃木坂"
          2 -> return "欅坂"
          else -> return ""
        }
      }

      override fun getItem(position: Int): Fragment {
        return Fragment()
      }

      override fun getCount(): Int {
        return 3
      }
    }

    viewPager.adapter = fragmentPagerAdapter
    tabLayout.setupWithViewPager(viewPager)
    tabLayout.setSelectedTabIndicatorHeight(3)

    return mBinding.root
  }

}