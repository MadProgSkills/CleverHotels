package ru.iqmafia.cleverhotels.ui.fragments

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import ru.iqmafia.cleverhotels.database.models.PicEntity
import ru.iqmafia.cleverhotels.databinding.PicFragmentBinding
import ru.iqmafia.cleverhotels.utils.*
import ru.iqmafia.cleverhotels.viewmodels.PicViewModel
import java.lang.Exception

private const val TAG = "Pic fragment"

class PicFragment : Fragment() {

    companion object {
        fun newInstance() = PicFragment()
    }

    private var _mBinding: PicFragmentBinding? = null
    private val mBinding get() = _mBinding!!
    private var mHotelName: String? = null
    private var mImage: String? = null
    private lateinit var mViewModel: PicViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = PicFragmentBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(PicViewModel::class.java)

        if (arguments != null) {
            mHotelName = arguments?.getString("name")
            mImage = arguments?.getString("pic")
        }

        //INSERT ROOM IF START FROM INTENT
        if (mHotelName != null) {
            showProgressBar()
            MY_IO_SCOPE.launch {
                mViewModel.insertPicToRoom(
                    PicEntity(
                        position = 0,
                        name = mHotelName,
                        image = mImage
                    )
                )
            }
        }

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        getRoomData()
    }

    private fun getRoomData() {
        MY_MAIN_SCOPE.launch {
            mViewModel.picEntity.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    showProgressBar()
                    mImage = it.image
                    mHotelName = it.name
                    updateUI()
                }
            })
        }
    }

    private fun updateUI() {
        Picasso.get().load("$IMAGE_BASE_URL$mImage").transform(PicassoTransformation())
            .into(mBinding.picImage, object : Callback {
                override fun onSuccess() {
                    hideProgressBar()
                }

                override fun onError(e: Exception?) {
                    hideProgressBar()
                    d(TAG, "Picasso error: $e")
                }

            })
        mBinding.picTv.text = mHotelName
    }

    private fun hideProgressBar() {
        mBinding.picProgressbar.visibility = View.GONE
        mBinding.picContainer.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        mBinding.picContainer.visibility = View.INVISIBLE
        mBinding.picProgressbar.visibility = View.VISIBLE
    }


    override fun onDestroy() {
        _mBinding = null
        super.onDestroy()
    }

}