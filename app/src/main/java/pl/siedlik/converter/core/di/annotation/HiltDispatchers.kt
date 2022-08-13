package pl.siedlik.converter.core.di.annotation

import javax.inject.Qualifier

object HiltDispatchers {

  @Qualifier
  @Retention(AnnotationRetention.BINARY)
  annotation class Main

  @Qualifier
  @Retention(AnnotationRetention.BINARY)
  annotation class Default

  @Qualifier
  @Retention(AnnotationRetention.BINARY)
  annotation class IO

  @Qualifier
  @Retention(AnnotationRetention.BINARY)
  annotation class Unconfined
}