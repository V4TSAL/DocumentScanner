import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.VerticalPdfReaderState

//@Composable
//fun PdfViewer(
//    modifier: Modifier = Modifier,
//    uri: Uri,
//    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp)
//) {
//    val rendererScope = rememberCoroutineScope()
//    val mutex = remember { Mutex() }
//    val renderer by produceState<PdfRenderer?>(null, uri) {
//        rendererScope.launch(Dispatchers.IO) {
//            val input = ParcelFileDescriptor.open(uri.toFile(), ParcelFileDescriptor.MODE_READ_ONLY)
//            value = PdfRenderer(input)
//        }
//        awaitDispose {
//            val currentRenderer = value
//            rendererScope.launch(Dispatchers.IO) {
//                mutex.withLock {
//                    currentRenderer?.close()
//                }
//            }
//        }
//    }
//    val context = LocalContext.current
//    val imageLoader = LocalContext.current.imageLoader
//    val imageLoadingScope = rememberCoroutineScope()
//    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
//        val width = with(LocalDensity.current) { maxWidth.toPx() }.toInt()
//        val height = (width * sqrt(2f)).toInt()
//        val pageCount by remember(renderer) { derivedStateOf { renderer?.pageCount ?: 0 } }
//        LazyColumn(
//            verticalArrangement = verticalArrangement
//        ) {
//            items(
//                count = pageCount,
//                key = { index -> "$uri-$index" }
//            ) { index ->
//                val cacheKey = MemoryCache.Key("$uri-$index")
//                val cacheValue : Bitmap? = imageLoader.memoryCache?.get(cacheKey)?.bitmap
//
//                var bitmap : Bitmap? by remember { mutableStateOf(cacheValue)}
//                if (bitmap == null) {
//                    DisposableEffect(uri, index) {
//                        val job = imageLoadingScope.launch(Dispatchers.IO) {
//                            val destinationBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                            mutex.withLock {
//                                Log.d("PdfGenerator", "Loading PDF $uri - page $index/$pageCount")
//                                if (!coroutineContext.isActive) return@launch
//                                try {
//                                    renderer?.let {
//                                        it.openPage(index).use { page ->
//                                            page.render(
//                                                destinationBitmap,
//                                                null,
//                                                null,
//                                                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
//                                            )
//                                        }
//                                    }
//                                } catch (e: Exception) {
//                                    //Just catch and return in case the renderer is being closed
//                                    return@launch
//                                }
//                            }
//                            bitmap = destinationBitmap
//                        }
//                        onDispose {
//                            job.cancel()
//                        }
//                    }
//                    Box(modifier = Modifier.background(Color.White).aspectRatio(1f / sqrt(2f)).fillMaxWidth())
//                } else { //bitmap != null
//                    val request = ImageRequest.Builder(context)
//                        .size(width, height)
//                        .memoryCacheKey(cacheKey)
//                        .data(bitmap)
//                        .build()
//
//                    Image(
//                        modifier = Modifier.background(Color.White).aspectRatio(1f / sqrt(2f)).fillMaxWidth(),
//                        contentScale = ContentScale.Fit,
//                        painter = rememberImagePainter(request),
//                        contentDescription = "Page ${index + 1} of $pageCount"
//                    )
//                }
//            }
//        }
//    }
//}
@Composable
fun PdfViewer(modifier: Modifier = Modifier,uri: String) {
    val pdfVerticalReaderState = VerticalPdfReaderState(ResourceType.Remote(uri), isZoomEnable = true)
    VerticalPDFReader(
        state = pdfVerticalReaderState, modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(color = Color.Gray)
    )

}