package com.deepen.newsapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.deepen.newsapp.R
import com.deepen.newsapp.data.models.Article


@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: ((Article) -> Unit)? = null
) {

    val context = LocalContext.current

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            modifier = modifier
                .clickable {
                    if (onClick != null) {
                        onClick(article)
                    }
                }
                .fillMaxWidth()
                .background(Color.White)
                .padding(5.dp)

        ) {

            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                placeholder = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.placeholder),

                )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .height(100.dp)
            ) {
                Text(
                    text = article.title ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.description ?: "",
                        style = MaterialTheme.typography.labelSmall.copy(),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp
                    )

                }
            }
        }
    }

}


@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,

    ) {
    AlertDialog(

        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}


@Composable
fun ResultScreen(text: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center

        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNewsTopBar(title: String = "") {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 25.sp,
                color = Color.DarkGray

            )
        }
    )
}