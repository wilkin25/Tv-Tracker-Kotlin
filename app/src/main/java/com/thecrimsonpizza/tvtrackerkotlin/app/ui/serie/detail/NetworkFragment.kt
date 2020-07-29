package com.thecrimsonpizza.tvtrackerkotlin.app.ui.serie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.thecrimsonpizza.tvtrackerkotlin.R
import com.thecrimsonpizza.tvtrackerkotlin.app.domain.BasicResponse
import com.thecrimsonpizza.tvtrackerkotlin.app.domain.serie.SerieResponse
import com.thecrimsonpizza.tvtrackerkotlin.app.ui.serie.SeriesViewModel
import com.thecrimsonpizza.tvtrackerkotlin.core.base.BaseClass
import com.thecrimsonpizza.tvtrackerkotlin.core.extensions.getImage
import com.thecrimsonpizza.tvtrackerkotlin.core.extensions.setBaseAdapterTwo
import com.thecrimsonpizza.tvtrackerkotlin.core.utils.GlobalConstants
import com.thecrimsonpizza.tvtrackerkotlin.core.utils.GlobalConstants.BASE_URL_IMAGES_NETWORK
import com.thecrimsonpizza.tvtrackerkotlin.core.utils.GlobalConstants.ID_SERIE
import kotlinx.android.synthetic.main.fragment_genre.*
import kotlinx.android.synthetic.main.lista_series_basic.view.*

class NetworkFragment(data: BaseClass?) : Fragment() {

    private val seriesViewModel: SeriesViewModel by activityViewModels()

    private var mNetwork = data as SerieResponse.Serie.Network
    private val seriesByNetwork: MutableList<BasicResponse.SerieBasic> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genre_name.text = mNetwork.name
        genre_icon.getImage(requireContext(), BASE_URL_IMAGES_NETWORK + mNetwork.logoPath)
        setAdapter()
        getSeriesByNetwork()
    }

    private fun getSeriesByNetwork() {
        seriesViewModel.getShowsByNetwork(mNetwork.id)
            .observe(viewLifecycleOwner, Observer<BasicResponse> {
                seriesByNetwork.clear()
                seriesByNetwork.addAll(it.basicSeries)
                rv_genres.adapter?.notifyDataSetChanged()
            })
    }

    private fun setAdapter() {
        rv_genres.setBaseAdapterTwo(
            seriesByNetwork,
            R.layout.lista_series_basic
        ) { serie ->
            itemView.posterBasic.getImage(
                requireContext(),
                GlobalConstants.BASE_URL_IMAGES_POSTER + serie.posterPath
            )
            itemView.titleBasic.text = serie.name
            itemView.ratingBasic.text = serie.voteAverage.toString()

            itemView.ratingBasic.visibility = View.GONE
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(ID_SERIE, serie.id)
                Navigation.findNavController(it)
                    .navigate(R.id.action_global_navigation_series, bundle)
            }
        }
    }
}