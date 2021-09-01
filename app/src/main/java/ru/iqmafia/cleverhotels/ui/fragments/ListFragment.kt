package ru.iqmafia.cleverhotels.ui.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.iqmafia.cleverhotels.ui.activities.MainActivity
import ru.iqmafia.cleverhotels.MyApp
import ru.iqmafia.cleverhotels.R
import ru.iqmafia.cleverhotels.ui.adapters.ListRecyclerAdapter
import ru.iqmafia.cleverhotels.database.models.AllHotelsEntity
import ru.iqmafia.cleverhotels.databinding.ListFragmentBinding
import ru.iqmafia.cleverhotels.utils.ACTIVITY
import ru.iqmafia.cleverhotels.utils.MY_IO_SCOPE
import ru.iqmafia.cleverhotels.utils.MY_MAIN_SCOPE
import ru.iqmafia.cleverhotels.viewmodels.ListViewModel

private const val TAG = "ListFragment"

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
        fun goToInfoFrag(id: Int) {
            val myBundle = Bundle().apply { putInt("id", id) }
            ACTIVITY.navController.navigate(R.id.action_listFragment_to_infoFragment, myBundle)
        }
    }

    private var _mBinding: ListFragmentBinding? = null
    private val mBinding get() = _mBinding!!
    private lateinit var mViewModel: ListViewModel
    private lateinit var listRecycler: RecyclerView
    private lateinit var recyclerAdapter: ListRecyclerAdapter
    private lateinit var mActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context as MainActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = ListFragmentBinding.inflate(inflater, container, false)
        showProgressBar()
        initialization()

        return mBinding.root
    }


    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        fetchAllHotelsByRetrofit()
    }

    private fun initialization() {
        mViewModel = ViewModelProvider(mActivity).get(ListViewModel::class.java)
        recyclerAdapter = ListRecyclerAdapter()
        listRecycler = mBinding.listRecycler
        listRecycler.adapter = recyclerAdapter
    }

    private fun fetchAllHotelsByRetrofit() {
        if (mViewModel.loadedFromWebFlag.value == false) {
            //BEST PERFORMANCE JOB
            MY_MAIN_SCOPE.launch {
            mViewModel.fetchAllHotels(hotelsRetrofitApi = (activity?.application as MyApp).hotelsRetrofitApi)
            mViewModel.allHotelsResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful) {
                    MY_IO_SCOPE.launch {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                for (index in it.indices) {
                                    mViewModel.insertAllHotelsToRoom(
                                        AllHotelsEntity(
                                            position = index,
                                            id = it[index].id!!,
                                            address = it[index].address!!,
                                            suitesCount = it[index].suitesAvailability?.split(':')
                                                ?.count()!!,
                                            distance = it[index].distance!!,
                                            name = it[index].name!!,
                                            stars = it[index].stars!!
                                        )
                                    )
                                }
                            }
                        }
                    }
                    loadRecycler()
                } else {
                    Toast.makeText(activity, "Trouble with connection", Toast.LENGTH_SHORT)
                        .show()
                    d(TAG, response.message())
                }
            })
            }
        } else {
            loadRecycler()
        }
    }

    //RECYCLER ACTIONS
    private fun loadRecycler() {
        mViewModel.switchLoadedFlag()
        when (mViewModel.sortingState.value) {
            "default" -> getDefaultSortedHotelsFromRoom()
            "distance" -> getDistanceSortedFromRoom()
            "suites" -> getSuitesSortedFromRoom()
        }
    }

    private fun getDefaultSortedHotelsFromRoom() {
        showProgressBar()
        mViewModel.getSortedByDefault()
        mViewModel.allHotelsEntity.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.setList(it)
        })
        hideProgressBar()
        mViewModel.switchSortingState("default")
    }

    private fun getDistanceSortedFromRoom() {
        showProgressBar()
        mViewModel.getSortedByDistance()
        mViewModel.allHotelsEntity.observe(viewLifecycleOwner, Observer { response ->
            recyclerAdapter.setList(response)
        })
        hideProgressBar()
        mViewModel.switchSortingState("distance")
    }

    private fun getSuitesSortedFromRoom() {
        showProgressBar()
        mViewModel.getSortedBySuites()
        mViewModel.allHotelsEntity.observe(viewLifecycleOwner, Observer { response ->
            recyclerAdapter.setList(response)
        })
        hideProgressBar()
        mViewModel.switchSortingState("suites")
    }


    //UI
    private fun showProgressBar() {
        mBinding.listRecycler.visibility = View.INVISIBLE
        mBinding.listProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        mBinding.listRecycler.visibility = View.VISIBLE
        mBinding.listProgressBar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.list_sorting_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sorting_default -> getDefaultSortedHotelsFromRoom()
            R.id.sorting_distance -> getDistanceSortedFromRoom()
            R.id.sorting_suites -> getSuitesSortedFromRoom()
        }
        return true
    }

    override fun onDestroy() {
        _mBinding = null
        super.onDestroy()
    }

}