package ru.iqmafia.cleverhotels.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.iqmafia.cleverhotels.MyApp
import ru.iqmafia.cleverhotels.R
import ru.iqmafia.cleverhotels.database.models.InfoResponseEntity
import ru.iqmafia.cleverhotels.databinding.InfoFragmentBinding
import ru.iqmafia.cleverhotels.ui.activities.MainActivity
import ru.iqmafia.cleverhotels.utils.*
import ru.iqmafia.cleverhotels.viewmodels.InfoViewModel
import java.lang.Exception

private const val TAG = "InfoFragment"

class InfoFragment : Fragment() {

    companion object {
        fun newInstance() = InfoFragment()
    }

    private var _mBinding: InfoFragmentBinding? = null
    private val mBinding get() = _mBinding!!
    private lateinit var mViewModel: InfoViewModel
    private var mHotelId: Int? = null

    //view
    private var mHotelName: String? = null
    private var mAddress: String? = null
    private var mSuitesCount: Int? = null
    private var mDistance: Double? = null
    private var mStars: Double? = null
    private var mImageId: String? = null


    //    private var
    private lateinit var mActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = InfoFragmentBinding.inflate(inflater, container, false)
        if (arguments != null) {
            mHotelId = arguments?.getInt("id")
        }

        mViewModel = ViewModelProvider(mActivity).get(InfoViewModel::class.java)

        fetchInfoByRetrofit()

        mBinding.infoImage.setOnClickListener {
            val myBundle = Bundle().apply {
                putString("pic", mImageId)
                putString("name", mHotelName)
            }
            ACTIVITY.navController.navigate(R.id.action_infoFragment_to_picFragment, myBundle)

            Toast.makeText(activity, "OnCreate Info", Toast.LENGTH_SHORT)
                .show()
        }


        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(activity, "OnStart Info", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(activity, "OnResume Info", Toast.LENGTH_SHORT)
            .show()
    }


    private fun fetchInfoByRetrofit() {
        //IF START FROM INTENT
        if (mHotelId != null) {
            showProgressBar()
            mViewModel.dynamicFetchHotel(
                (activity?.application as MyApp).hotelsRetrofitApi,
                mHotelId!!
            )
            mViewModel.infoRetrofitResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful) {
                    MY_IO_SCOPE.launch {
                        mViewModel.insertInfoToRoom(
                            InfoResponseEntity(
                                position = 0,
                                id = response.body()?.id,
                                image = response.body()?.image,
                                address = response.body()?.address,
                                suitesCount = response.body()?.suitesAvailability?.split(':')
                                    ?.count(),
                                distance = response.body()?.distance,
                                name = response.body()?.name,
                                stars = response.body()?.stars
                            )
                        )
                    }
                } else {
                    d(TAG, "Retrofit error: $response.message()")
                }
            })
        }
        updateUI()
    }


    private fun updateUI() {
        MY_MAIN_SCOPE.launch {
            if (mImageId == null) {
                mViewModel.getInfoFromRoom()
                val myObserver = Observer<InfoResponseEntity> {
                    mBinding.infoNameTv.text = it.name
                    mBinding.infoAddressTv.text = it.address
                    mBinding.infoSuitesTv.text = it.suitesCount.toString()
                    mBinding.infoDistanceTv.text = it.distance.toString()
                    mBinding.infoStarsTv.text = it.stars.toString()
                    mImageId = it.image
                    Picasso.get().load("$IMAGE_BASE_URL$mImageId")
                        .transform(PicassoTransformation())
                        .into(mBinding.infoImage, object : Callback {
                            override fun onSuccess() {
                                hideProgressBar()
                            }

                            override fun onError(e: Exception?) {
                                hideProgressBar()
                                d(TAG, "Picasso error: $e")
                            }
                        })
                }
                mViewModel.infoRoomResponse.observe(viewLifecycleOwner, myObserver)

            }
        }
    }


    private fun hideProgressBar() {
        mBinding.infoProgressBar.visibility = View.GONE
        mBinding.infoContainer.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        mBinding.infoContainer.visibility = View.INVISIBLE
        mBinding.infoProgressBar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        mHotelId = null
        super.onDestroy()
    }

}