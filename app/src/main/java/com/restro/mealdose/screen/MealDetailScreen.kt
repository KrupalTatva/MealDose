package com.restro.mealdose.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.compose_structure.presentation.components.CustomDialog
import com.app.compose_structure.presentation.components.NetworkImage
import com.app.compose_structure.presentation.components.ProgressDialog
import com.example.composedemo.composable.TopBar
import com.restro.mealdose.viewmodel.MealViewModel

@Composable
fun MealDetailScreen(
    mealId : String,
    onBack: ()->Unit,
    viewModel: MealViewModel = hiltViewModel<MealViewModel>()
) {

    LaunchedEffect(key1 = Unit,){
        Log.i("meal id","${mealId}")
        viewModel.getMeal(id = mealId)
    }

    Column (modifier = Modifier.fillMaxSize()){
        TopBar(
            title = viewModel.mealsDetailI?.strMeal ?: "",
            textColor = Color.Black,
            onBackPressed = onBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            NetworkImage(
                imageUrl = viewModel.mealsDetailI?.strMealThumb ?: "",

                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp)
            )
            viewModel.mealsDetailI?.toMeasuresList()?.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 4.dp,),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.ingredient ?: "",
                        style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                    )
                    Text(
                        text = item.measures ?: "",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Recipe",
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp)
            )
            Text(
                text =  viewModel.mealsDetailI?.strInstructions?.replace("\n" , "\n -->") ?: "",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
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