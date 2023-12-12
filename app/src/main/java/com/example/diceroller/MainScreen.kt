package com.example.diceroller

import android.R.attr.data
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)
        //Get buttons and set activity
        val switchButton = view.findViewById<ImageButton>(R.id.mainFragment)
        val activity = activity as MainActivity



        switchButton.setOnClickListener{
            view.findNavController().navigate(R.id.main_to_drawer)
        }

        //Loop based on number to reduce repetitive code and allow for easy additions
        //Note: Not sure if it would be better to use an array instead
        for (i in 1 until 21)
        {
            val buttonID = "dieImage$i"
            val resID : Int = requireContext().resources.getIdentifier(buttonID, "id", requireContext().packageName)
            //If resID is greater than 0 then it is a valid ID
            if (resID > 0){
                var boardButton : ImageView? = view.findViewById<ImageView>(resID)
                boardButton!!.setOnClickListener {
                    activity.currentDice = i
                    Toast.makeText(
                        getActivity(), "Current dice selected: "+i,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainScreen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}