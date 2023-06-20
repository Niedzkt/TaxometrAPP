package pl.wader.taxometrapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import pl.wader.taxometrapp.databinding.FragmentDailyEndScreenBinding

class DailyEndScreenFragment : Fragment() {

    //obejście nie działającego bindingu we fragmentach, teraz działą
    private var _binding: FragmentDailyEndScreenBinding? = null
    private val binding get() = _binding!!
    private val mainVm by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyEndScreenBinding.inflate(layoutInflater, container, false) //rozdmuchanie bindingu
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setOnClickListener - po kliknięciu wywołuje akcje
        var DailyEnterPrice = mainVm.DailyEnter
        var DailyWaitingPrice = (mainVm.TempDailyWaiting/60)*mainVm.DailyWaiting
        var DailyDrivingPrice = mainVm.TempDailyDriving-DailyEnterPrice
        var EndPrice = mainVm.TempDailyDriving+DailyWaitingPrice
        val FormattedEndPrice = String.format("%.2f", EndPrice)

        val FormattedDrivingPrice = String.format("%.2f", DailyDrivingPrice)

        val FormattedWaitingPrice = String.format("%.2f", DailyWaitingPrice)

        val FormattedEnterPrice = String.format("%.2f", DailyEnterPrice)

        binding.TotalDrivePriceTextView.text = FormattedDrivingPrice
        binding.TotalWaitingPriceTextView.text = FormattedWaitingPrice
        binding.TotalPriceTextView.text = FormattedEndPrice
        binding.TotalEnterPriceTextView.text = FormattedEnterPrice

        binding.ContinueButton.setOnClickListener {
            findNavController().navigate(R.id.action_dailyEndScreenFragment_to_firstFragment)
        }

        binding.BackToDrivingFromEndButton.setOnClickListener {
            findNavController().navigate(R.id.action_dailyEndScreenFragment_to_dailyDrivingFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}