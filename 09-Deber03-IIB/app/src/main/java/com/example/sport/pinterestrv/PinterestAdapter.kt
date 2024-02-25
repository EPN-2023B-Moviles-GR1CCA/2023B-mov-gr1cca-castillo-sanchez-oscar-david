import android.view.GestureDetector
import android.view.MotionEvent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sport.pinterestrv.ImageDetailActivity
import com.example.sport.pinterestrv.R


class PinterestAdapter(private val images: List<String>) :
    RecyclerView.Adapter<PinterestAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        private val likeImageView: ImageView = view.findViewById(R.id.likeImageView)
        private val overlayView: View = view.findViewById(R.id.overlayView)
        private var imageUrl: String? = null

        init {
            val gestureDetector = GestureDetector(view.context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    imageUrl?.let {
                        val intent = Intent(view.context, ImageDetailActivity::class.java).apply {
                            putExtra("IMAGE_URL", it)
                        }
                        view.context.startActivity(intent)
                    }
                    return true
                }

                override fun onDoubleTap(e: MotionEvent): Boolean {
                    showLikeAnimation()
                    return true
                }
            })

            view.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                true
            }
        }

        fun bind(imageUrl: String) {
            this.imageUrl = imageUrl // Guardar la URL de la imagen para su uso en onSingleTapConfirmed
            Glide.with(imageView.context).load(imageUrl).into(imageView)
        }
        fun showLikeAnimation() {
            // Mostrar el overlay y el ícono del corazón
            overlayView.visibility = View.VISIBLE
            likeImageView.visibility = View.VISIBLE
            likeImageView.alpha = 1f

            // Animar el ícono del corazón para desvanecerse
            likeImageView.animate().alpha(0f).setDuration(1000).withEndAction {
                likeImageView.visibility = View.INVISIBLE
                overlayView.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val photoView = inflater.inflate(R.layout.item_layout, parent, false)
        return ViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = images[position]
        holder.bind(imageUrl) // Asigna los datos sin necesidad de manejar clics aquí
    }

    override fun getItemCount(): Int = images.size
}
