package akomissarova.archsample.ui


import akomissarova.archsample.R
import akomissarova.archsample.di.SimpleProvider
import akomissarova.archsample.viewmodel.CitiesViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_cities_list.*


class CitiesListFragment : Fragment() {

    private val adapter = CitiesAdapter()
    private var viewModel: CitiesViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cities_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        citiesList.layoutManager = LinearLayoutManager(context)
        citiesList.adapter = adapter
        viewModel = ViewModelProviders.of(this, SimpleProvider.createCitiesViewModelFactory(context!!)).get(CitiesViewModel::class.java)
        viewModel?.getListMonad()?.observe(this, Observer { it?.let {
            it.fold({
                //todo show error
            }, {
                adapter.setList(it)
            })
        } })
    }

    companion object {
        val TAG = CitiesListFragment::class.toString()
        fun newInstance(): CitiesListFragment {
            val fragment = CitiesListFragment()
            return fragment
        }
    }

}
