package com.example.diceroller

import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiceDrawer.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiceDrawer : Fragment() {
    // TODO: Rename and change types of parameters
    private var x: Float? = null
    private var y: Float? = null
    private var deb: Boolean = false
    private var SoundHandler: SoundPool? = null

    private var diceThrowSound : Int? = null
    private var diceRollSound : Int? = null

    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Create a soundpool
        SoundHandler = SoundPool.Builder().setMaxStreams(6).setContext(requireContext()).build()

        //Load both sounds ids to variables
        diceThrowSound = SoundHandler!!.load(requireContext(), R.raw.dicethrow, 1)
        diceRollSound = SoundHandler!!.load(requireContext(), R.raw.rollingdice, 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dice_drawer, container, false)

        val switchButton = view.findViewById<ImageButton>(R.id.drawerFragment)

        //define activity to access variables
        val activity = activity as MainActivity

        //Get image and flavorText
        val dieImage = view.findViewById<ImageView>(R.id.rollingDieImage)
        val flavorText = view.findViewById<TextView>(R.id.flavorText)

        //Get drawable for the current dice
        val draw = requireContext().resources.getDrawable(requireContext().resources.getIdentifier("dice"+activity.currentDice, "drawable", requireContext().getPackageName()), requireContext().theme)
        //Set drawable as image
        dieImage.setImageDrawable(draw)


        //Touch handler for dragging dice
        dieImage.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                //Release handler
                MotionEvent.ACTION_UP -> {
                    SoundHandler!!.play(diceThrowSound!!, 1F, 1F, 0, 0, 1F) //Play throw sound from soundpool
                    val num = (1..activity.currentDice).random() //Generate random number
                    flavorText.setText("You rolled a "+num+"!") //Display random number (Note: putting random function in the setText causes a crash)
                }
                //Start handler
                MotionEvent.ACTION_DOWN -> {
                    SoundHandler!!.play(diceRollSound!!, 1F, 1F, 0, 0, 1F) //Play rolling sound from soundpool
                    x = dieImage.x - event.rawX //Set the current x based on where the image was pressed to make smoother drag
                    y = dieImage.y - event.rawY
                }
                //Movement handler
                MotionEvent.ACTION_MOVE -> dieImage.animate()
                    .x(event.rawX + x!!) //X value
                    .y(event.rawY + y!!).setDuration(0).start()//Y value

            }
            true
        }

        switchButton.setOnClickListener {
            view.findNavController().navigate(R.id.drawer_to_main) //Nav controller
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
         * @return A new instance of fragment DiceDrawer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiceDrawer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}