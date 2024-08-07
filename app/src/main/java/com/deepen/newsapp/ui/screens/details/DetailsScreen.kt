package com.deepen.newsapp.ui.screens.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deepen.newsapp.R
import com.deepen.newsapp.data.models.Article
import com.deepen.newsapp.data.models.Source


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    article: Article, modifier: Modifier = Modifier, onBackClicked: (() -> Unit)? = null
) {

    val context = LocalContext.current
    val bookmarksViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)

    Column (
        modifier = modifier.verticalScroll(rememberScrollState()),

    ){
        DetailsTopBar(article = article,
            onFavClicked = {
                bookmarksViewModel.addArticle(article = article)
                Toast.makeText(context, "Bookmark Added", Toast.LENGTH_SHORT).show()
            },
            onWebViewClicked = {
                if (article.url != null) {
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW, Uri.parse(article.url)
                    )
                    context.startActivity(urlIntent)
                }


            },
            onBackClicked = {
                if (onBackClicked != null) {
                    onBackClicked()

                }
            })
        DetailsBody(modifier = modifier,
            context=context, article = article)
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    article: Article,
    onFavClicked: (Article) -> Unit,
    onWebViewClicked: () -> Unit,
    onBackClicked: (() -> Unit)?
) {
    TopAppBar( title = {
        Text(
            text = article.title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp
        )
    }, navigationIcon = {
        IconButton(onClick = {
            if (onBackClicked != null) {
                onBackClicked()
            }
        }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
    }, actions = {
        IconButton(onClick = {
            onFavClicked(article)
        }) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null
            )
        }
        IconButton(onClick = {
            onWebViewClicked()
        }) {
            Icon(
                imageVector = Icons.Outlined.Info, contentDescription = null
            )
        }
    }

    )
}

@Composable
fun DetailsBody(modifier: Modifier = Modifier, context: Context, article: Article) {
    Column(

        modifier = modifier.padding(20.dp)

    ) {

        AsyncImage(
            model = ImageRequest.Builder(context = context).data(article.urlToImage).build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Inside,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = article.description ?: "",
            style = MaterialTheme.typography.bodyMedium,

        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.author ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Blue
            )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.publishedAt ?: "",
            style = MaterialTheme.typography.bodyMedium,

            )

    }
}


@Preview
@Composable
fun PreviewDEtailsScreen() {
    DetailsScreen(article = Article(Source("", ""), "", "Title", "desc", "", "", "", ""))
}

