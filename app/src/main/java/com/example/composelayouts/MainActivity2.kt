package com.example.composelayouts

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.composelayouts.dataclass.Note
import com.example.composelayouts.ui.theme.ComposeLayoutsTheme
import kotlin.random.Random

class MainActivity2 : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController!!.hide(
                android.view.WindowInsets.Type.statusBars()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullCard()
        }
    }
}


val noteList = arrayListOf<Note>(
    Note(
        "All", ""

    ), Note(
        "Important",
        "Review of Previous Action Items\n" +
                "Product Development Update\n" +
                "User Feedback and Customer Insights\n" +
                "Competitive Analysis\n" +
                "Roadmap Discussion "
    ), Note(
        "Lecture notes",
        "Reply to emails\n" +
                "Prepare presentation slides for the marketing meeting\n" +
                "Conduct research on competitor products\n" +
                "Schedule and plan customer interviews\n" +
                "Take a break and recharge "
    ), Note(
        "To-do lists",
        "Rice\n" +
                "Pasta\n" +
                "Cereal\n" +
                "Yogurt\n" +
                "Cheese\n" +
                "Butter"
    ), Note(
        "Shopping list", "Review of Previous Action Items\n" +
                "Product Development Update\n" +
                "User Feedback and Customer Insights\n" +
                "Competitive Analysis\n" +
                "Roadmap Discussion "
    )
)

val colorList = arrayListOf<Color>(Color.Gray, Color.Cyan, Color.Green, Color.Magenta, Color.Yellow)


@Composable
fun FullCard() {

    Box() {
        Column() {
            ComposeLayoutsTheme(false) {
                MainScreenView()
                //title

                Row {
                    Title()
                }

                //searchBar
                Row() {
                    val textState = remember { mutableStateOf(TextFieldValue("")) }
                    SearchBarSample(textState)

                }

                //Category
                Row() {
                    CategoryList()
                }

                //Tasks
                Row() {
                    ListTaskSheet()
                }


            }
        }
    }
}


sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {

    object Home : BottomNavItem("Home", R.drawable.home, "home")
    object MyNetwork : BottomNavItem("My Network", R.drawable.home.hashCode(), "my_network")
    object AddPost : BottomNavItem("Post", R.drawable.home, "add_post")
    object Notification : BottomNavItem("Notification", R.drawable.home, "notification")
    object Jobs : BottomNavItem("Jobs", R.drawable.home, "jobs")
}

@Composable
fun NavigationGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Home.screen_route,
        modifier = Modifier.padding(padding)
    ) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen1()
        }
        composable(BottomNavItem.MyNetwork.screen_route) {
            NetworkScreen()
        }
        composable(BottomNavItem.AddPost.screen_route) {
            AddPostScreen()
        }
        composable(BottomNavItem.Notification.screen_route) {
            NotificationScreen()
        }
        composable(BottomNavItem.Jobs.screen_route) {
            JobScreen()
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }) {
        NavigationGraph(navController = navController, it)
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyNetwork,
        BottomNavItem.AddPost,
        BottomNavItem.Notification,
        BottomNavItem.Jobs
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(10.dp)
                    )
                },
                label = {
                    Text(text = item.title)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun HomeScreen1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Home Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun NetworkScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "My Network Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun AddPostScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Add Post Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}


@Composable
fun NotificationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Notification Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}


@Composable
fun Title() {
    val context = LocalContext.current.applicationContext
    val title = buildAnnotatedString {
        append("THI")
        withStyle(
            style = SpanStyle(
                Color(
                    ContextCompat.getColor(
                        context,
                        R.color.titleSpanColor
                    )
                )
            )
        ) {
            append("N")
        }
        append("K.")
    }
    Text(
        text = title,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SearchBarSample(state: MutableState<TextFieldValue>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {

        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    colorResource(id = R.color.ligheGray),
                    shape = RoundedCornerShape(15.dp)
                ),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search, "", modifier = Modifier
                        .padding(5.dp)
                        .size(24.dp)

                )
            }, trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(onClick = {
                        state.value = TextFieldValue("")
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close Icon",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(24.dp)
                        )

                    }
                }
            }, singleLine = true, colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                leadingIconColor = Color.Black,
                trailingIconColor = Color.Black,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )

        )


    }
}

@Composable
fun CategoryList() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LazyRow(contentPadding = PaddingValues(top = 4.dp, start = 12.dp, end = 12.dp)) {
        items(noteList.size) { positions ->
            if (positions == 0) {
                Text(
                    text = noteList[positions].title,
                    modifier = Modifier
                        .padding(8.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.ligheGray),
                            shape = RoundedCornerShape(12.dp),
                        )
                        .background(
                            color = Color(0xFF1F2937),
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .padding(start = 10.dp, top = 6.dp, end = 10.dp, bottom = 6.dp),
                    color = Color(0xFFFFFFFF)
                )


            } else {
                Text(
                    text = noteList[positions].title, modifier = Modifier
                        .padding(8.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.ligheGray),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(start = 10.dp, top = 6.dp, end = 10.dp, bottom = 6.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskLayout(note: Note) {
    var showLongClickDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = colorList[Random.nextInt(0, colorList.size - 1)],
                shape = RoundedCornerShape(size = 12.dp)
            )
            .combinedClickable(
                onClick = {
                    showDialog = showDialog.not()
                },
                onLongClick = { showLongClickDialog = showLongClickDialog.not() }

            )

    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.title,
                color = Color.Black, fontSize = 22.sp
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.note
            )
        }
        if (showLongClickDialog) {
            AlertDialog(
                onDismissRequest = { showLongClickDialog = false },
                title = { Text("Are you sure you want to delete this?") },
                text = { Text("This action cannot be undone") },
                confirmButton = {
                    TextButton(onClick = {

                    }) {
                        Text("Delete it".uppercase())
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLongClickDialog = false }) {
                        Text("Cancel".uppercase())
                    }
                },
            )
        }
        if (showDialog) {
            CustomDialog(
                note.title,
                note.note,
                onDissmissRequest = { showDialog = false },
                setValue = {})
        }
    }

}

@Composable
fun CustomDialog(
    title: String,
    value: String,
    onDissmissRequest: () -> Unit,
    setValue: (String) -> Unit
) {

    val txtField = remember { mutableStateOf(value) }
    val txtTitle by remember { mutableStateOf(title) }

    Dialog(onDismissRequest = onDissmissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = txtTitle,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { onDissmissRequest() }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = R.color.titleSpanColor)
                                ),
                                shape = RoundedCornerShape(5)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Note is Null") },
                        value = txtField.value,
                        onValueChange = {
                            txtField.value = it
                        })
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListTaskSheet() {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxHeight()
    ) {
        items(noteList.drop(1).size) {
            TaskLayout(noteList[it])
        }

    }
}


@Composable
fun JobScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Jobs Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}
