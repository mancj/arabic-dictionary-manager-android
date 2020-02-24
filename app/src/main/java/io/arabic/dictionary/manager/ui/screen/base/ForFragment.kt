package io.arabic.dictionary.ui.screen.base

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
/**
 * Annotation for activities to indicate that function or property is
 * created for use only in fragment and should not be called
 * from current activity.
 * ---
 * **This annotation does not affect the logic of the application and it's compilation.**
 */
annotation class ForFragment(
        /**
         * This parameter is used to indicate in which fragment class a method or property is used.
         * If it is not specified, it means that method or property is created for common use.
         */
        @Suppress("unused")
        val fragmentClass: KClass<out Fragment> = androidx.fragment.app.Fragment::class
)