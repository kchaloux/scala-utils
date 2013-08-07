package com.purloux.scala.utils.test
import com.purloux.scala.utils.SafeOperations._
import org.scalatest.FlatSpec

class SafeOperationsSpec extends FlatSpec {
  "A safePerform" should "return Some on successful operations" in {
    val result = "1" safePerform (_.toInt)
    assert(result == Some(1))
  }

  it should "return None on unsuccessful operations" in {
    val result = "fail" safePerform (_.toInt)
    assert(result == None)
  }

  "An 'or' redirection" should "return the value contained within a Some Option" in {
    val result = Some(1) or 0
    assert(result === 1)
  }

  it should "return a fallback value when given a None Option" in {
    val result = (None:Option[Int]) or 0
    assert(result === 0)
  }

  "A safePerform-or chain" should "return the result of a success safe-performed function" in {
    val result = "1" safePerform (_.toInt) or 0
    assert(result === 1)
  }

  it should "return a fallback result for an unsuccessfully performed function" in {
    val result = "fail" safePerform (_.toInt) or 0
    assert(result === 0)
  }
}
