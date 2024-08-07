package com.deepen.newsapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.deepen.newsapp.data.models.Article
import com.deepen.newsapp.data.models.Source
import com.deepen.newsapp.ui.common.CustomNewsTopBar
import com.deepen.newsapp.ui.common.NewsCard
import com.deepen.newsapp.ui.common.ResultScreen

@Composable
fun HomeScreen(
    homeNewsUiState: HomeNewsUiState, navController:NavHostController? = null,
    modifier: Modifier) {

    when(homeNewsUiState){
       is  HomeNewsUiState.Error -> ResultScreen(text = " ...Error Fetching data")
        is HomeNewsUiState.Loading -> ResultScreen(text = "...Loading")
        is HomeNewsUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            )
            {
                CustomNewsTopBar("News App")
                NewsList(homeNewsUiState.news.articles.filter {
                    !it.title.contains("Removed")
                },modifier = modifier,navController=navController)
            }

        }
        else -> {
            ResultScreen(text = "...Loading")
        }

    }
}






@Composable
fun NewsList(newsArticles:List<Article>,modifier: Modifier = Modifier,navController:NavHostController? = null)
{
 LazyColumn (  modifier = modifier.fillMaxSize(),
     verticalArrangement = Arrangement.spacedBy(20.dp),
     contentPadding = PaddingValues(all = 15.dp)){
        items(newsArticles){
            //NewsItem(it)
            NewsCard(article = it, onClick = {

                navController?.currentBackStackEntry?.savedStateHandle?.set("article",it)
                navController?.navigate("Detail")

            })
        }
 }
}





@Preview
@Composable
fun myPreview()
{
    //NewsItem(item= Article(Source("",""),"","Title","desc","","","",""))
    NewsCard(article = Article(Source("",""),"","Title","desc","","","",""))
}