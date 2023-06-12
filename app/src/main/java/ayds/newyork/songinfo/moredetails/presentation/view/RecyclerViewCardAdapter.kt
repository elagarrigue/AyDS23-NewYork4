package ayds.newyork.songinfo.moredetails.presentation.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.utils.UtilsInjector.imageLoader
import ayds.newyork.songinfo.utils.UtilsInjector.navigationUtils

class RecyclerViewCardAdapter(
    private val items: List<Card>
): RecyclerView.Adapter<RecyclerViewCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init()
        val card = items[position]
        holder.bindLogo(card.sourceLogoUrl)
        holder.bindDescription(card.description)
        holder.bindUrl(card.sourceLogoUrl)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var logoView: ImageView
        private lateinit var descriptionView: TextView
        private lateinit var urlView: View

        fun init(){
            logoView = itemView.findViewById(R.id.itemCardLogo)
            descriptionView = itemView.findViewById(R.id.itemCardDescription)
            urlView = itemView.findViewById(R.id.itemCardFullArticleButton)
        }

        fun bindLogo(url: String){
            imageLoader.loadImageIntoView(url, logoView)
        }

        fun bindDescription(description: String){
            descriptionView.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        fun bindUrl(url: String){
            urlView.setOnClickListener {
                if(url.isNotEmpty()) {
                    navigationUtils.openExternalUrl(itemView.context as Activity, url)
                }
            }
            urlView.isEnabled = url.isNotEmpty()
        }
    }

}