package com.example.hellokittyquiz

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

private const val TAG = "MCListFragment"

class MCListFragment: Fragment() {
    private lateinit var mcRecyclerView: RecyclerView
    private var adapter: MCAdapter? = null

    private val mcListViewModel: MCListViewModel by lazy {
        ViewModelProviders.of(this).get(MCListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total questions: ${mcListViewModel.mcQuests.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mc_quest_list, container, false)

        mcRecyclerView =
            view.findViewById(R.id.mc_recycler_view) as RecyclerView
        mcRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI(){
        val mcQuests = mcListViewModel.mcQuests
        adapter = MCAdapter(mcQuests)
        mcRecyclerView.adapter = adapter
    }

    private inner class MCHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var mcQuest: MCQuestion

        private val title: TextView = itemView.findViewById(R.id.question)
        private val answerButtons: Array<RadioButton> = arrayOf(itemView.findViewById(R.id.answerOne),
            itemView.findViewById(R.id.answerTwo),
            itemView.findViewById(R.id.answerThree),
            itemView.findViewById(R.id.answerFour))

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(mcQuest: MCQuestion){
            this.mcQuest = mcQuest
            title.text = this.mcQuest.questText
            for (i in 0 until 4) {
                answerButtons[i].text = this.mcQuest.questAnswers[i]
                }
        }

        override fun onClick(v: View){
            //Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class MCAdapter(var mcQuests: List<MCQuestion>)
        :RecyclerView.Adapter<MCHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MCHolder {
            val view = layoutInflater.inflate(R.layout.mc_quest_list_item, parent, false)
            return MCHolder(view)
        }

        override fun getItemCount() = mcQuests.size

        override fun onBindViewHolder(holder: MCHolder, position: Int) {
            val mcQuest = mcQuests[position]
            holder.bind(mcQuest)
        }
    }

    companion object{
        fun newInstance(): MCListFragment {
            return MCListFragment()
        }
    }
}