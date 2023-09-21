package com.restro.mealdose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.compose_structure.presentation.components.CasinoProgressDialog
import com.app.compose_structure.presentation.components.CustomDialog
import com.app.compose_structure.presentation.components.NetworkImage
import com.app.compose_structure.presentation.components.ProgressDialog
import com.example.composedemo.composable.TopBar
import com.restro.mealdose.R
import com.restro.mealdose.component.ExpandableText
import com.restro.mealdose.viewmodel.MealViewModel

@Composable
fun RandomScreen(
    onBack: () -> Unit,
    viewModel: MealViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit,){
        viewModel.getRandomMeal()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Try Your Luck",
            textColor = Color.Black,
            onBackPressed = onBack
        )
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)
            ) {
                // Image (non-scrollable)
                Text(
                    text = viewModel.mealsDetailI?.strMeal ?: "",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 22.sp)
                )
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
                Spacer(modifier = Modifier.height(20.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(
                        Alignment.BottomCenter
                    )
                    .padding(bottom = 20.dp),
            ) {
                FloatingActionButton(
                    onClick = { viewModel.getRandomMeal() },
                    modifier = Modifier
                        .align(
                            Alignment.BottomCenter
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_casino),
                        contentDescription = "Try your luck",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }

    }

    if (viewModel.loading) {
        CasinoProgressDialog {
            viewModel.revertLoading()
        }
    }

    if (viewModel.error.isNotEmpty()) {
        CustomDialog(
            text = viewModel.error,
            onConfirmClick = viewModel::clearError,
            onDismissRequest = viewModel::clearError
        )
    }

}