package com.restro.mealdose.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.compose_structure.presentation.components.CustomDialog
import com.app.compose_structure.presentation.components.NetworkImage
import com.app.compose_structure.presentation.components.ProgressDialog
import com.example.composedemo.composable.TopBar
import com.restro.mealdose.R
import com.restro.mealdose.viewmodel.MealListViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealListScreen(
    viewModel: MealListViewModel = hiltViewModel(),
    onBack : () -> Unit,
    navigationToMealDetailScreen : (String) ->Unit
) {

    Column (modifier = Modifier.fillMaxSize()){
        TopBar(
            title = "Meals" ,
            textColor = Color.Black,
            onBackPressed = onBack
            )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(viewModel.mealList) { cardData ->
                DishCard(
                    imageUrl = cardData?.strMealThumb ?: "",
                    name = cardData?.strMeal ?: ""
                ) {
                    cardData?.idMeal?.let { navigationToMealDetailScreen(it) }
                }
            }
        }
    }

    if(viewModel.loading){
        ProgressDialog {
            viewModel.revertLoading()
        }
    }

    if (viewModel.error.isNotEmpty()){
        CustomDialog(text = viewModel.error, onConfirmClick = viewModel::clearError , onDismissRequest = viewModel::clearError )
    }
}


@Composable
fun DishCard(
    imageUrl: String,
    name: String,
    onTap: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth().clickable { onTap() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            NetworkImage(imageUrl = imageUrl , modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp)))

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = name,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
}

