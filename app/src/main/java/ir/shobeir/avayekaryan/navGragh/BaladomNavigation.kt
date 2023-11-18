package ir.shobeir.avayekaryan.navGragh

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import ir.shobeir.avayekaryan.R

sealed class Screen( val route: String,
                     val title: String,
                     val icon: Int,
                     val icon_focused: Int
) {
    object Home: Screen( route = "home",
        title = "پیشخوان",
        icon = R.drawable.newspaper,
        icon_focused = R.drawable.newspaper
    )
    object Detail: Screen(
        route = "detail",
        title = "Detail",
        icon = R.drawable.ic_bottom_profile,
        icon_focused = R.drawable.ic_bottom_profile_focused
    )
    object DetailGallery: Screen(
        route = "detail_gallery",
        title = "DetailGallery",
        icon = R.drawable.ic_bottom_profile,
        icon_focused = R.drawable.ic_bottom_profile_focused
    )
    object Gallery: Screen(
        route = "gallery",
        title = "طاکه",
        icon = R.drawable.media,
        icon_focused = R.drawable.media
    )
    object Comments: Screen(
        route = "comments",
        title = "Comments",
        icon = R.drawable.media,
        icon_focused = R.drawable.media
    )
}








