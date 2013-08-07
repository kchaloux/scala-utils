package com.purloux.scala.utils

/** Provide a quicks method for calling functions that may
 *  result in an exception, returning an Option[A] as the result 
 */
object SafeOperations {
  /** Implicit safePerform extensions on all types
   *  
   *  @param value calling value of any type 
   */
  implicit class AnySafePerformance[A](value : A) {
  
    /** Return Some[A](result) if the provided function succeeds.
     *  Return None[A] if the provided function fails.
     *  
     *  @param fn potentially unsafe operation
     */
    def safePerform[B](fn : A => B): Option[B] = { 
      try {
        Some(fn(value))
      } catch {
        case _ : Throwable => None  
      }
    }
  }

  /** Implicit or() extensions for all Option types 
   *  
   *  @param opt calling option type 
   */
  implicit class OptionOr[A](opt : Option[A]) {
   
    /** Return the value of Some[A](value) or the provided
     *  fallback value for None[A]
     *  
     *  @param fallback value to return if option is None
     */
    def or(fallback : A): A = opt match {
      case Some(value)  => value
      case None         => fallback
    }
  }
}