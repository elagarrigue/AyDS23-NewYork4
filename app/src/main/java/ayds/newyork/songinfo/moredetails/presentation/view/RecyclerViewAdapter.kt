package ayds.newyork.songinfo.moredetails.presentation.view

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.utils.UtilsInjector.imageLoader
import ayds.newyork.songinfo.utils.UtilsInjector.navigationUtils

class RecyclerViewAdapter(
    private val items: List<Card>,
    private val activity: AppCompatActivity
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = items[position]
        imageLoader.loadImageIntoView(card.sourceLogoUrl, holder.logoView)
        holder.descriptionView.text = Html.fromHtml(card.description)
        holder.urlView.setOnClickListener {
            card.infoUrl?.let {
                navigationUtils.openExternalUrl(activity, it)
            }
        }
        holder.urlView.isEnabled = card.infoUrl != null
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logoView: ImageView = itemView.findViewById(R.id.itemCardLogo)
        val descriptionView: TextView = itemView.findViewById(R.id.itemCardDescription)
        val urlView: View = itemView.findViewById(R.id.itemCardFullArticleButton)
    }

}