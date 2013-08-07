package com.purloux.scala.utils
import scala.language.reflectiveCalls

/** Provides methods for resource-safe handling of files
 *  
 *  Functions provided by the File module should endeavor
 *  to automatically handle the opening and closing of 
 *  external resources. 
 */
object AutoResource {
  
  /** Opens a closure through which a resource defining a close() function
   *  can be manipulated. Automatically closes the resource when 
   *  the closure exits scope.
   *  
   *  @param closeable any object defining close(): Unit
   *  @param fn actions performed within closure on closeable
   */
  def withResource[A, B <: {def close(): Unit}](closeable: B)(fn : B => A): A = {
    try { fn(closeable) } finally { try { closeable.close }}
  }
  
  /** Opens an external file for reading using io.Source
   *  
   * Exposes a reference to the opening io.Source
   * object, which can be used within a closure to read lines
   * from the file at the given path. When the closure exits,
   * scope, the file stream is closed. 
   * 
   * Note: The final value of the closure will be returned.
   * Make sure that this is a strict value (not an iterator)
   * that does not rely on the file stream being open to access.
   * 
   * @param path location of the file to read
   * @param fn actions performed within closure to read file
   */
  def readText[A](path: String)(fn: (io.Source) => A): A = {
    withResource(io.Source.fromFile(path))(fn)
  }

  /** Opens an external file for writing using io.Source
   *  
   *  Exposes a reference to a java.io.PrintWriter object
   *  within a closure that can be used to write text
   *  sequentially to an output file. When the closure exits
   *  scope, the file stream is closed.
   * 
   * @param path path to write the file to
   * @param fn actions performed within the closure to write file
   */
  def writeText(path: String)(fn: (java.io.PrintWriter) => Unit): Unit = {
    withResource(new java.io.PrintWriter(path))(fn)
  }
}