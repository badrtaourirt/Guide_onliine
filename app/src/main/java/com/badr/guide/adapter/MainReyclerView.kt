package com.badr.guide.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.badr.guide.MainActivity
import com.badr.guide.R
import com.badr.guide.model.Model
import com.bumptech.glide.Glide


class MainReyclerView(
    var context: Context,
    private val listener: MainActivity,

    ):RecyclerView.Adapter<MainReyclerView.CountryViewHoler>() {

    var guideList:MutableList<Model> = ArrayList()

    fun setList(countryList:MutableList<Model>){

        this.guideList=countryList

        notifyDataSetChanged()
    }

    inner class CountryViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        var title: TextView = itemView.findViewById(R.id.title)
        var img: ImageView = itemView.findViewById(R.id.itemimage)
        var actionimage: ImageView = itemView.findViewById(R.id.actionimage)


        fun bind(model: Model, position: Int){

            title.text= model.description
            Glide
                .with(context)
                .load(model.img)
                .override(100, 200)
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .into(img);
            actionimage.setImageResource(model.actionimage)

            actionimage.setOnClickListener{

                val context: Context = itemView.context
                val country = guideList[position]
                val intent = Intent(context, MainActivity::class.java)
                val badr=intent.putExtra(MainActivity.guide_data, country)
                context.startActivity(intent)
                Toast.makeText( it.context,"Added to Favorite", Toast.LENGTH_SHORT).show()

                Log.d("badr","$badr")


                /* val fragment: Fragment = Main()
                 val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                 val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                 fragmentTransaction.replace(R.id.favo, fragment)
                 fragmentTransaction.addToBackStack(null)
                 fragmentTransaction.commit() */


            }






        }

        init {
            itemView.setOnClickListener(this)



        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onemClick(position)

            }
        }

    }


        // end
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHoler {

        var view:View = LayoutInflater.from(parent.context).inflate(R.layout.itemlist,parent,false)

        return CountryViewHoler(view)
    }

    override fun onBindViewHolder(holder: CountryViewHoler, position: Int) {

        holder.bind(guideList[position],position)

    }

    override fun getItemCount(): Int {


        return guideList.size
    }




}
