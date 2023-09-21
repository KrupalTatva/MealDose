package com.restro.mealdose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.compose_structure.presentation.components.CustomDialog
import com.app.compose_structure.presentation.components.NetworkImage
import com.app.compose_structure.presentation.components.ProgressDialog
import com.example.composedemo.composable.TopBar
import com.restro.mealdose.R
import com.restro.mealdose.component.ExpandableText
import com.restro.mealdose.viewmodel.DashboardViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navigateToRandomScreen: () -> Unit,
    navigateToMealListScreen: (String) -> Unit
) {

    Column (modifier = Modifier.fillMaxSize()){
        TopBar(
            title = "Dashboard" ,
            textColor = Color.Black,
            actions = {
                IconButton(onClick = navigateToRandomScreen ) {
                    Image(painter = painterResource(id = R.drawable.ic_slot_machine), contentDescription = "Try your luck" )
                }
            })
        LazyColumn{
            items(viewModel.categoryList){ item ->
                CircularImageCard(
                    title = item?.strCategory,
                    description = item?.strCategoryDescription,
                    imageUrl = item?.strCategoryThumb,
                    onTap = {
                        item?.strCategory?.let { navigateToMealListScreen(it) }
                    }
                )
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
fun CircularImageCard(
    onTap: () -> Unit,
    imageUrl : String? = "",
    title : String? = "",
    description : String? = "",
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable {
                    onTap()
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Circular Image
            NetworkImage(
                imageUrl = imageUrl ?: "",
                modifier = Modifier
                    .fillMaxWidth().height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Name
            Text(
                text = title ?: "",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Category Description
            ExpandableText(
                text = description ?: "",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}