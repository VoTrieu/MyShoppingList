package com.ttv.myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.ttv.myshoppinglist.ui.theme.MyShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Navigation(modifier: Modifier){
    val navController = rememberNavController()
    val viewModel: LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    NavHost(navController = navController, startDestination = "shoppinglistview"){
        composable("shoppinglistview"){
            ShoppingListView(viewModel, locationUtils, navController, context,
                address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address")
        }
        dialog("locationSelection"){backstack ->
            viewModel.locationData.value?.let {it ->
                LocationSelectionView(it, onLocationSelected = {locationData ->
                    viewModel.fetchAddress("${locationData.latitude},${locationData.longitude}")
                    navController.popBackStack()
                })
            }
        }

    }
}