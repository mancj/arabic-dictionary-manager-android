package io.arabic.dictionary.ui.screen.base

@Suppress("unused")
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
/**
 * Annotation for fragments to indicate that function or property is
 * created for use only in activity and should not be called
 * from current fragment
 */
annotation class ForActivity