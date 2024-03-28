package com.example.composebottomappbarheight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.composebottomappbarheight.ui.theme.ComposeBottomAppBarHeightTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBottomAppBarHeightTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    ModalNavigationDrawer(
        drawerContent = { DrawerContent() },
        drawerState = drawerState,
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TopBar(drawerState) },
            bottomBar = { BottomAppBar(scrollBehavior) },
        ) { paddingValues ->
            val modifier = Modifier.padding(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
                top = paddingValues.calculateTopPadding() + 8.dp,
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr) + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 8.dp
            )
            MainScreenContent(modifier = modifier)
        }
    }
}

@Composable
fun MainScreenContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            Greeting("Androids list")
        }
        items(100) { index ->
            Greeting("Android number $index")
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomAppBar(scrollBehavior: BottomAppBarScrollBehavior) {
    BottomAppBar(
        modifier = Modifier.height(72.dp), // commenting this line would fix the crash
        scrollBehavior = scrollBehavior,
        content = {
            IconButton(
                onClick = { /* do something */ }
            ) {
                Icon(
                    Icons.Rounded.Home,
                    contentDescription = "HomeButton"
                )
            }
            IconButton(
                onClick = { /* do something */ }
            ) {
                Icon(
                    Icons.Rounded.AccountCircle,
                    contentDescription = "AccountButton"
                )
            }
            IconButton(
                onClick = { /* do something */ }
            ) {
                Icon(
                    Icons.Rounded.Favorite,
                    contentDescription = "FavoriteButton"
                )
            }
        },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = {}, // no title
        navigationIcon = {
            IconButton(
                onClick = { coroutineScope.launch { drawerState.open() } }
            ) {
                Icon(
                    Icons.Rounded.Menu,
                    contentDescription = "MenuButton"
                )
            }
        },
    )
}

@Composable
fun DrawerContent(modifier: Modifier = Modifier) {
    ModalDrawerSheet(modifier.width(260.dp)) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(text = "First element", modifier = Modifier.padding(8.dp))
                Text(text = "Second element", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeBottomAppBarHeightTheme {
        Greeting("Android")
    }
}
