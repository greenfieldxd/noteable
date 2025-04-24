package greenfieldxd.noteable.presentation.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object DefaultScreenTransitions : DestinationStyle.Animated() {
    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
        slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 300)
        )
    }

    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 300)
        )
    }

    override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
        slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 300)
        )
    }

    override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 300)
        )
    }
}